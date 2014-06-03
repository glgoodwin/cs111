(* Substitution model interpreter for HOFL *)

module HoflSubstInterp = struct

  open List
  open FunUtils
  open ListUtils
  open Hofl

  let print = StringUtils.print 
  let println = StringUtils.println

  (* val run : Hofl.pgm -> int list -> int *)
  let rec run (Pgm(fmls,body)) ints = 
    let flen = length fmls
    and ilen = length ints 
    in 
      if flen = ilen then 
        eval (substAll (map (fun i -> Lit (Int i)) ints) fmls body)
      else 
        raise (EvalError ("Program expected " ^ (string_of_int flen)
                          ^ " arguments but got " ^ (string_of_int ilen)))

  (* val eval : Hofl.exp -> valu *)
  and eval exp = 
    match exp with 
      Lit v -> v
    | Var name -> raise (EvalError("Unbound variable: " ^ name))
    | PrimApp(op, rands) -> (primopFunction op) (map eval rands)
    | If(tst,thn,els) -> 
       (match eval tst with 
         Bool true -> eval thn
       | Bool false -> eval els
       | v -> raise (EvalError ("Non-boolean test value " 
				^ (valuToString v)
				^ " in if expression"))
       )
    | Abs(fml,body) -> Fun(fml,body,Env.empty) (* No env needed in subst. model *)
    | App(rator,rand) -> apply (eval rator) (eval rand)
    | Bindrec(names,defns,body) -> 
	eval (substAll (map (fun defn -> Bindrec(names,defns,defn))
                            defns)
                       names
                       body)
        (* Below are alternative versions that are *way* too complicated.
           What was I thinking? *)
        (*
	eval (substAll (map (fun defn -> 
	                      (substAll (map (fun name -> Bindrec(names,defns, Var name))
                                             names)
                                        names
                                        defn))
                            defns)
                       names 
                       body)
         *)
        (*
        let recenv = Env.make names 
                              (map (fun name -> Bindrec(names,defns, Var name))
                                   names) 
	in eval (subst body (Env.make names
			              (map (fun defn -> subst defn recenv) defns)))
         *)


  and apply fcn arg = 
    match fcn with 
      Fun(fml,body,_) -> eval (subst1 (Lit arg) fml body)
                         (* Lit converts any argument valu (including lists & functions)
                            into a literal for purposes of substitution *)
    | _ -> raise (EvalError ("Non-function rator in application: "
			     ^ (valuToString fcn)))

  (* A function for running programs expressed as strings *)
  let runString pgmString args = 
    run (sexpToPgm (Sexp.stringToSexp pgmString)) args	  

  (* A function for running a programs in a files *)
  let runFile pgmFile args = 
    run (sexpToPgm (Sexp.stringToSexp (File.fileToString pgmFile))) args	  

  (* An interactive read-eval-print loop (REPL) for Hofl expressions.
     By default, assumes zero arguments, but this can be changed
     with the #args directive (see below). The following directives
     are supported:

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
      let _ = print "\nhofl> " in 
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
           (* Bindrec-based interpretation of top-level definitions. *)
	      let (names,defns) = ListUtils.unzip (List.rev bindings) in 
              let _ = println (valuToString (eval (Bindrec(names,defns, sexpToExp sexp))))
                (* although bindings are mutually recursive, pay attention
                   to more recent bindings when the same name is defined twice. *)
	      in loop bindings
        with 
          EvalError s -> (println ("EvalError: " ^ s); loop bindings)
        | SyntaxError s -> (println ("SyntaxError: " ^ s); loop bindings)
        | Sexp.IllFormedSexp s -> (print ("SexpError: " ^ s); loop bindings)
        | Sys_error s -> (println ("Sys_error: " ^ s); loop bindings)
    in loop [] 

end
