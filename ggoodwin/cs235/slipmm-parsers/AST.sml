structure AST = struct

  datatype pgm = Pgm of stm

  and stm =
    Assign of string * exp
  | Print of exp
  | Seq of stm list 

  and exp = 
    Id of string
  | Int of int
  | BinApp of exp * Token.binop * exp

end
