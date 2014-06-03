 (* Dynamically-scoped environment model interpreter for Hofl *)

module HoflEnvInterpDynamic = struct
  
  open List
  open FunUtils
  open ListUtils
  open Hofl

  let print = StringUtils.print 
  let println = StringUtils.println

  (* val run : Hofl.pgm -> int list -> valu *)
  let rec run (Pgm(fmls,body)) ints = 
    let flen = length fmls
    and ilen = length ints 
    in 
      if flen = ilen then 
        eval body (Env.make fmls (map (fun i -> Int i) ints))
      else 
        raise (EvalError ("Program expected " ^ (string_of_int flen)
                          ^ " arguments but got " ^ (string_of_int ilen)))

  (* val eval : Hofl.exp -> valu Env.env -> valu *)
  and eval exp env = 
    match exp with 
      Lit v -> v
    | Var name -> 
	(match Env.lookup name env with 
	   Some(v) -> v
         | None -> raise (EvalError("Unbound variable: " ^ name)))
    | PrimApp(op, rands) -> (primopFunction op) (map (flip eval env) rands)
    | If(tst,thn,els) -> 
       (match eval tst env with 
         Bool b -> if b then eval thn env else eval els env
       | v -> raise (EvalError ("Non-boolean test value " 
				^ (valuToString v)
				^ " in if expression"))
       )
    | Abs(fml,body) -> Fun(fml,body,env) (* make a closure *)
    | App(rator,rand) -> apply (eval rator env) (eval rand env) env
        (* dynamically scoped interpreter passes env as an extra argument *)
    | Bindrec(names,defns,body) -> 
        eval body
             (Env.fix (fun e -> 
                             (Env.bindAllThunks names 
                                                (map (fun defn -> 
                                                         (fun () -> eval defn e))
                                                     defns)
                                                env)))

  and apply fcn arg denv = 
    (* dynamically scoped interpreter accepts dynamic env denv as an extra argument *)
    match fcn with 
      Fun(fml,body,senv) -> eval body (Env.bind fml arg denv)
         (* dynamically scoped interpreter extends dynamic env rather than static env *)
    | _ -> raise (EvalError ("Non-function rator in application: " ^ (valuToString fcn)))

  (* A function for running programs expressed as strings *)
  let runString pgmString ints = 
    run (sexpToPgm (Sexp.stringToSexp pgmString)) ints

  (* A function for running a programs in a files *)
  let runFile pgmFile ints = 
    run (sexpToPgm (Sexp.stringToSexp (File.fileToString pgmFile))) ints

  (* An interactive read-eval-print loop (REPL) for Hofl expressions.
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
    (* Repl loop carries with it a list of name/exp bindings introduced by declarations
       of the form (def ...) and (load ...). 
       These are used as the bindings of a BINDREC every time an expression is 
       evaluated. Thus, all the bindings are mutually recursive. *)
    let rec loop bindings = 
      let _ = print "\nhofl-dynamic> " in 
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
	      in loop bindings
          | Sexp.Seq ((Sexp.Sym "def" | Sexp.Sym "load") :: _) -> 
	      let newBindings = map (fun (name,defnx) -> (name, sexpToExp defnx))
  	 	                    (declToBindings sexp) in 
	      let _ = app (fun (name,_) -> println name) newBindings (* print each declaration name *)
	      (* Add each binding to end of current list of bindings *)
	      in loop (bindings @ newBindings)
          | _ -> 
           (* (* Bindseq-based interpretation of top-level definitions. *)
	      let (names,defns) = ListUtils.unzip bindings in 
              let _ = println 
		       (valuToString 
		        (eval (sexpToExp sexp)
			      (ListUtils.foldr2 
                                 (* Since more recent bindings are at front of list, 
                                    foldr2 defines a name to most recent definition. *)
                                 (fun n d e -> Env.bind n (eval d e) e)
                                 Env.empty
                                 names
                                 defns))) in 
              loop bindings
            *)
           (* Bindrec-based interpretation of top-level definitions. *)
	      let (names,defns) = ListUtils.unzip (List.rev bindings) in 
	      let _  = println (valuToString (eval (Bindrec(names,defns, sexpToExp sexp))
				    	      Env.empty)) 
                (* although bindings are mutually recursive, pay attention
                   to more recent bindings when the same name is defined twice. *)
	      in loop bindings
        with 
          EvalError s -> (println ("EvalError: " ^ s); loop bindings)
        | SyntaxError s -> (println ("SyntaxError: " ^ s); loop bindings)
        | Sexp.IllFormedSexp s -> (println ("SexpError: " ^ s); loop bindings)
        | Sys_error s -> (println ("Sys_error: " ^ s); loop bindings)
    in loop [] 

end
