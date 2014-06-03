structure PDATest = struct 

(* This code is adapted from Chelsea Hoover '11's testing code for the 
   Forlan PDA module she developed. *)
   
(* replaces the empty str with "" instead of "%" for pred testing purposes*)
fun strToString s = if (Str.equal (s,Str.fromString "%")) then "" else Str.toString s

(* generate all Strs of up to length n from symbols in syms *)
fun genStrs syms n =
    if (n <= 0) then 
	[Str.fromString "%"]
    else
	[] :: (List.concat (List.map 
			    (fn s => List.map 
			              (fn c => c :: s) 
				      syms)
			    (genStrs syms (n-1))))

(* Adapted from Chelsea's pda/pred tester *)
(* Tests all strings from PDA alphabet of up to length n with both pda and pred. 
   If they agree on all tests, print a message saying this and return true.  
   Otherwise, print out results for every mismatch and return false. *)
fun comparePDAPred pda pred n =
    let	val alphabet = let val a = (PDA.alphabet pda) in 
			   case (Set.toList a) of 
			       [] => (print ("Warning: alphabet of pda "^
				      " is empty; only the empty "^
				      "string will be tested.\n"); a)
			     | _ => a
		       end
	val strs = genStrs (Set.toList alphabet) n
	val gram = PDA.toGram pda (* Convert PDA to gram for testing *)
	fun test s = Gram.generated gram s
	val results = List.map (fn s => (s,test s,pred (strToString s))) strs
	val misses = List.filter (fn (s,b1,b2) => (b1 = not b2)) results
	fun printMiss (str, pdaTest, predTest) = 
	    (print ("Mismatch -- \"" ^ (strToString str)
		    ^ "\": PDA says " ^ (Bool.toString pdaTest)
		    ^ "; pred says " ^ (Bool.toString predTest)
		    ^ "\n"))
    in if List.null misses then 
	(print ("Passed all test cases for strings up to length "
		^ (Int.toString n) ^ "\n");
	 true)
       else 
	   (List.app printMiss misses; false)
    end

fun comparePDAFilePred filename pred n = 
    let val errorstring = "Error reading file: "^filename^"\n"
	val pda = PDA.input filename handle Error => (print errorstring; raise Error)
    in comparePDAPred pda pred n
    end

end (* of struct *)
