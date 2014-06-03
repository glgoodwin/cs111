structure GramTest = struct 

(* Determine whether the given grammar generates the specified string. 
   Abstracts over handling annoying "%" (as opposed to "") *)
fun testOnString gram string = 
  Gram.generated gram (Str.fromString (if string = "" then "%" else string))

(* Return a list of all strings on which gram and pred return different results *)
fun mismatches gram pred strings = 
  List.filter (fn s => (testOnString gram s) <> (pred s))
              strings

(* Tests all strings with both gram and pred. If they agree on all tests,
   print a message saying this and return true.  Otherwise, print out
   results for every mismatch and return false. 
   MSG_GRAM is the name of the grammar; MSG_PRED is the name of the predicate *)
fun testOnStrings' msg_gram msg_pred gram pred strings = 
  let fun printMiss miss = 
        (print ("Mismatch -- \"" ^ miss 
                 ^ "\": " ^ msg_gram ^ " says " ^ (Bool.toString (testOnString gram miss))
                 ^ "; " ^ msg_pred ^ " says " ^ (Bool.toString (pred miss))
                 ^ "\n"))
      val misses = mismatches gram pred strings 
   in if misses = [] then 
         (print "Passed all test cases\n"; true)
      else 
         (List.app printMiss misses; false)
  end

(* Tests all strings with both gram and pred. If they agree on all tests,
   print a message saying this and return true.  Otherwise, print out
   results for every mismatch and return false. *)
fun testOnStrings gram pred strings = testOnStrings' "grammar" "pred" gram pred strings

(* Generate all strings of up to length n from characters in cs *)
fun genStrings (n, cs) = 
  if n <= 0 then
    [""] 
  else
    let fun postpendChar s c = s ^ (String.str c)
        fun postpendAllChars str = List.map (postpendChar str) cs
    in "" :: (List.concat (List.map postpendAllChars (genStrings (n-1, cs))))
    end

(* Tests all strings of up to length n over alphabet chars with both gram and pred. 
   If they agree on all tests, print a message saying this and return true.  
   Otherwise, print out results for every mismatch and return false. *)
fun testOnAlphabet gram pred chars n = 
  testOnStrings gram pred (genStrings (n,chars))

fun parseString gram string = 
  let val pt = Gram.parseStr gram 
                 (Str.fromString (if string = "" then "%" else string))
  in PT.output("", pt); pt
  end



end