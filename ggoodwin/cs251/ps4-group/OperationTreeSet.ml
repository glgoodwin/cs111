(* Problem Set 4 - Group: Hanhong Lu & Goodwin Gabrielle 
A tree-based representation of sets that remembers
the structure of set operations created the set. *)

module OperationTreeSet : SET = struct

  module LSU = ListSetUtils

  type 'a set = 
      Empty 
    | Insert of 'a * 'a set
    | Delete of 'a * 'a set
    | Union  of 'a set * 'a set
    | Intersection of 'a set * 'a set
    | Difference of 'a set * 'a set

  let empty = Empty 
  let insert x s = Insert(x,s)
  let singleton x = Insert(x, Empty)
  let delete x s = Delete(x, s)
  let union s1 s2 = Union(s1,s2)
  let intersection s1 s2 = Intersection(s1,s2)
  let difference s1 s2 = Difference(s1,s2)

  let rec toList s = (* Replace this stub. You may use operations in ListSetUtils, 
                        using the abbreviation LSU defined above. *)
    match s with 
      Empty -> []
    | Insert(y,s') -> LSU.insert y (toList s')
    | Delete(y,s') -> LSU.delete y (toList s')
    | Union(s1,s2) -> LSU.union (toList s1) (toList s2)
    | Intersection(s1,s2) -> LSU.intersection (toList s1) (toList s2)
    | Difference(s1,s2) -> LSU.difference (toList s1) (toList s2)

  let rec splithalf xs n i = 
    match xs with
      [] -> ([],[])
    | x::xs' -> 
	let (a,b) = splithalf xs' n (i+1) in 
	if i<n then
	  (x::a,b)
	else
	  (a,x::b)
    
  let rec fromList xs =
    match xs with
      [] -> Empty
    | [x] -> Insert(x,Empty)
    | x::xs' -> let (s1,s2) = splithalf xs ((List.length xs) / 2) 0 in
      Union(fromList s1,fromList s2)
      
  let rec member x s = (* Replace this stub. Do *not* use toList in this definition! *)
    match s with 
      Empty -> false
    | Insert(y,s') -> (x = y) || (member x s')
    | Delete(y,s') -> member x s'
    | Union(s1,s2) -> (member x s1) || (member x s2)
    | Intersection(s1,s2) -> (member x s1) && (member x s2)
    | Difference(s1,s2) -> (member x s1) && (not (member x s2))

  let size s = List.length (toList s)

  let isEmpty s = if (toList s) = [] then true else false

  let rec toSexp eltToSexp s = 
    match s with 
      Empty -> Sexp.Seq [Sexp.Sym "empty"]
    | Insert(y,s') -> Sexp.Seq [Sexp.Sym "insert"; eltToSexp y; toSexp eltToSexp s']
    | Delete(y,s') -> Sexp.Seq [Sexp.Sym "delete"; eltToSexp y; toSexp eltToSexp s']
    | Union(s1,s2) -> Sexp.Seq [Sexp.Sym "union"; toSexp eltToSexp s1; toSexp eltToSexp s2]
    | Difference(s1,s2) -> Sexp.Seq [Sexp.Sym "difference"; toSexp eltToSexp s1; toSexp eltToSexp s2]
    | Intersection(s1,s2) -> Sexp.Seq [Sexp.Sym "intersection"; toSexp eltToSexp s1; toSexp eltToSexp s2]

  let rec fromSexp eltFromSexp sexp = 
    match sexp with
      Sexp.Seq [Sexp.Sym "empty"] -> Empty
    | Sexp.Seq [Sexp.Sym "insert"; y; (s')] -> Insert(eltFromSexp y, fromSexp eltFromSexp s')
    | Sexp.Seq [Sexp.Sym "delete"; y; (s')] -> Delete(eltFromSexp y, fromSexp eltFromSexp s')
    | Sexp.Seq [Sexp.Sym "union"; (s1); (s2)] -> Union(fromSexp eltFromSexp s1,fromSexp eltFromSexp s2)
    | Sexp.Seq [Sexp.Sym "difference"; (s1); (s2)] -> Difference(fromSexp eltFromSexp s1,fromSexp eltFromSexp s2)
    | Sexp.Seq [Sexp.Sym "intersection"; (s1); (s2)] -> Intersection(fromSexp eltFromSexp s1, fromSexp eltFromSexp s2)
    | _ -> raise (Failure ("OperationTreeSet.fromExp -- can't handle sexp:\n" ^ (Sexp.sexpToString sexp)))

  let rec toString eltToString s = StringUtils.listToString eltToString (toList s)

end
