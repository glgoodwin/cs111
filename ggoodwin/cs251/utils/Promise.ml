(* Promises = memoized thunks *)
module type PROMISE = sig
  type 'a promise
  val makePromise : (unit -> 'a) -> 'a promise
  val force: 'a promise -> 'a
end

module Promise: PROMISE = struct

  type 'a promise = (unit -> 'a)

  let makePromise thunk = 
    let memo = ref None in 
    fun () -> 
      (match !memo with
	 Some v -> v
       | None -> 
	   let v = thunk() in 
	   let _ = memo := Some v in 
	   v)

  let force promise = promise()

end

