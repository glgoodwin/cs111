module MiniFP = struct

  exception SyntaxError of string

  (************************************************************
   Abstract Syntax
   ************************************************************)

  type obj = 
      Int of int (* integer objects *)
    | Seq of obj list (* sequence of objects *)

  type funForm = 
      Id    (* identity *)
    | Add   (* addition *)
    | Sub   (* subtraction *)
    | Mul   (* multiplication *) 
    | Div   (* division *)
    | Distl (* ditribute from left *)
    | Distr (* ditribute from right *)
    | Trans (* transpose *)
    | Sel of int    (* selection *)
    | Const of obj  (* constant function *)
    | Map of funForm  (* alpha = map *)
    | Reduce of funForm (* / = reduce *)
    | BinaryToUnary of funForm * obj (* bu *)
    | Compose of funForm * funForm  (* o = composition *)
    | FunSeq of funForm list (* [...] = function sequence *)


  (************************************************************
   Unparsing to S-Expressions
   ************************************************************)

  let rec objToSexp obj = 
    (* Replace this stub for group problem 2c *)
    Sexp.Sym "<object>"

  and objToString obj = Sexp.sexpToString (objToSexp obj)

  let rec funFormToSexp ff = 
    (* Replace this stub for group problem 2c *)
    Sexp.Sym "<functional form>"

  and funFormToString ff = Sexp.sexpToString (funFormToSexp ff)

  (************************************************************
   Parsing from S-Expressions
   ************************************************************)

  let rec sexpToObj sexp = 
    (* Replace this stub for group problem 2c *)
    Int 17

  and stringToObj s = sexpToObj (Sexp.stringToSexp s)

  let rec sexpToFunForm sexp = 
    (* Replace this stub for group problem 2c *)
    Id

  and stringToFunForm s = sexpToFunForm (Sexp.stringToSexp s)

  (************************************************************
   Sample Objects and Functional Forms in Abstract Syntax
   ************************************************************)

  let vector1 = Seq[Int 2; Int 3; Int 5]

  let vector2 = Seq[Int 10; Int 20; Int 30]

  let vectors = Seq[vector1; vector2] (* A pair of vectors or a 2x3 matrix *)

  let matrix = Seq[Seq[Int 1; Int 4]; Seq[Int 8; Int 6]; Seq[Int 7; Int 9]] (* A 3x2 matrix *)
      
  let matrices1 = Seq[vectors; matrix] (* A pair of a 2x3 matrix and a 3x2 matrix *)
      
  let matrices2 = Seq[matrix; vectors] (* A pair of a 3x2 matrix and a 2x3 matrix *)

  (* inner product example from Backus's paper *)
  let ip = Compose(Reduce Add, Compose(Map Mul, Trans))

  (* matrix multiply example from Backus's paper *)
  let mm = Compose(Map(Map ip), 
		   Compose(Map Distl, 
			   Compose(Distr, 
				   FunSeq[Sel 1; Compose(Trans, Sel 2)])))

  (* the F function from PS4 *)
  let f = Compose(Map(Reduce Add), 
		  Compose (Map (Map Mul), 
			   Compose(Map Distl, 
				   Compose(Distr, FunSeq[Id;Id]))))
    
   
          

end 




