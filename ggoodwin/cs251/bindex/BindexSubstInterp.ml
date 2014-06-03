(* Substitution model interpreter for BINDEX *)

module BindexSubstInterp = struct
  
  open Bindex
  open List
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
        (* CBN version: eval (subst1 defn name body) *)

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

  (* An interactive read-eval-print loop (REPL) for Bindex expressions.
     By default, assumes zero arguments, but this can be changed
     with the #args directive (see below). The following directives
     are supported:

     + (#args (a_1 i_1) ... (a_n i_n)): Installs the n integers i_ 1 ... i_n 
       as the current program arguments a_1 ... a_n

     + (#runFile <pgm> <arg1> ... <arg_n>) runs the program specified
       by <filename> on the arguments in <args>, where

       - <pgm> is a symbol or string naming a file containing the program
         or an sexp representation of the program.

       - <arg_i> are integer program arguments

       E.g., (#runFile squares.bdx 7 5)
             (#runFile "squares.bdx" 7 5)
             (#runFile (bindex (x y) (/ (+ x y) 2)) 5 15)

     + (#quit): Exit the interpreter
   *)
  
  let repl () =

    let rec println = StringUtils.println

    and print = StringUtils.print

    and sexpToint sexp =   (* sexpToint : sexp -> int *)
      match sexp with 
        Sexp.Int(i) -> i
      | _ -> raise (Failure ("Not an int!: " ^ (Sexp.sexpToString sexp)))

    and sexpToSymIntPair sexp =   (* sexpToStringIntPair : sexp -> (string * int) *)
      match sexp with 
        Sexp.Seq [Sexp.Sym s; Sexp.Int i] -> (s, i)
      | _ -> raise (Failure "Not an symbol/int pair!") 

    and getPgm sexp = (* get an intex program from a specification *)
      match sexp with 
          (* Treat a symbol or string as the name of a file containing the program *)
        Sexp.Sym filename -> stringToPgm (File.fileToString filename)
      | Sexp.Str filename -> stringToPgm (File.fileToString filename)
      | _ -> sexpToPgm sexp 

    and loop env = (* env here maps names to Lit exps, not names to ints! *)
      let _ = print "\nbindex> " in 
      let line = read_line () in 
        match (Sexp.stringToSexp line) with 
          Sexp.Seq [Sexp.Sym "#quit"] -> println "\nMoriturus te saluto!"
        | Sexp.Seq ((Sexp.Sym "#args") :: bindings) -> 
            let (names, ints) = ListUtils.unzip (map sexpToSymIntPair bindings) in 
	      loop (Env.make names (map (fun i -> Lit i) ints))
        | Sexp.Seq ((Sexp.Sym "#run") :: pgmx :: intxs) -> 
            let _ = try 
    	              println (string_of_int (run (getPgm pgmx) (List.map sexpToint intxs)))
                    with 
                      EvalError s -> println ("Error: " ^ s)
                    | SyntaxError s -> println ("Error: " ^ s)
                    | Sys_error s -> println ("Error: " ^ s)
                    | Failure s -> println ("Error: " ^ s)
             in loop env
        | _ -> let _ = try 
                         println (string_of_int (eval (subst (stringToExp line) env)))
                       with 
                         EvalError s -> println ("Error: " ^ s)
                       | SyntaxError s -> println ("Error: " ^ s)
                       | Sys_error s -> println ("Error: " ^ s)
                       | Failure s -> println ("Error: " ^ s)
	       in loop env

    in loop Env.empty

end
