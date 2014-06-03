module type FUN_UTILS = sig
  val cons : 'a -> 'a list -> 'a list
  val curry : ('a * 'b -> 'c) -> 'a -> 'b -> 'c
  val pair : 'a -> 'b -> 'a * 'b
  val triple : 'a -> 'b -> 'c -> 'a * 'b * 'c
  val flip : ('a -> 'b -> 'c) -> ('b -> 'a -> 'c) 
  val id : 'a -> 'a
  val iterate : ('a -> 'a) -> ('a -> bool) -> 'a -> 'a
  val o : ('b -> 'c) -> ('a -> 'b) -> ('a -> 'c) 
  val uncurry : ('a -> 'b -> 'c) -> 'a * 'b -> 'c
  val valOf : 'a option -> 'a
end

module FunUtils : FUN_UTILS = struct

  let cons x xs = x :: xs

  let pair x y = (x , y)

  let triple x y z= (x , y, z)

  let flip f a b = f b a 

  (* function composition *)
  let o f g x = f (g x)

  (* identity function *)
  let id x = x

  let curry f a b = f(a,b)

  let uncurry f (a,b) = (f a b)

  let valOf opt = 
    match opt with 
      Some v -> v
    | None -> raise (Failure "valOf of None")

  (* Similar to ListUtils.gen, but only returns the final state of an iteration *)
  let rec iterate next isDone state = 
    if isDone state then
      state
    else 
      iterate next isDone (next state)

end
