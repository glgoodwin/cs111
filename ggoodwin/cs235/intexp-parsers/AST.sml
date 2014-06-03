structure AST = struct

  datatype pgm = Pgm of exp

  and exp = 
    Int of int
  | BinApp of exp * Token.binop * exp

  (* Construct a fully-parenthesized infix expression string *)
  fun expToString (Int(i)) = Int.toString(i)
    | expToString (BinApp(e1,b,e2)) = "(" ^ (expToString e1) 
                                       ^ " " ^ (Token.binopToString b)
                                       ^ " " ^ (expToString e2)
                                       ^ ")" 

end
