(* Environment model interpreter for call-by-value Hoilic in which variables
   are modeled by OCAML refs. (An alternative strategy would be
   to model variables by locations in an explicit store.) *)

module HoilicEnvInterp = struct
  
  open FunUtils
  open ListUtils
  open Hoilic

  let print = StringUtils.print 
  let println = StringUtils.println

  (* val run : Hoilic.pgm -> int list -> valu *)
  let rec run (Pgm(fmls,body)) ints = 
    let flen = List.length fmls
    and ilen = List.length ints 
    in 
      if flen = ilen then 
        eval body (Env.make fmls (map (fun i -> ref (Int i)) ints))
      else 
        raise (EvalError ("Program expected " ^ (string_of_int flen)
                          ^ " arguments but got " ^ (string_of_int ilen)))

  (* val eval : Hoilic.exp -> valu ref Env.env -> valu *)
  and eval exp env = 
    match exp with 
      Lit v -> v
    | Var name -> 
	(match Env.lookup name env with 
	   Some implicitCell -> (! implicitCell) (* Implicit variable dereference *)
         | None -> raise (EvalError("Unbound variable: " ^ name)))
    | PrimApp(op, rands) -> (primopFunction op) (evalExps rands env)
    | If(tst,thn,els) -> 
       (match eval tst env with 
         Bool b -> if b then eval thn env else eval els env
       | v -> raise (EvalError ("Non-boolean test value " 
				^ (valuToString v)
				^ " in if expression"))
       )
    | Abs(fml,body) -> Fun(fml,body,env) (* make a closure *)
    | App(rator,rand) ->  (* force left to right evaluation of rator and rand *)
        let fcn = eval rator env in 
        let arg = eval rand env in 
	apply fcn arg
    | Assign(name,rhs) -> 
        (* Store value of rhs in name and return old value. 
           Note that assignment expressions in some other languages return the new value (C), 
           a trivial value (OCAML), or an unspecified value (Scheme). *)
	(match Env.lookup name env with 
	   Some implicitCell -> 
	     let oldValu = (! implicitCell)
             and newValu = eval rhs env in
	     let _ = implicitCell := newValu in 
	       oldValu
         | None -> raise (EvalError("Unbound variable: " ^ name)))

  and evalExps exps env = (* force left-to-right evaluation of exps (not guaranteed by map) *)
    match exps with 
      [] -> [] 
    | exp::exps' -> let valu = eval exp env in valu :: (evalExps exps' env)

  and apply fcn arg = 
    match fcn with 
      Fun(fml,body,env) -> eval body (Env.bind fml (ref arg) env)
    | _ -> raise (EvalError ("Non-function rator in application: " ^ (valuToString fcn)))

  (* A function for running programs expressed as strings *)
  let runString pgmString ints = 
    run (sexpToPgm (Sexp.stringToSexp pgmString)) ints

  (* A function for running a programs in a files *)
  let runFile pgmFile ints = 
    let valu = run (sexpToPgm (Sexp.stringToSexp (File.fileToString pgmFile))) ints in
    let _ = println (valuToString valu) in 
    valu

  (* An interactive read-eval-print loop (REPL) for Hoilic expressions.
     The following directives are supported:

     + (#desugar <exp-or-decl>) prints out the desugared form of <exp-or-decl>

     + (def <name> <exp>) introduces a top-level binding of <name> to <exp>. 
       This binding is mutually recursive with all other top-level bindings.

     + (def (<name> <fml1> ... <fmln>) <exp>) is sugar for
       (def <name> (fun (<fml1> ... <fmln>) <exp>))

     + (load <filename>) loads definitions and other recursive loads
       from file named <filename> (which must be a string literal). 

     + (#quit): Exit the interpreter
   *)

  (* val repl : unit -> unit *)
  let rec repl () =
    (* Repl loop evaluates expressions relative to a global environment for the 
       bindings introduced by declarations  of the form (def ...) and (load ...). 
       All these bindings are mutually recursive. *)
    let globalEnv = Env.global() in 
    let rec loop () = 
      let _ = print "\nhoilic-cbv> " in 
      let sexp = Sexp.readSexp() in 
        try 
          match sexp with 
            Sexp.Seq [Sexp.Sym "#quit"] -> print "\ndone\n"
          | Sexp.Seq [Sexp.Sym "#desugar"; sexp] -> 
              let _ = match sexp with 
		        Sexp.Seq ((Sexp.Sym "def" | Sexp.Sym "load") :: _) -> 
			  for_each (fun (name,defnx) -> 
  		                     println (Sexp.sexpToString
  					     (Sexp.Seq [Sexp.Sym "def"; Sexp.Sym name; desugar defnx])))
                                   (declToBindings sexp)
 	              | _ -> print (Sexp.sexpToString (desugar sexp))
	      in loop ()
          | Sexp.Seq ((Sexp.Sym "def" | Sexp.Sym "load") :: _) -> 
	      let newBindings = map (fun (name,defnx) -> (name, sexpToExp defnx))
  	 	                    (declToBindings sexp) in 
	      let _ = for_each (fun (name,defn) -> 
		                 (Env.define name (ref (eval defn globalEnv)) globalEnv; 
				  println name))
                               newBindings
	      in loop ()
          | _ -> 
	      let _  = println (valuToString (eval (sexpToExp sexp) globalEnv))
	      in loop ()
        with 
          EvalError s -> (println ("EvalError: " ^ s); loop ())
        | Env.DefineError s -> (println ("Shouldn't happen! DefineError: " ^ s); loop ())
        | Env.BlackHole s -> (println ("BlackHole: " ^ s); loop ())
        | SyntaxError s -> (println ("SyntaxError: " ^ s); loop ())
        | Sexp.IllFormedSexp s -> (println ("SexpError: " ^ s); ())
        | Sys_error s -> (println ("Sys_error: " ^ s); loop ())
    in loop ()

end
