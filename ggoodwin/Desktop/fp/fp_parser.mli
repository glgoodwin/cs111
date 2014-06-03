type token =
  | Lprim of (string)
  | Leval
  | Lpar
  | Rpar
  | Lang
  | Rang
  | Lsqu
  | Rsqu
  | Lcom
  | Lscl
  | Leol
  | Lapplytoall
  | Lins
  | Lo
  | Lcond
  | Lcst
  | Lbu
  | Lwhile
  | LT
  | LF
  | Laff
  | LDef
  | LUndef
  | LShow
  | LQuit
  | LLoad
  | LSave
  | Ls of (int)
  | Lr of (int)
  | Lint of (int)
  | Lident of (string)
  | Lvar of (string)
  | Lstr of (string)

val cmd :
  (Lexing.lexbuf  -> token) -> Lexing.lexbuf -> Fp.cmd
val exp :
  (Lexing.lexbuf  -> token) -> Lexing.lexbuf -> Fp.expr
val fct :
  (Lexing.lexbuf  -> token) -> Lexing.lexbuf -> Fp.fct
