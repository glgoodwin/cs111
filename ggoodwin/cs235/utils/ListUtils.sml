structure ListUtils = struct

fun listToString eltToString xs = "[" ^ (eltsToString eltToString xs) ^ "]"

and eltsToString eltToString [] = ""
  | eltsToString eltToString [x] = eltToString x
  | eltsToString eltToString (x::xs) = (eltToString x) ^ " " ^  (eltsToString eltToString xs)

end
