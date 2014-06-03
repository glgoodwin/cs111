module SortedListSet : SET = struct

  module LSU = ListSetUtils (* Abbreviation for list set utilities *)

  type 'a set = 'a list 

  let empty = [] 
  
  let singleton x = [x] 

  let insert x s = LSU.insert x s

  let delete x s = LSU.delete x s

  let size s = List.length s

  let isEmpty s = (s = []) 

  let member x s = LSU.member x s

  let union s1 s2 = LSU.union s1 s2

  let intersection s1 s2 = LSU.intersection s1 s2

  let difference s1 s2 = LSU.difference s1 s2

  let toList s = s 

  let fromList xs = List.fold_right insert xs empty 

  let fromSexp eltFromSexp sexp = 
    match sexp with 
      Sexp.Seq elts -> List.map eltFromSexp elts
    | _ -> raise (Failure "unrecognized s-expression")

  let toSexp eltToSexp xs = Sexp.Seq(List.map eltToSexp xs)

  let toString eltToString s = StringUtils.listToString eltToString s

end
