(* Environment model interpreter for VALEX *)

module ValexEnvInterp = struct

  open Valex
  open List  
  open FunUtils
  open ListUtils

  let print = StringUtils.print 
  let println = StringUtils.println


  (* val run : Valex.pgm -> int list -> valu *)
  let rec run (Pgm(fmls,body)) ints = 
    let flen = length fmls
    and ilen = length ints 
    in 
      if flen = ilen then 
        eval body (Env.make fmls (map (fun i -> Int i) ints))
      else 
        raise (EvalError ("Program expected " ^ (string_of_int flen)
                          ^ " arguments but got " ^ (string_of_int ilen)))

  (* val eval : Valex.exp -> valu Env.env -> valu *)
  and eval exp env = 
    match exp with 
      Lit v -> v
    | Var name -> 
	(match Env.lookup name env with 
	   Some(i) -> i
         | None -> raise (EvalError("Unbound variable: " ^ name)))
    | PrimApp(op, rands) -> (primopFunction op) (map (flip eval env) rands)
    | Bind(name,defn,body) -> eval body (Env.bind name (eval defn env) env) 
    | If(tst,thn,els) -> 
       (match eval tst env with 
         Bool b -> if b then eval thn env else eval els env
       | v -> raise (EvalError ("Non-boolean test value " 
				^ (valuToString v)
				^ " in if expression"))
       )

  (* A function for running programs expressed as strings *)
  let runString pgmString args = 
    run (sexpToPgm (Sexp.stringToSexp pgmString)) args	  

  (* A function for running a programs in a files *)
  let runFile pgmFile args = 
    run (sexpToPgm (Sexp.stringToSexp (File.fileToString pgmFile))) args	  

  (* An interactive read-eval-print loop (REPL) for Sigmex expressions.
     By default, assumes zero arguments, but this can be changed
     with the #args directive (see below). The following directives
     are supported:

     + (#desugar exp) prints out the desugared form of exp

     + (#args (a_1 i_1) ... (a_n i_n)):  Install the n integers i_ 1 ... i_n 
       as the current program arguments a_1 ... a_n

     + (#quit): Exit the interpreter
   *)

  (* val repl : unit -> unit *)
  let repl () =
    let sexpToSymIntPair sexp =   (* sexpToStringIntPair : sexp -> (string * int) *)
      match sexp with 
        Sexp.Seq [Sexp.Sym s; Sexp.Int i] -> (s, i)
      | _ -> raise (Failure "Not an symbol/int pair!") in 
    let rec loop env = 
      let _ = print "\n\nvalex> " in 
      let sexp = Sexp.readSexp () in 
        try
          match sexp with 
            Sexp.Seq [Sexp.Sym "#quit"] -> print "\ndone\n"
          | Sexp.Seq [Sexp.Sym "#desugar"; sexp] -> 
              (print (expToString (sexpToExp sexp)); 
              (* sexpToExp performs both the desugaring of desugar and the bindpar desugaring *)
               loop env)
          | Sexp.Seq ((Sexp.Sym "#args") :: bindings) -> 
              let (names, ints) = ListUtils.unzip (map sexpToSymIntPair bindings) in 
	      loop (Env.make names (map (fun i -> Int i) ints))
          | _ -> (print (valuToString (eval (sexpToExp sexp) env)); 
		  loop env)
        with 
	  EvalError s -> (println ("EvalError: " ^ s); loop env)
        | SyntaxError s -> (println ("SyntaxError: " ^ s); loop env)
        | Sexp.IllFormedSexp s -> (println ("SexpError: " ^ s); loop env)
    in loop Env.empty

end