use "../utils/FATest.sml";

structure RegTest = struct 

  (* First, some testing functions from regular expression lecture *)
  fun testWeakSimplify s = Reg.toString(Reg.weakSimplify(Reg.fromString s));

  fun testSimplify s = 
    Reg.toString(Reg.simplify Reg.weakSubset (Reg.fromString s));

  fun testTraceSimplify s = 
    Reg.toString(Reg.traceSimplify Reg.weakSubset (Reg.fromString s));

  fun regStringToFA regString = FA.fromReg (Reg.fromString regString)

  (* Test to see if the language specified by regString contains testString 
     Abstract over handling annoying "%" (as opposed to "") in testString *)
  fun testOnString regString testString = 
    FATest.testOnString (regStringToFA regString) testString

  (* Return a list of all testStrings on which regString and pred return different results *)
  fun mismatches regString pred testStrings = 
    FATest.mismatches (regStringToFA regString) pred testStrings

  (* Tests all strings with both regString and pred. If they agree on all tests,
     print a message saying this and return true.  Otherwise, print out
     results for every mismatch and return false. *)
  fun testOnStrings regString pred strings = 
    let val testReg =  FATest.testOnString (regStringToFA regString) 
      fun printMiss miss = 
      (print ("Mismatch -- \"" ^ miss 
	      ^ "\": regexp says " ^ (Bool.toString (testReg miss))
	      ^ "; pred says " ^ (Bool.toString (pred miss))
	      ^ "\n"))
	val misses = mismatches regString pred strings 
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
  fun testOnAlphabet regString pred chars n = 
    testOnStrings regString pred (genStrings (n,chars))


end
