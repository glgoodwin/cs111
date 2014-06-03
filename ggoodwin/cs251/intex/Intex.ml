module Intex = struct

  open Sexp

  exception SyntaxError of string

  (************************************************************
   Abstract Syntax
   ************************************************************)

  type pgm = Pgm of int * exp (* numargs, body *)

  and exp =
      Lit of int (* value *)
    | Arg of int (* index *)
    | BinApp of binop * exp * exp (* rator, rand1, rand2 *)

  and binop = Add | Sub | Mul | Div | Rem (* Arithmetic ops *)

  (************************************************************
   Folding over Intex Expressions
   ************************************************************)

  let rec fold litfun argfun appfun exp = 
    match exp with 
      Lit i -> litfun i 
    | Arg index -> argfun index
    | BinApp(op,rand1,rand2) -> 
        appfun op 
               (fold litfun argfun appfun rand1)
               (fold litfun argfun appfun rand2)

  (************************************************************
   Size
   ************************************************************)

  let rec sizePgm (Pgm(_,body)) = 1 + (sizeExp body)

(*
  (* direct version *)
  and sizeExp e = 
    match e with 
      Lit i -> 1
    | Arg index -> 1
    | BinApp(_,r1,r2) -> 2 + (sizeExp r1) + (sizeExp r2)
 *)

  (* fold-based version *)
  and sizeExp e = 
    fold (fun _ -> 1) (fun _ -> 1) (fun _ n1 n2 -> 2 + n1 + n2) e

  (************************************************************
   Static Arg Checking
   ************************************************************)

  (***********************************************************************)
  (* Bottom-up strategy *)
  let rec argCheckBottomUp (Pgm(n,body)) = 
   let (lo,hi) = argRangeChoose body
    in (lo >= 1) && (hi <= n)


  (* direct version *)
  and argRange e = 
    match e with 
      Lit i -> (max_int, min_int)
    | Arg index -> (index, index)
    | BinApp(_,r1,r2) -> 
        let (lo1, hi1) = argRange r1
        and (lo2, hi2) = argRange r2
         in (min lo1 lo2, max hi1 hi2)

  (* fold-based version *)
  and argRangeFold e = 
    fold (fun _ -> (max_int, min_int)) 
         (fun index -> (index, index))
         (fun _ (lo1,hi1) (lo2,hi2) -> (min lo1 lo2, max hi1 hi2))
         e

  and argRangeChoose e = argRange e

  (***********************************************************************)
  (* Top-down strategy *)
  let rec argCheckTopDown (Pgm(n,body)) = 
   checkExpChoose body n

  and checkExp exp numargs = 
    match exp with 
      Lit i -> true
    | Arg index -> (1 <= index) && (index <= numargs)
    | BinApp(_,r1,r2) -> (checkExp r1 numargs) && (checkExp r2 numargs)

  (* fold-based version *)
  and checkExpFold exp = 
    fold (fun i  -> (fun numargs -> true))  (* litfun *)
         (fun index -> (fun numargs ->      (* argfun *)
                         (1 <= index) && (index <= numargs)))
         (fun _ f1 f2 ->                    (* appfun *)
           (fun numargs -> (f1 numargs) && (f2 numargs)))
         exp

  and checkExpChoose body numargs = checkExp body numargs 

  and argCheck pgm = argCheckTopDown pgm

  (************************************************************
   Unparsing to S-Expressions
   ************************************************************)

  let rec pgmToSexp p = 
    match p with 
      Pgm (n, e) -> 
        Seq ([Sym "intex"; Int n; expToSexp e])

  and expToSexp e = 
    match e with 
      Lit i -> Int i
    | Arg i -> Seq [Sym "$"; Int i]
    | BinApp (rator, rand1, rand2) -> 
        Seq ([Sym (primopToString rator); expToSexp rand1; expToSexp rand2])

  and primopToString p = 
    match p with 
      Add -> "+"
    | Sub -> "-" 
    | Mul -> "*" 
    | Div -> "/" 
    | Rem -> "%" 

  and expToString s = sexpToString (expToSexp s)
  and pgmToString s = sexpToString (pgmToSexp s)

  (************************************************************
   Parsing from S-Expressions
   ************************************************************)

  let rec sexpToPgm sexp = 
    match sexp with 
      Sexp.Seq [Sexp.Sym("intex");
		Sexp.Int(n);
                body] -> 
        Pgm(n, sexpToExp body)
    | _ -> raise (SyntaxError ("invalid Intex program: " 
                               ^ (sexpToString sexp)))

  and sexpToExp sexp = 
    match sexp with 
      Int i -> Lit i
    | Seq([Sym "$"; Int i]) -> Arg i
    | Seq([Sym p; rand1; rand2]) -> 
        BinApp(stringToPrimop p, sexpToExp rand1, sexpToExp rand2)
    | _ ->  raise (SyntaxError ("invalid Intex expression: "  
                                ^ (sexpToString sexp)))

  and stringToPrimop s = 
    match s with 
      "+" -> Add 
    | "-" -> Sub 
    | "*" -> Mul 
    | "/" -> Div 
    | "%" -> Rem
    |  _ -> raise (SyntaxError ("invalid Intex primop: " ^ s))

  and stringToExp s = sexpToExp (stringToSexp s)
  and stringToPgm s = sexpToPgm (stringToSexp s)

  (************************************************************
   Sample Programs in Abstract Syntax
   ************************************************************)

  let sqr = Pgm(1, BinApp(Mul, Arg(1), Arg(1)))

  let avg = Pgm(2, BinApp(Div, 
                          BinApp(Add, Arg(1), Arg(2)),
                          Lit(2)))

  let f2c = Pgm(1, BinApp(Div, 
                          BinApp(Mul, 
                                 BinApp(Sub,Arg(1),Lit(32)),
                                 Lit(5)),
                          Lit(9)))

  let sumTo = Pgm(1, BinApp(Div, 
                            BinApp(Mul, 
                                   Arg(1),
                                   BinApp(Add, Arg(1),Lit(1))),
                            Lit(2)))

  let divMod = Pgm(4, BinApp(Add, 
                             BinApp(Mul, 
                                    BinApp(Div, Arg(1), Arg(2)),
                                    Arg(3)),
                             BinApp(Rem, Arg(1), Arg(4))))

  let arith = Pgm(2, BinApp(Mul, 
                            BinApp(Add, 
                                   Arg(1), 
                                   BinApp(Div,Arg(1),Arg(2))),
                            BinApp(Rem,
                                   Arg(1), 
                                   BinApp(Sub, Arg(1), Arg(2)))))

  let tooBigArg = Pgm(1, BinApp(Add, Arg(1), Arg(2)))

  let zeroArg = Pgm(1, BinApp(Add, Arg(1), Arg(0)))

  let negArg = Pgm(1, BinApp(Add, Arg(1), Arg(-1)))

  (************************************************************
   Sample Programs in S-Expression Syntax
   ************************************************************)

  let sqr = stringToPgm "(intex 1 (* ($ 1) ($ 1)))"

  let avg = stringToPgm "(intex 2 (/ (+ ($ 1) ($ 2)) 2))"

  let f2c = stringToPgm "(intex 1 (/ (* (- ($ 1) 32) 5) 9))"

  let sumTo = stringToPgm "(intex 1 (/ (* ($ 1) (+ ($ 1) 1)) 2))"

  let divMod = stringToPgm "(intex 4 (+ (* (/ ($ 1) ($ 2)) ($ 3)) (% ($ 1) ($ 4))))"

  let arith= stringToPgm "(intex 2 (* (+ ($ 1) (/ ($ 1) ($ 2)))
                                     (% ($ 1) (- ($ 1) ($ 2)))))"

  let tooBigArg = stringToPgm "(intex 1 (+ ($ 1) ($ 2)))"

  let zeroArg = stringToPgm "(intex 1 (+ ($ 1) ($ 0)))"

  let negArg = stringToPgm "(intex 1 (+ ($ 1) ($ -1)))"

  let programs = [sqr;avg;f2c;sumTo;divMod;tooBigArg;zeroArg;negArg]

end 




