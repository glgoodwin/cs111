
val banner : string
type fct =
    Prim of string
  | Sel of int
  | RSel of int
  | User of string
  | ApplyToAll of fct
  | Insert of fct
  | Construction of fct list
  | Composition of fct * fct
  | Condition of fct * fct * fct
  | Constant of expr
  | Bu of fct * expr
  | While of fct * fct
and expr =
    Bottom
  | Void
  | T
  | F
  | Var of string
  | Int of Num.num
  | Seq of expr list
  | App of fct * expr
and cmd =
    Def of string * fct
  | Undef of string
  | Show of string
  | Exp of expr
  | Quit
  | Load of string
  | Save of string
  | None
val string_of_fct2 : fct -> bool -> string
val string_of_expr : expr -> string
val string_of_fct : fct -> string
val print_expr : expr -> unit
val print_fct : fct -> unit
val defs : (string, fct) Hashtbl.t
val add_user_def : string -> fct -> unit
val get_user_def : string -> fct
val del_user_def : string -> unit
exception Eval_bottom of expr
exception Eval_error of expr
exception Eval_bug of string
val applyToAll : fct -> expr -> expr
val insert : fct -> expr -> expr
val transpose : expr -> expr
val match_seq : expr -> (expr list -> 'a) -> 'a
val eval_arith : string -> expr -> expr -> expr
val eval : expr -> expr
val eval_fct : fct -> expr -> expr
val eval_prim : string -> expr -> expr
