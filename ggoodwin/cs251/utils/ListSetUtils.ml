(* Functions for handling sets represented as sorted lists without duplicates *)

module type LIST_SET_UTILS = sig

  val member: 'a -> 'a list -> bool
  val insert: 'a -> 'a list -> 'a list
  val delete: 'a -> 'a list -> 'a list
  val union: 'a list -> 'a list -> 'a list
  val intersection: 'a list -> 'a list -> 'a list
  val difference: 'a list -> 'a list -> 'a list

end

module ListSetUtils : LIST_SET_UTILS = struct

  let rec member x ys = 
    match ys with 
      [] -> false
    | y::ys' -> (x = y) || ((x > y) && (member x ys'))

  (* Insert an element into a sorted list *)
  let rec insert x ys = 
    match ys with 
      [] -> [x]
    | y::ys' -> if x < y then x::ys
                else if x = y then ys
                else y::(insert x ys')

  (* Delete an element from a sorted list *)
  let rec delete x ys = 
    match ys with 
      [] -> []
    | y::ys' -> if x = y then ys'
                else if x < y then ys
                else y::(delete x ys')

  (* Merge two sorted lists, removing duplicates *)
  let rec union xs ys = 
    match (xs, ys) with 
      ([], _) -> ys
    | (_, []) -> xs
    | (x::xs',y::ys') -> if x = y then x::(union xs' ys')
                         else if x < y then x::(union xs' ys)
                         else y::(union xs ys')

  (* Intersection of two sorted lists *)
  let rec intersection xs ys = 
    match (xs, ys) with 
      ([], _) -> []
    | (_, []) -> []
    | (x::xs',y::ys') -> if x = y then x::(intersection xs' ys')
                         else if x < y then intersection xs' ys
                         else intersection xs ys'

  (* Difference of two sorted lists *)
  let rec difference xs ys = 
    match (xs, ys) with 
      ([], _) -> []
    | (_, []) -> xs
    | (x::xs',y::ys') -> if x = y then difference xs' ys
                         else if x < y then x::(difference xs' ys)
                         else difference xs ys'

end
