(* This file defines integers A and B
   and the fact function. 
   (* by the way, functions nest properly in SML! *) 
 *)

val a = 2+3;

val b = 2*a;

fun fact n = (* a recursive factorial function *)
  if n = 0 then
    1
  else
    n * fact (n - 1)
    
      
	
