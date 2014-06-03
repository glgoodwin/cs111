structure DFATest = struct 

(* Determine whether the given DFA accepts the specified string. 
   Abstracts over handling annoying "%" (as opposed to "") *)
fun testOnString dfa string = 
  DFA.accepted dfa (Str.fromString (if string = "" then "%" else string))

(* Return a list of all strings on which dfa and pred return different results *)
fun mismatches dfa pred strings = 
  List.filter (fn s => (testOnString dfa s) <> (pred s))
              strings

(* Tests all strings with both dfa and pred. If they agree on all tests,
   print a message saying this and return true.  Otherwise, print out
   results for every mismatch and return false. *)
fun testOnStrings dfa pred strings = 
  let fun printMiss miss = 
        (print ("Mismatch -- \"" ^ miss 
                 ^ "\": DFA says " ^ (Bool.toString (testOnString dfa miss))
                 ^ "; pred says " ^ (Bool.toString (pred miss))
                 ^ "\n"))
      val misses = mismatches dfa pred strings 
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

(* Tests all strings of up to length n over alphabet chars with both dfa and pred. 
   If they agree on all tests, print a message saying this and return true.  
   Otherwise, print out results for every mismatch and return false. *)
fun testOnAlphabet dfa pred chars n = 
  testOnStrings dfa pred (genStrings (n,chars))

end
