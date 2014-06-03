{
 open Fp_parser ;;

  let string_chars s =
    String.sub s 1 ((String.length s) - 2) ;;

  let int_sel s =
    int_of_string (String.sub s 0 ((String.length s) - 1)) ;;

}


let prim = 
  "+" | "-" | "*" | "div" | "÷"
| "reverse" | "length" | "transpose"
| "tl" | "tlr" | "distl" | "distr"
| "apndl" | "apndr" | "rotl" | "rotr"
| "id" 
| "atom" | "eq" | "null"
| "and" | "or" | "not" 

rule lexer = parse
  [' '] { lexer lexbuf }

| '#' [^'\n']* '\n' { Leol } 
|  '\n' { Leol }

| prim { Lprim (Lexing.lexeme lexbuf) }

|  ':' { Leval }
|  '(' { Lpar }
|  ')' { Rpar }
|  '<' { Lang }
|  '>' { Rang }
|  '[' { Lsqu }
|  ']' { Rsqu }
|  ',' { Lcom }
|  ';' { Lscl }
 
|  '@' { Lapplytoall }
|  'o' { Lo }
|  "->" { Lcond }
|  '~' { Lcst }
|  '/' { Lins }
|  "bu" { Lbu }
|  "while" { Lwhile }
 
|  "T" { LT }
|  "F" { LF }

|  '='        { Laff }
|  "Def"      { LDef }
|  "Undef"    { LUndef }
|  "Show"     { LShow }
|  "Quit"     { LQuit }
|  "Load"     { LLoad }
|  "Save"     { LSave }

|  ['0'-'9']+ 's' { Ls (int_sel (Lexing.lexeme lexbuf)) }
|  ['0'-'9']+ 'r' { Lr (int_sel (Lexing.lexeme lexbuf)) }

| ['-']?['0'-'9']+            { Lint (int_of_string (Lexing.lexeme lexbuf)) }
| ['_']['A'-'z' '0'-'9']+     { Lvar (Lexing.lexeme lexbuf) }
| ['A'-'z']['A'-'z' '0'-'9']* { Lident (Lexing.lexeme lexbuf) }
| '"' [^'"']* '"'             { Lstr (string_chars (Lexing.lexeme lexbuf)) }
