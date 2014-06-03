module IntexInterp = struct
  
  open Intex

  exception EvalError of string

  let rec run (Pgm(n,body)) ints = 
    let len = List.length ints in 
      if n = len then
        eval body ints
      else 
        raise (EvalError ("Program expected " ^ (string_of_int n)
                          ^ " arguments but got " ^ (string_of_int len)))


  (* direct version *)
  and eval exp args = 
    match exp with 
      Lit i -> i
    | Arg index -> 
        if (index <= 0) || (index > List.length args) then 
          raise (EvalError("Illegal arg index: " ^ (string_of_int index)))
        else 
          List.nth args (index - 1)
    | BinApp(op,rand1,rand2) -> 
        binApply op (eval rand1 args) (eval rand2 args)
	  
(*
  (* fold-based version *)
  and eval exp =
    fold (fun i -> (fun args -> i))
      (fun index -> 
        (fun args -> 
	  if (index <= 0) || (index > List.length args) then 
	    raise (EvalError("Illegal arg index: " ^ (string_of_int index)))
f	  else 
	    List.nth args (index - 1)))
      (fun op fun1 fun2 -> 
	(fun args -> 
	  binApply op (fun1 args) (fun2 args)))
      exp
 *)

  and binApply binop x y = 
    match binop with 
      Add -> x + y 
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

  (* An interactive read-eval-print loop (REPL) for Intex expressions.
     By default, assumes zero arguments, but this can be changed
     with the #args directive (see below). The following directives
     are supported:

     + (#args i_1 ... i_n):  Install the n integers i_ 1 ... i_n 
       as the current program arguments

     + (#runFile <pgm> <arg1> ... <arg_n>) runs the program specified
       by <filename> on the arguments in <args>, where

       - <pgm> is a symbol or string naming a file containing the program
         or an sexp representation of the program.

       - <arg_i> are integer program arguments

       E.g., (#runFile avg.itx 3 7)
             (#runFile "avg.itx" 3 7)
             (#runFile (intex 2 (/ (+ ($ 1) ($ 2)) 2)) 3 7)

     + (#quit): Exit the interpreter
   *)

  (* A function for running programs expressed as strings *)
  let runString pgmString args = 
    run (sexpToPgm (Sexp.stringToSexp pgmString)) args	  

  (* A function for running a programs in a files *)
  let runFile pgmFile args = 
    run (sexpToPgm (Sexp.stringToSexp (File.fileToString pgmFile))) args	  
  
  let repl () =

    let rec println = StringUtils.println

    and print = StringUtils.print

    and sexpToint sexp =   (* sexpToint : sexp -> int *)
      match sexp with 
        Sexp.Int(i) -> i
      | _ -> raise (Failure ("Not an int!: " ^ (Sexp.sexpToString sexp)))

    and getPgm sexp = (* get an intex program from a specification *)
      match sexp with 
          (* Treat a symbol or string as the name of a file containing the program *)
        Sexp.Sym filename -> stringToPgm (File.fileToString filename)
      | Sexp.Str filename -> stringToPgm (File.fileToString filename)
      | _ -> sexpToPgm sexp 

    and loop args = 
      let _ = print "\nintex> " in 
      let line = read_line () in 
        match (Sexp.stringToSexp line) with 
          Sexp.Seq [Sexp.Sym "#quit"] -> println "\nMoriturus te saluto!"
        | Sexp.Seq ((Sexp.Sym "#args") :: intxs) -> loop (List.map sexpToint intxs)
        | Sexp.Seq ((Sexp.Sym "#run") :: pgmx :: intxs) -> 
            let _ = try 
    	              println (string_of_int (run (getPgm pgmx) (List.map sexpToint intxs)))
                    with 
                      EvalError s -> println ("Error: " ^ s)
                    | SyntaxError s -> println ("Error: " ^ s)
                    | Sys_error s -> println ("Error: " ^ s)
                    | Failure s -> println ("Error: " ^ s)
             in loop args
        | _ -> let _ = try 
                        println (string_of_int (eval (stringToExp line) args))
                       with 
                         EvalError s -> println ("Error: " ^ s)
                       | SyntaxError s -> println ("Error: " ^ s)
                       | Sys_error s -> println ("Error: " ^ s)
                       | Failure s -> println ("Error: " ^ s)
	       in loop args

    in loop [] 

end
