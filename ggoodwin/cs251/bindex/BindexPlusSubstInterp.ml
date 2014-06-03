(* Substitution model interpreter for BINDEXPLUS *)

module BindexPlusSubstInterp = struct
  
  open BindexPlus
  open List
  open FunUtils
  open ListUtils

  exception EvalError of string

  (* val run : Bindex.pgm -> int list -> int *)
  let rec run (Pgm(fmls,body)) ints = 
    let flen = length fmls
    and ilen = length ints 
    in 
      if flen = ilen then 
        eval (substAll (map (fun i -> Lit i) ints) fmls body)
      else 
        raise (EvalError ("Program expected " ^ (string_of_int flen)
                          ^ " arguments but got " ^ (string_of_int ilen)))

  (* val eval : Bindex.exp -> int *)
  and eval exp = 
    match exp with 
      Lit i -> i
    | Var name -> raise (EvalError("Unbound variable: " ^ name))
    | BinApp(rator,rand1,rand2) -> 
	binApply rator (eval rand1) (eval rand2)
    | Bind(name,defn,body) -> 
        eval (subst1 (Lit (eval defn)) name body)
    | Bindpar(names,defns,body) -> 
        eval (substAll (map (fun defn -> Lit (eval defn)) defns)
		       names
                       body)
    | Bindseq(names,defns,body) -> 
        (* easiest to express this with recursion *)
        evalBindseq names defns body
    | Sigma(name,lo,hi,body) ->
        foldr (+) 0
          (map (fun i -> eval (subst1 (Lit i) name body))
               (range (eval lo) (eval hi)))

  and evalBindseq names defns body = 
    match (names,defns) with 
      ([],[]) -> eval body 
    | (n::names',d::defns') -> 
	 let sub = subst1 (Lit (eval d)) n in 
         evalBindseq names' (map sub defns') (sub body)  
    | _ -> raise (EvalError("shouldn't happen"))

  (* val binApply : Bindex.binop -> int -> int -> int *)
  and binApply op x y = 
    match op with 
    | Add -> x + y
    | Sub -> x - y
    | Mul -> x * y
    | Div -> if y = 0 then 
               raise (EvalError ("Division by 0: " 
                                 ^ (string_of_int x)))
             else 
               x / y
    | Rem -> if y = 0 then 
               raise (EvalError ("Remainder by 0: "
                                 ^ (string_of_int x)))
             else 
               x mod y

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
      let _ = print "\n\nbindex> " in 
      let line = read_line () in 
        match (Sexp.stringToSexp line) with 
          Sexp.Seq [Sexp.Sym "#quit"] -> print "\ndone\n"
        | Sexp.Seq ((Sexp.Sym "#args") :: bindings) -> 
            let (names, ints) = unzip (map sexpToSymIntPair bindings) in 
	      loop (Env.make names (map (fun i -> Lit i) ints))
        | _ -> 
            try 
             (print (string_of_int (eval (subst (stringToExp line) env))); 
              loop env)
             with 
              EvalError s -> (print ("EvalError: " ^ s); loop env)
            | SyntaxError s -> (print ("SyntaxError: " ^ s); loop env)
            | Sexp.IllFormedSexp s -> (print ("SexpError: " ^ s); loop env)
    in loop Env.empty

end
