(* Environment model interpreter for call-by-name Hoilic in which variables
   are modeled by OCAML refs containing promises. This difference
   could be abstracted over, but I have not done that here. *)

module HoilicCBLEnvInterp = struct
  
  open FunUtils
  open ListUtils
  open HoilicCBL
  open Promise

  let print = StringUtils.print 
  let println = StringUtils.println

  (* val run : Hoilic.pgm -> int list -> valu *)
  let rec run (Pgm(fmls,body)) ints = 
    let flen = List.length fmls
    and ilen = List.length ints 
    in 
      if flen = ilen then 
        eval body (Env.make fmls (map (fun i -> ref (delayedValu (Int i))) ints))
      else 
        raise (EvalError ("Program expected " ^ (string_of_int flen)
                          ^ " arguments but got " ^ (string_of_int ilen)))

  (* Convert a value to a promise *)
  and delayedValu v = makePromise (fun () -> v)

  (* val eval : Hoilic.exp -> valu thunk ref Env.env -> valu *)
  and eval exp env = 
    match exp with 
      Lit v -> v
    | Var name -> 
	(match Env.lookup name env with 
	   Some implicitCell -> force (! implicitCell) 
	      (* Calculate value of promise from implicit variable dereference *)
         | None -> raise (EvalError("Unbound variable: " ^ name)))
    (* Special case for prep, which is a lazy data constructor *)
    | PrimApp(Primop("prep",_), rands) -> 
        (match rands with 
	  [rand1;rand2] -> 
	    List(DCons(makePromise (fun () -> eval rand1 env), 
		       makePromise 
                        (fun () -> 
                          (* Delay the type checking of the tail *)
		         match eval rand2 env with 
			   List dlist -> dlist
			 | v -> raise (EvalError ("Non-list tail in list: " 
						  ^ (valuToString v))))))
        | _ -> raise (EvalError ("Prep expects two arguments but got: " ^ 
				 (String.concat "," (map expToString rands)))))
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
        let argPromise = makePromise (fun () -> eval rand env) in 
	apply fcn argPromise
    | Assign(name,rhs) -> 
        (* Store value of rhs in name and return old value. 
           Note that assignment in many other languages returns the new value (C) 
           or a trivial value (OCAML, Scheme). *)
	(match Env.lookup name env with 
	  Some(implicitCell) -> 
	    let oldValu = force (! implicitCell)
            and newValu = eval rhs env in (* evaluate *now* to avoid loops like (<- c (+ c 1)) *)
	    let _ = implicitCell := delayedValu newValu in 
	    oldValu
	| None -> raise (EvalError("Unbound variable: " ^ name)))
    | Sequence(exp1,exp2) -> (let _ = eval exp1 env in eval exp2 env)

  and evalExps exps env = (* force left-to-right evaluation of exps (not guaranteed by map) *)
    match exps with 
      [] -> [] 
    | exp::exps' -> let valu = eval exp env in valu :: (evalExps exps' env)

  and apply fcn argPromise = 
    match fcn with 
      Fun(fml,body,env) -> eval body (Env.bind fml (ref argPromise) env)
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
      let _ = print "\nhoilic-cbn> " in 
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
		                 (* As in assignments, top-level definitions are
                                    evaluated fully to values that are then thunked. *)
		                 let valu = eval defn globalEnv in 
				 let _ = Env.define name (ref (delayedValu valu)) globalEnv in 
				 println name)
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


