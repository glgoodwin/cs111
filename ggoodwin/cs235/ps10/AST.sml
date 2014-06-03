structure AST = struct

  datatype typ = Base of Token.baseTy
    | List of typ
    | Prod of typ * typ
    | Arrow of typ * typ

  (* Construct a fully-parenthesized infix type string *)
  fun typToString (Base(b)) = Token.baseTyToString b
    | typToString (List(t)) = "(" ^ (typToString t) ^ " list)"
    | typToString (Prod(t1,t2)) = "(" ^ (typToString t1) ^ " * " ^ (typToString t2) ^ ")" 
    | typToString (Arrow(t1,t2)) = "(" ^ (typToString t1) ^ " -> " ^ (typToString t2) ^ ")" 

  (* Construct a string for a list of types *)
  fun typListToString t = "[" ^ (typEltsToString t) ^ "]"

  and typEltsToString [] = ""
    | typEltsToString [t] = typToString t
    | typEltsToString (t::ts) = (typToString t) ^ "," ^ (typEltsToString ts)

end
