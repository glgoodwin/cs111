(* 

Filename: Token.lex

Summary: Tokens for Slip-- expressions. 
         For simplicity, they do not carry position information
         (although they should in a more robust implementation). 

*)

structure Token = 

struct 

  datatype binop = Add | Mul | Sub | Div

  datatype token = EOF
               |   ID of string
               |   INT of int
               |   OP of binop
               |   PRINT
               |   LPAREN |  RPAREN | COMMA | SEMI | GETS
               |   BEGIN | END (* new in Slip-- *)

  fun eof() = EOF

  fun isEof(EOF) = true
    | isEof(_) = false

  fun binopToString(Add) = "+"
    | binopToString(Sub) = "-"
    | binopToString(Mul) = "*"
    | binopToString(Div) = "/"

  fun toString(EOF) = "[EOF]"
    | toString(ID(s)) = "[" ^ s ^ "]"
    | toString(INT(i)) = "[" ^ (Int.toString(i)) ^ "]"
    | toString(OP(opr)) = "[" ^ (binopToString(opr)) ^ "]"
    | toString(PRINT) = "[PRINT]" 
    | toString(LPAREN) = "[(]"
    | toString(RPAREN) = "[)]"
    | toString(COMMA) = "[,]"
    | toString(SEMI) = "[;]"
    | toString(GETS) = "[:=]"

end
