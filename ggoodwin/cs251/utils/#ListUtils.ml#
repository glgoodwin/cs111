(* Examples of higher-order list functions in OCAML *)

(* [lyn, 3/9/08] Changed map, map2, map3 to force left-to-right 
   processing of elements. *)

module type LIST_UTILS = sig
  val ana : ('a -> ('b * 'a) option) -> 'a -> 'b list
  val app : ('a -> unit) -> 'a list -> unit
  val drop : int -> 'a list -> 'a list
  val exists : ('a -> bool) -> 'a list -> bool 
  val exists2 : ('a -> 'b -> bool) -> 'a list -> 'b list -> bool
  val filter : ('a -> bool) -> 'a list -> 'a list 
  val flatten : 'a list list -> 'a list
  val foldr : ('a -> 'b -> 'b) -> 'b -> 'a list -> 'b
  val foldr' : ('a -> 'a list -> 'b -> 'b) -> 'b -> 'a list -> 'b
  val foldr2 : ('a -> 'b -> 'c -> 'c) -> 'c -> 'a list -> 'b list -> 'c
  val foldl : 'a -> ('a -> 'b -> 'a) -> 'b list -> 'a 
  val foldl2 : 'a -> ('a -> 'b -> 'c -> 'a) -> 'b list -> 'c list -> 'a 
  val for_all : ('a -> bool) -> 'a list -> bool 
  val for_all2 : ('a -> 'b -> bool) -> 'a list -> 'b list -> bool
  val for_each : ('a -> 'b) -> 'a list -> unit
  val for_each2 : ('a -> 'b -> 'c) -> 'a list -> 'b list -> unit 
  val gen : ('a -> 'a) -> ('a -> bool) -> 'a -> 'a list 
  val iterate : ('a -> 'a) -> ('a -> bool) -> 'a -> 'a
  val last : 'a list -> 'a
  val map : ('a -> 'b) -> 'a list -> 'b list
  val map_iter : ('a -> 'b) -> 'a list -> 'b list
  val map2 : ('a -> 'b -> 'c) -> 'a list -> 'b list -> 'c list
  val map3 : ('a -> 'b -> 'c -> 'd) -> 'a list -> 'b list -> 'c list -> 'd list
  val range : int -> int -> int list
  val rev : 'a list -> 'a list
  val some : ('a -> bool) -> 'a list -> 'a option
  val some2 : ('a -> 'b -> bool) -> 'a list -> 'b list -> ('a * 'b) option
  val take : int -> 'a list -> 'a list
  val zip : ('a list * 'b list) -> ('a * 'b) list
  val zip3 : ('a list * 'b list * 'c list) -> ('a * 'b * 'c) list
  val unzip : ('a * 'b) list -> ('a list * 'b list) 
  val unzip3 : ('a * 'b * 'c) list -> ('a list * 'b list * 'c list) 
end

module ListUtils : LIST_UTILS = struct

  open FunUtils

  (* Returns the first n elements of the list *)
  let rec take n xs = 
    if n <= 0 then 
      []
    else match xs with 
      [] -> []
    | (x::xs') -> x::(take (n-1) xs')

  (* Returns all but the first n elements of the list *)
  let rec drop n xs = 
    if n <= 0 then 
      xs
    else match xs with 
      [] -> []
    | (x::xs') -> drop (n-1) xs'

  (* Returns the last element of a list *)
  let rec last xs = 
    match xs with 
      [] -> raise (Failure "Attempt to find the last element of a non-empty list")
    | [x] -> x
    | x::xs' -> last xs'

  (* Equivalent to List.map *)
  let rec map f xs = 
    match xs with 
      [] -> [] 
    | (x::xs') -> let fx = (f x) in fx :: map f xs'

  (* Like List.map2 except does not raise exception for lists of unequal length.
     Instead, resulting list has length that is shorter of two lists. *)
  let rec map2 f xs ys = 
    match (xs,ys) with 
      ([], _) -> []
    | (_, []) -> []
    | (x::xs',y::ys') -> let fxy = (f x y) in fxy :: map2 f xs' ys'

  let rec map3 f xs ys zs= 
    match (xs,ys,zs) with 
      ([], _, _) -> []
    | (_, [], _) -> []
    | (_, _, []) -> []
    | (x::xs',y::ys',z::zs') -> let fxyz = (f x y z) in fxyz :: map3 f xs' ys' zs'

  (* Like List.combine except (1) this zip is tupled rather than curried and
     (2) this zip allows lists of unequal length (result has length of shorter
     of the two lists *) 
  let zip (xs,ys) = map2 pair xs ys

  let zip3 (xs,ys,zs) = map3 triple xs ys zs

  (* Equivalent to List.filter *)
  let rec filter p xs = 
    match xs with 
      [] -> [] 
    | x :: xs'-> if p x then x :: filter p xs' else filter p xs' 

  (* Like List.fold_right, except takes its arguments in a different order *)
  (* This is a so-called "catamorphism" *)
  let rec foldr binop null xs = 
    match xs with 
      [] -> null
    | x :: xs' -> binop x (foldr binop null xs')

  (* foldr' is like foldr, except that is supplies the rest of list to the glue
    in addition to the head and the result. This is a so-called "paramorphism". 
    There is no equivalent in the List module. *)
  let rec foldr' ternop null xs = 
    match xs with 
      [] -> null
    | x :: xs' -> ternop x xs' (foldr' ternop null xs')

  (* Two-argument version of foldr *)
  let rec foldr2 ternop null xs ys= 
    match (xs,ys) with 
      ([], _) -> null
    | (_, []) -> null
    | (x :: xs', y::ys') -> ternop x  y (foldr2 ternop null xs' ys')

  (* Equivalent to List.flatten *)
  let flatten xss = foldr (@) [] xss

  (* Like List.fold_left, except takes its arguments in a different order *)
  let rec foldl ans binop xs = 
    match xs with 
      [] -> ans
    | x :: xs' -> foldl (binop ans x) binop xs' 

  (* Equivalent to List.rev *)
  let rev xs = foldl [] (flip cons) xs

  (* Two-argument version of foldl *)
  let rec foldl2 ans ternop xs ys = 
    match (xs,ys) with 
      ([],_) -> ans
    | (_,[]) -> ans
    | (x::xs',y::ys') -> foldl2 (ternop ans x y) ternop xs' ys'

  (* Equivalent to List.for_all *)
  let rec for_all p xs =
    match xs with 
      [] -> true
    | x::xs' -> (p x) && for_all p xs' 

  (* Equivalent to List.app *)
  let rec for_each f xs =
    match xs with 
      [] -> ()
    | x::xs' -> let _ = f x in for_each f xs' (* force left-to-right evaluation *)

  (* Equivalent to List.exists *)
  let rec exists p xs =
    match xs with 
      [] -> false 
    | x::xs' -> (p x) || exists p xs' 

  (* Returns (using Some) the first element in xs satisfying predicate p, 
     and None if there is no such element. No equivalent in the List.module *)
  let rec some p xs =
    match xs with 
      [] -> None
    | x::xs' when p x -> Some x
    | x::xs' -> some p xs'

  let oneListOpToTwoListOp f =
    let twoListOp binop xs ys = f (uncurry binop) (zip(xs,ys))
     in twoListOp

  let for_all2 pred = oneListOpToTwoListOp for_all pred

  let for_each2 fcn = oneListOpToTwoListOp for_each fcn

  let exists2 pred = oneListOpToTwoListOp exists pred

  let some2 pred = oneListOpToTwoListOp some pred

  (* No equivalent in List module *)
  let rec gen next isDone seed = 
    if isDone seed then 
      []
    else 
      seed :: (gen next isDone (next seed))

  (* No equivalent in List module *)
  let range lo hi = gen ((+) 1) ((<) hi) lo 

  (* Similar to gen, but only returns the final state of an iteration *)
  let rec iterate next isDone state = 
    if isDone state then
      state
    else 
      iterate next isDone (next state)

  (* Generalization of gen, a so-called "anamorphism" *)
  let rec ana g seed = 
    match g seed with 
      None -> []
    | Some(h,seed') -> h:: ana g seed'

  (* Equivalent to List.split *)
  let unzip ps = foldr (fun (x,y) (xs, ys) -> (x::xs, y::ys)) ([],[]) ps

  let unzip3 ts = foldr (fun (x,y,z) (xs, ys, zs) -> (x::xs, y::ys, z::zs)) ([],[],[]) ts

  (* A version of map using constant stack space *)
  let map_iter f xs = 
    let rec loop ys revs = 
      match ys with 
        [] -> rev revs
      |	(y::ys') -> loop ys' ((f y) :: revs)
    in loop xs []

  (* Apply a unit-returning function to each element of a list
     in order from left to right *)
  let rec app f xs = 
    match xs with
      [] -> ()
    | x::xs' -> let _ = f x in app f xs'

end

