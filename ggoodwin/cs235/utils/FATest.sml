structure FATest = struct 

(* Determine whether the given FA accepts the specified string. 
   Abstracts over handling annoying "%" (as opposed to "") *)
fun testOnString fa string = 
  FA.accepted fa (Str.fromString (if string = "" then "%" else string))

(* Return a list of all strings on which fa and pred return different results *)
fun mismatches fa pred strings = 
  List.filter (fn s => (testOnString fa s) <> (pred s))
              strings

(* Tests all strings with both fa and pred. If they agree on all tests,
   print a message saying this and return true.  Otherwise, print out
   results for every mismatch and return false. *)
fun testOnStrings fa pred strings = 
  let fun printMiss miss = 
        (print ("Mismatch -- \"" ^ miss 
                 ^ "\": FA says " ^ (Bool.toString (testOnString fa miss))
                 ^ "; pred says " ^ (Bool.toString (pred miss))
                 ^ "\n"))
      val misses = mismatches fa pred strings 
   in if misses = [] then 
         (print "Passed all test cases\n"; true)
      else 
         (List.app printMiss misses; false)
  end

(* Generate all strings of up to length n from characters in cs *)
fun genStrings (n, cs) = 
  if n <= 0 then
    [""] 
  else
    let fun postpendChar s c = s ^ (String.str c)
        fun postpendAllChars str = List.map (postpendChar str) cs
    in "" :: (List.concat (List.map postpendAllChars (genStrings (n-1, cs))))
    end

(* Tests all strings of up to length n over alphabet chars with both fa and pred. 
   If they agree on all tests, print a message saying this and return true.  
   Otherwise, print out results for every mismatch and return false. *)
fun testOnAlphabet fa pred chars n = 
  testOnStrings fa pred (genStrings (n,chars))

end
