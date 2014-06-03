module type RANDOM_UTILS = sig
  val randomizeArray : 'a array -> unit
  val randomizeList : 'a list -> 'a list
end

module RandomUtils : RANDOM_UTILS = struct

  (* swap the elements at two indices in an array *)
  let swap a i j = 
    let a_i = Array.get a i in 
    let a_j = Array.get a j in 
    let _ = Array.set a i a_j in 
    let _ = Array.set a j a_i in 
    ()

  let randomizeArray a = 
    let _ = Random.self_init() in (* initialize random number generator *)
    let rec loop i = 
      if i = 0 then 
        ()
      else 
	let _ = swap a i (Random.int (i+1)) in 
	loop (i-1)
    in loop ((Array.length a) - 1)

  let randomizeList xs = 
    let a = Array.of_list xs in 
    let _ = randomizeArray a in 
    Array.to_list a
    
end

