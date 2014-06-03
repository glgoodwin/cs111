structure RegTest = struct 

(* A simple tester for a regular expression specified by regExpString. 
   Returns all the elements of testStrings in the language specified
   by the regular expression in regExpString. 
   You don't have to understand this code until later in the course. *)
fun testRegExpString regExpString testStrings = 
  let val inLangOfRegExp = 
        (FA.accepted (FA.fromReg (Reg.fromString regExpString))) o 
	(fn s => Str.fromString (if s = "" then "%" else s))
  in List.filter inLangOfRegExp testStrings
  end


(* Abstract over handling annoying "%" (as opposed to "") *)
fun testOnString dfa string = 
  FA.accepted dfa (Str.fromString (if string = "" then "%" else string))

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
        fun postpendAllChars str = map (postpendChar str) cs
    in "" :: (List.concat (List.map postpendAllChars (genStrings (n-1, cs))))
    end

end
