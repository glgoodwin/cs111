
(**********************************************************************
 This is the SML code skeleton file ps5.sml
 **********************************************************************)

(* load DFA testing utilities from class:
   testOnString, testOnStrings, mismatches, genStrings *)
use "../utils/DFATest.sml"; 
use "../utils/FATest.sml";  (* FA version of the above *)
open DFATest; (* open the module, so don't need 'DFATest.' qualifier *)

(* Useful helper functions *)
val faToDFA = nfaToDFA o efaToNFA o faToEFA
val dfaToFA = EFA.injToFA o NFA.injToEFA o DFA.injToNFA
fun revString s = String.implode (List.rev (String.explode s))

(********** Froblem 1 **********)

fun test_ab_and_bc n = 
   testOnStrings (DFA.input "ab_and_bc.dfa")
                 (fn s => (String.isSubstring "ab" s) 
                           andalso (String.isSubstring "bc" s))
                 (genStrings(n, [#"a", #"b", #"c"]))

fun test_ab_or_bc n = 
   testOnStrings (DFA.input "ab_or_bc.dfa")
                 (fn s => (String.isSubstring "ab" s) 
                           orelse (String.isSubstring "bc" s))
                 (genStrings(n, [#"a", #"b", #"c"]))

fun test_ab_minus_bc n = 
   testOnStrings (DFA.input "ab_minus_bc.dfa")
                 (fn s => (String.isSubstring "ab" s) 
                           andalso (not (String.isSubstring "bc" s)))
                 (genStrings(n, [#"a", #"b", #"c"]))

fun test_neither_ab_nor_bc n = 
   testOnStrings (DFA.input "neither_ab_nor_bc.dfa")
                 (fn s => (not (String.isSubstring "ab" s))
                           andalso (not (String.isSubstring "bc" s)))
                 (genStrings(n, [#"a", #"b", #"c"]))

(********** Froblem 2 **********)

fun isHamming n = (n mod 2) = 0 orelse (n mod 3) = 0 orelse (n mod 5) = 0

(* A string of digits is nonstandard if it's empty or is 0 followed by another digit *)
  
fun nonstandard digits = 
  (digits = "")
  orelse ((String.sub(digits,0) = #"0")
	  andalso ((String.size digits) > 1))

val digitChars = [#"0",#"1",#"2",#"3",#"4",#"5",#"6",#"7",#"8",#"9"]

fun intFromString s = 
  case Int.fromString s of
    (SOME n) => n
  | NONE => 0

(* Test FA for detection of nonstandard strings on digit strings of length n *)
fun testNonstandard fa n =
  FATest.testOnAlphabet fa nonstandard digitChars n

(* Test FA for detecing multiples of x on digit strings of length n *)
fun testMultiple x fa n =
  FATest.testOnAlphabet 
    fa 
    (fn s => (intFromString s) mod x = 0)
    digitChars 
    n

(* Test FA for Hammingness on digit strings of length n *)
fun testHamming fa n = 
  FATest.testOnAlphabet
    fa 
    (fn s => if nonstandard s then false
	     else (isHamming (intFromString s)))
    digitChars
    n

fun testMultipleOf2FA n = testMultiple 2 (FA.input "multipleOf2.fa") n

fun testMultipleOf3FA n = testMultiple 3 (FA.input "multipleOf3.fa") n

fun testMultipleOf5FA n = testMultiple 5 (FA.input "multipleOf5.fa") n

fun testNonstandardFA n = testNonstandard (FA.input "nonstandard.fa") n

fun testHammingDFA n = testHamming (DFA.injToFA (DFA.input "hamming.dfa")) n

fun writeHammingDFA () =
  DFA.output("hamming.dfa",
             buildHammingDFA (FA.input "multipleOf2.fa") 
	                     (FA.input "multipleOf3.fa") 
                             (FA.input "multipleOf5.fa") 
                             (FA.input "nonstandard.fa"))

and buildHammingDFA m2 m3 m5 ns =  
  (* replace this stub *)

    let
   (* val nsc = DFA.injToFA(DFA.complement ((faToDFA ns), SymSet.fromString ("0,1,2,3,4,5,6,7,8,9")))*)
    val m2m3 = FA.union (m2, m3)
    val m5m2m3 = faToDFA(FA.union(m2m3, m5))
    val nsd = faToDFA(ns)	

	in
	  DFA.renameStatesCanonically(DFA.minimize(DFA.minus(m5m2m3, nsd)))
	  (* DFA.minimize(faToDFA m2m3)*)
	    (*faToDFA m5ns*)
	  
	   
	    
end
 


