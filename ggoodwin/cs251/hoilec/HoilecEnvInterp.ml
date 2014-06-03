(* Environment model interpreter for Hoilec that uses 
   OCAML's state to represent Hoilec's state *)

module HoilecEnvInterp = struct
  
  open List
  open FunUtils
  open ListUtils
  open Hoilec

  let print = StringUtils.print 
  let println = StringUtils.println

  (* val run : Hoilec.pgm -> int list -> valu *)
  let rec run (Pgm(fmls,body)) ints = 
    let flen = length fmls
    and ilen = length ints 
    in 
      if flen = ilen then 
        eval body (Env.make fmls (map (fun i -> Int i) ints))
      else 
        raise (EvalError ("Program expected " ^ (string_of_int flen)
                          ^ " arguments but got " ^ (string_of_int ilen)))

  (* val eval : Hoilec.exp -> valu Env.env -> valu *)
  and eval exp env = 
    match exp with 
      Lit v -> v
    | Var name -> 
	(match Env.lookup name env with 
	   Some(v) -> v
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
    | Bindrec(names,defns,body) -> 
        eval body
             (Env.bindrecAll names (fun e -> evalExps defns e) env)

  and evalExps exps env = (* force left-to-right evaluation of exps (not guaranteed by map) *)
    match exps with 
      [] -> [] 
    | exp::exps' -> let valu = eval exp env in valu :: (evalExps exps' env)

  and apply fcn arg = 
    match fcn with 
      Fun(fml,body,env) -> eval body (Env.bind fml arg env)
    | _ -> raise (EvalError ("Non-function rator in application: " ^ (valuToString fcn)))

  (* A function for running programs expressed as strings *)
  let runString pgmString ints = 
    run (sexpToPgm (Sexp.stringToSexp pgmString)) ints

  (* A function for running a programs in a files *)
  let runFile pgmFile ints = 
    let valu = run (sexpToPgm (Sexp.stringToSexp (File.fileToString pgmFile))) ints in
    let _ = println (valuToString valu) in 
    valu

  (* An interactive read-eval-print loop (REPL) for Hoilec expressions.
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
      let _ = print "\nhoilec> " in 
      let sexp = Sexp.readSexp() in 
        try 
          match sexp with 
            Sexp.Seq [Sexp.Sym "#quit"] -> print "\ndone\n"
          | Sexp.Seq [Sexp.Sym "#desugar"; sexp] -> 
              let _ = match sexp with 
		        Sexp.Seq ((Sexp.Sym "def" | Sexp.Sym "load") :: _) -> 
			  app (fun (name,defnx) -> 
		                 println (Sexp.sexpToString
  					  (Sexp.Seq [Sexp.Sym "def"; Sexp.Sym name; desugar defnx])))
                              (declToBindings sexp)
 	              | _ -> print (Sexp.sexpToString (desugar sexp))
	      in loop ()
          | Sexp.Seq ((Sexp.Sym "def" | Sexp.Sym "load") :: _) -> 
	      let newBindings = map (fun (name,defnx) -> (name, sexpToExp defnx))
  	 	                    (declToBindings sexp) in 
	      let _ = for_each (fun (name,defn) -> 
		                 (Env.define name (eval defn globalEnv) globalEnv; 
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
