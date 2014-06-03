module type MATRIX = sig
  type 'a matrix
  val make : int -> int -> (int -> int -> 'a) -> 'a matrix 
      (* in make rows cols f, assume both rows and cols must be >= 1 *)
  val dimensions : 'a matrix -> (int * int)
  val get : int -> int -> 'a matrix -> 'a option
  val put : int -> int -> 'a -> 'a matrix -> 'a matrix
  val transpose : 'a matrix -> 'a matrix
  val toLists : 'a matrix -> 'a list list
  val map : ('a -> 'b) -> 'a matrix -> 'b matrix
end

