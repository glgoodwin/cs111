(* Environment model interpreter for Fofl *)

module FoflPlusEnvInterp = struct
  
  open List
  open FunUtils
  open ListUtils
  open FoflPlus

  let print = StringUtils.print 
  let println = StringUtils.println

  (* Model a Fofl scoping mechanism as a way of combining global and local environments *)
  type scoping = valu Env.env (* global parameter environment *)
                 * valu Env.env (* local parameter environment *)
                 -> valu Env.env 

  (* val run : scoping -> Fofl.pgm -> int list -> valu *)
  (* This function is an example of block structure *)
  let rec run scope (Pgm(fmls,body,fcns)) ints = 
    let flen = length fmls
    and ilen = length ints 
    in if flen <> ilen then 
         raise (EvalError ("Program expected " ^ (string_of_int flen)
                            ^ " arguments but got " ^ (string_of_int ilen)))
       else 
         let genv = Env.make fmls (map (fun i -> Int i) ints) (* global parameter environment *)
         and fenv = Env.make (map fcnName fcns) fcns (* function environment *)

         in let rec eval exp venv (* current variable environment *) =
 	      match exp with 
		Lit v -> v
	      | Var name -> 
		  (match Env.lookup name venv with 
		    Some v -> v
		  | None -> raise (EvalError("Unbound variable: " ^ name)))
	      | PrimApp(op, rands) -> (primopFunction op) (map (flip eval venv) rands)
	      | If(tst, thn, els) -> 
		  (match eval tst venv with 
		    Bool true -> eval thn venv
		  | Bool false -> eval els venv
		  | v -> raise (EvalError ("Non-boolean test value " ^ (valuToString v)
                                           ^ " in if expression"))
		  )
	      | Bind(name, defn, body) -> 
		  eval body (Env.bind name (eval defn venv) venv)
	      | App(fname, rands) -> 
		  (match Env.lookup fname fenv with 
		     None -> raise (EvalError ("unknown function " ^ fname))
		   | Some fcn -> apply fcn (map (flip eval venv) rands) venv)
	      | Fref fname -> 
		  (match Env.lookup fname fenv with 
		    Some f -> Fun f 
		  | None -> raise (EvalError("Unbound function: " ^ fname)))
              |	Fapp(rator,rands) -> 
		  (match eval rator venv with 
		     Fun fcn -> apply fcn (map (flip eval venv) rands) venv
                  | v -> raise (EvalError ("application of non-function " ^ (valuToString v))))

           and apply (Fcn(name,formals,body)) actuals venv = 
	     eval body (Env.bindAll formals actuals (scope genv venv))

           in eval body genv

  (* Scoping mechanisms *)
  let static = fun genv venv -> genv 
  let dynamic = fun genv venv -> venv 
  (* "weird" scopes *)
  let empty = fun genv venv -> Env.empty
  let merged = fun genv venv -> Env.merge genv venv
  (* Note that Env.merge venv genv is equivalent to dynamic scope *)

  (* A function for running programs expressed as strings *)
  let runString scope pgmString ints = 
    run scope (sexpToPgm (Sexp.stringToSexp pgmString)) ints

  (* A function for running a programs in a files *)
  let runFile scope pgmFile ints = 
    run scope (sexpToPgm (Sexp.stringToSexp (File.fileToString pgmFile))) ints

  (* An interactive read-eval-print loop (REPL) for Fofl expressions.
     The following directives are supported:

     + (#desugar <exp-or-decl>) prints out the desugared form of <exp-or-decl>

     + (def (<name> <fml1> ... <fmln>) <exp>) is sugar for
       (def <name> (fun (<fml1> ... <fmln>) <exp>))

     + (load <filename>) loads definitions and other recursive loads
       from file named <filename> (which must be a string literal). 

     + (#args (a_1 i_1) ... (a_n i_n)):  Install the n integers i_ 1 ... i_n 
       as the current program arguments a_1 ... a_n

     + (#quit): Exit the interpreter
   *)

  (* val repl : unit -> unit *)
  let rec repl scope () =
    let sexpToSymIntPair sexp =   (* sexpToStringIntPair : sexp -> (string * int) *)
      match sexp with 
        Sexp.Seq [Sexp.Sym s; Sexp.Int i] -> (s, i)
      | _ -> raise (Failure "Not an symbol/int pair!") in 
    (* Repl loop carries with it 
       (1) a list of function declarations
       (2) a list of program formal parameter names
       (3) a list of program actual arguments (integers) 
     *)
    let rec loop fcns formals actuals = 
      let _ = print "\nfofl> " in 
      let sexp = Sexp.readSexp() in 
        try 
          match sexp with 
            Sexp.Seq [Sexp.Sym "#quit"] -> print "\ndone\n"
          | Sexp.Seq [Sexp.Sym "#desugar"; sexp] -> 
              let _ = match sexp with 
		        Sexp.Seq ((Sexp.Sym "def" | Sexp.Sym "load") :: _) -> 
			  app (fun (Fcn(fname,formals,body)) -> 
		                 println (Sexp.sexpToString
  					  (Sexp.Seq [Sexp.Sym "def"; 
						     Sexp.Seq ((Sexp.Sym fname) ::
							       (map (fun fml -> Sexp.Sym fml)
								  formals));
						     expToSexp body])))
                              (declToFcns sexp)
 	              | _ -> print (Sexp.sexpToString (desugar sexp))
	      in loop fcns formals actuals
          | Sexp.Seq ((Sexp.Sym "#args") :: bindings) -> 
              let (names, ints) = ListUtils.unzip (map sexpToSymIntPair bindings) in 
	      loop fcns names ints
          | Sexp.Seq ((Sexp.Sym "def" | Sexp.Sym "load") :: _) -> 
	      let newFcns = declToFcns sexp in 
	      let _ = app (fun fcn -> println (fcnName fcn)) newFcns (* print each declaration name *)
	      (* Add each function declaration to *front* of current declaration list *)
	      (* This ensures that when there are duplicate names, the more recent
                 declaration is chosen. *)
	      in loop (newFcns @ fcns) formals actuals 
          | _ -> 
           (* Evaluation of expressions *)
	      let _ = println
		       (valuToString 
		         (run scope (Pgm(formals, sexpToExp sexp, fcns)) actuals))
	      in loop fcns formals actuals
        with 
          EvalError s -> (println ("EvalError: " ^ s); loop fcns formals actuals)
        | SyntaxError s -> (println ("SyntaxError: " ^ s); loop fcns formals actuals)
        | Sexp.IllFormedSexp s -> (println ("SexpError: " ^ s); loop fcns formals actuals)
        | Sys_error s -> (println ("Sys_error: " ^ s); loop fcns formals actuals)
    in loop [] [] []

end
