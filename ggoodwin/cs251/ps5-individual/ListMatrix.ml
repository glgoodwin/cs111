module ListMatrix : MATRIX = struct

  open FunUtils
  open ListUtils

  type 'a matrix = 'a list list 

  let make rows cols f = 
    (* replace this stub *)
    [[]]

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
    [[]]

end
