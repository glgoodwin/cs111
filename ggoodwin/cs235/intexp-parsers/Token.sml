(* 

Filename: Token.lex

Summary: Tokens for Intexp expressions. 
         For simplicity, they do not carry position information
         (although they should in a more robust implementation). 

*)

structure Token = 

struct 

  datatype binop = Add | Mul | Sub | Div | Expt

  datatype token = EOF
               |   INT of int
               |   OP of binop
               |   LPAREN |  RPAREN 

  fun eof() = EOF

  fun isEof(EOF) = true
    | isEof(_) = false

  fun binopToString(Add) = "+"
    | binopToString(Sub) = "-"
    | binopToString(Mul) = "*"
    | binopToString(Div) = "/"
    | binopToString(Expt) = "^"

  fun toString(EOF) = "[EOF]"
    | toString(INT(i)) = "[" ^ (Int.toString(i)) ^ "]"
    | toString(OP(opr)) = "[" ^ (binopToString(opr)) ^ "]"
    | toString(LPAREN) = "[(]"
    | toString(RPAREN) = "[)]"

end
