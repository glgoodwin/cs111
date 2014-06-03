module FunMatrix : MATRIX = struct

  open FunUtils
  open ListUtils

  type 'a matrix = int -> int -> 'a option

  let make rows cols f = 
    (* replace this stub *)
    fun i j -> None

  let dimensions m =
    (* replace this stub *)
    (0, 0)

  let get i j m = 
    (* replace this stub *)
    None

  let put i j v m = 
    (* replace this stub *)
    m

  let transpose m = 
    (* replace this stub *)
    m

  let toLists m = 
    (* replace this stub *)
    [[]]

  let map f m = 
    (* replace this stub *)
    fun i j -> None

end
