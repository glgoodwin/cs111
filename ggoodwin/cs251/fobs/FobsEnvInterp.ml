(* Environment model interpreter for Fobs *)

module FobsEnvInterp = struct
  
  open List
  open FunUtils
  open ListUtils
  open Fobs

  let print = StringUtils.print 
  let println = StringUtils.println

  (* Model a Fobs scoping mechanism as a way to combine static and dynamic environments *)
  type 'a scoping = 'a Env.env (* static *) * 'a Env.env (* dynamic *) -> 'a Env.env 

  (* function closures; see spring'02 code for an alternative form of knot-tying for closures *)
  type closure = Clo of fcn * valu Env.env * closure Env.env

  (* val run : scoping -> scoping -> Fobs.pgm -> int list -> valu *)
  (* vscope is variable scope and fscope is function scope *)
  let rec run vscope fscope (Pgm(fmls,body)) ints = 
    let flen = length fmls and ilen = length ints 
    in if flen <> ilen then 
         raise (EvalError ("Program expected " ^ (string_of_int flen)
                            ^ " arguments but got " ^ (string_of_int ilen)))
       else 
         let rec eval exp venv (* current variable environment *) 
                          fenv (* current function environment *) =
           match exp with 
 	     Lit v -> v
	   | Var name -> 
	       (match Env.lookup name venv with 
		 Some(i) -> i
	       | None -> raise (EvalError("Unbound variable: " ^ name)))
	   | PrimApp(op, rands) -> (primopFunction op) (evalExps rands venv fenv)
	   | If(tst, thn, els) -> 
	       (match eval tst venv fenv with 
		 Bool true -> eval thn venv fenv
	       | Bool false -> eval els venv fenv
	       | v -> raise (EvalError ("Non-boolean test value " ^ (valuToString v)
                                        ^ " in if expression")))
	   | Bind(name, defn, body) -> 
	       eval body (Env.bind name (eval defn venv fenv) venv) fenv
	   | App(fname, rands) -> 
	       apply fname (evalExps rands venv fenv) venv fenv
	   | Funrec(body, fcns) -> 
	       eval body venv 
                    (Env.fix (fun fe -> Env.bindAllThunks (map fcnName fcns)
                                                          (map (fun fcn -> 
                                                                 (fun () -> Clo(fcn, venv, fe)))
                                                                fcns)
                                                          fenv))

         and evalExps exps venv fenv = map (fun e -> eval e venv fenv) exps
		 
         and apply fname actuals dvenv dfenv = 
	   match Env.lookup fname dfenv with 
	     None -> raise (EvalError ("unknown function " ^ fname))
           | Some (Clo(Fcn(name,formals,body), svenv, sfenv)) -> 
	       let flen = length formals and alen = length actuals
	       in if flen <> alen then 
		 raise (EvalError ("Function " ^ name ^ " expected " 
		                   ^ (string_of_int flen) ^ " arguments but got " 
                                   ^ (string_of_int alen)))
	       else eval body (Env.bindAll formals actuals (vscope svenv dvenv))
                              (fscope sfenv dfenv)

         in eval body 
                 (Env.make fmls (map (fun i -> Int i) ints)) (* initial venv *)
                 Env.empty (* initial fenv *)

  (* Scoping mechanisms *)
  let static = fun senv denv -> senv
  let dynamic = fun senv denv -> denv 
  (* "weird" scopes *)
  let empty = fun senv denv -> Env.empty
  let merged1 = fun senv denv -> Env.merge senv denv
  let merged2 = fun senv denv -> Env.merge denv senv

  (* A function for running programs expressed as strings *)
  let runString vscope fscope pgmString ints = 
    run vscope fscope (sexpToPgm (Sexp.stringToSexp pgmString)) ints

  (* A function for running a programs in a files *)
  let runFile vscope fscope pgmFile ints = 
    run vscope fscope (sexpToPgm (Sexp.stringToSexp (File.fileToString pgmFile))) ints

  (* An interactive read-eval-print loop (REPL) for Fobs expressions.
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
  let rec repl vscope fscope () =
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
      let _ = print "\nfobs> " in 
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
		         (run vscope fscope (Pgm(formals, Funrec(sexpToExp sexp, fcns))) actuals))
	      in loop fcns formals actuals
        with 
          EvalError s -> (println ("EvalError: " ^ s); loop fcns formals actuals)
        | SyntaxError s -> (println ("SyntaxError: " ^ s); loop fcns formals actuals)
        | Sexp.IllFormedSexp s -> (println ("SexpError: " ^ s); loop fcns formals actuals)
        | Sys_error s -> (println ("Sys_error: " ^ s); loop fcns formals actuals)
    in loop [] [] []

end
