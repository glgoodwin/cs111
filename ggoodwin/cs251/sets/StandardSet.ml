(* Note: The result of applying this functor does not satisfy the SET signature,
   because it is instantiated to a particular element type rather than working
   for any element type *)

module StandardSet (Ord: Set.OrderedType) = ((struct

  module SM = Set.Make(Ord)

  type set = SM.t
 
  let empty = SM.empty
  
  let singleton x = SM.singleton x

  let insert x s = SM.add x s

  let delete x s = SM.remove x s

  let size s = SM.cardinal s

  let isEmpty s = SM.is_empty s

  let member x s = SM.mem x s
 
  let union s1 s2 = SM.union s1 s2

  let intersection s1 s2 = SM.inter s1 s2

  let difference s1 s2 = SM.diff s1 s2

  let toList s = SM.elements s

  let fromList xs = List.fold_right SM.add xs SM.empty 

  let fromSexp eltFromSexp sexp = 
    match sexp with 
      Sexp.Seq elts -> fromList (List.map eltFromSexp elts)
    | _ -> raise (Failure "unrecognized s-expression")

  let toSexp eltToSexp s = Sexp.Seq(List.map eltToSexp (toList s))

  let toString eltToString s = 
    StringUtils.listToString eltToString (SM.elements s)

end) : 
  (sig
    type set = Set.Make(Ord).t
    val empty : set
    val singleton : Ord.t -> set
    val insert : Ord.t -> set -> set
    val delete : Ord.t -> set -> set
    val member : Ord.t -> set -> bool
    val isEmpty : set -> bool
    val size : set -> int
    val union : set -> set -> set
    val intersection : set -> set -> set
    val difference : set -> set -> set
    val toList : set -> Ord.t list
    val fromList : Ord.t list -> set
    val fromSexp : (Sexp.sexp -> Ord.t) -> Sexp.sexp -> set
    val toSexp : (Ord.t -> Sexp.sexp) -> set -> Sexp.sexp
    val toString : (Ord.t -> string) -> set -> string
  end
  ))
  
