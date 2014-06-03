(* Substitution model interpreter for VALEX *)

module ValexSubstInterp = struct

  open Valex
  open List  
  open FunUtils
  open ListUtils

  (* val run : Valex.pgm -> int list -> int *)
  let rec run (Pgm(fmls,body)) ints = 
    let flen = length fmls
    and ilen = length ints 
    in 
      if flen = ilen then 
        eval (substAll (map (fun i -> Lit (Int i)) ints) fmls body)
      else 
        raise (EvalError ("Program expected " ^ (string_of_int flen)
                          ^ " arguments but got " ^ (string_of_int ilen)))

  (* val eval : Valex.exp -> valu *)
  and eval exp = 
    match exp with 
      Lit v -> v
    | Var name -> raise (EvalError("Unbound variable: " ^ name))
    | PrimApp(op, rands) -> (primopFunction op) (map eval rands)
    | Bind(name,defn,body) -> eval (subst1 (Lit (eval defn)) name body)
    | If(tst,thn,els) -> 
       (match eval tst with 
         Bool true -> eval thn
       | Bool false -> eval els
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

     + (#args (a_1 i_1) ... (a_n i_n)):  Install the n integers i_ 1 ... i_n 
       as the current program arguments a_1 ... a_n

     + (#quit): Exit the interpreter
   *)

  (* val repl : unit -> unit *)
  let repl () =
    let print = StringUtils.print in 
    let sexpToSymIntPair sexp =   (* sexpToStringIntPair : sexp -> (string * int) *)
      match sexp with 
        Sexp.Seq [Sexp.Sym s; Sexp.Int i] -> (s, i)
      | _ -> raise (Failure "Not an symbol/int pair!") in 
    let rec loop env = 
      let _ = print "\n\nvalex> " in 
      let line = read_line () in 
        match (Sexp.stringToSexp line) with 
          Sexp.Seq [Sexp.Sym "#quit"] -> print "\ndone\n"
        | Sexp.Seq ((Sexp.Sym "#args") :: bindings) -> 
            let (names, ints) = ListUtils.unzip (map sexpToSymIntPair bindings) in 
	      loop (Env.make names (map (fun i -> Lit (Int i)) ints))
        | Sexp.Seq [Sexp.Sym "#desugar"; sexp] -> 
            (print (expToString (sexpToExp sexp)); 
              (* stringToSexp performs both the desugaring of desugar
                 as well as the desugaring of bindpar *)
             loop env)
        | _ -> 
            try 
             (print (valuToString (eval (subst (stringToExp line) env))); 
              loop env)
             with 
              EvalError s -> (print ("EvalError: " ^ s); loop env)
            | SyntaxError s -> (print ("SyntaxError: " ^ s); loop env)
            | Sexp.IllFormedSexp s -> (print ("SexpError: " ^ s); loop env)
    in loop Env.empty

end
