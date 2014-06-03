(* 

Filename: Token.lex

Summary: Tokens for SML-like types
         For simplicity, they do not carry position information

*)

structure Token = 

struct 

  datatype baseTy = Unit | Bool | Int | Real | Char | String

  datatype token = EOF
               |   BASE of baseTy
               |   PROD
               |   ARROW
               |   LIST
               |   LPAREN 
               |   RPAREN 

  fun eof() = EOF

  fun isEof(EOF) = true
    | isEof(_) = false

  fun baseTyToString(Unit) = "unit"
    | baseTyToString(Bool) = "bool"
    | baseTyToString(Int) = "int"
    | baseTyToString(Real) = "real"
    | baseTyToString(Char) = "char"
    | baseTyToString(String) = "string"

  fun toString(EOF) = "[EOF]"
    | toString(BASE(b)) = "[" ^ (baseTyToString(b)) ^ "]"
    | toString(PROD) = "*" 
    | toString(ARROW) = "[->]"
    | toString(LIST) = "[list]" 
    | toString(LPAREN) = "[(]"
    | toString(RPAREN) = "[)]"

end
