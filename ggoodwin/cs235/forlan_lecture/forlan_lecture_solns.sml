(* lec11.sml skeleton *)

(* load DFA testing utilities presented in class:
   testOnString, testOnStrings, mismatches, genStrings *)
use "../utils/DFATest.sml"; 
open DFATest; (* open the module, so don't need 'DFATest.' qualifier *)

(**********************************************************************
 A helper function
 **********************************************************************)

(* Return the integer denoted by a binary numeral string. 
   Complains about any character in the string that's not 0 or 1 *)
fun binaryToInt binaryNumeral = 
  let fun loop num [] = num 
        | loop num (#"0" :: cs) = loop (2*num) cs
        | loop num (#"1" :: cs) = loop (2*num + 1) cs
        | loop num (c :: cs) = raise (Fail ("Unexpected char in binary numeral: " 
                                            ^ (String.str c)))
  in loop 0 (String.explode binaryNumeral)
 end 

(**********************************************************************
 Replace the stubs below by your predicates for PS4 Problem 2:
 **********************************************************************)

fun begin_and_end_with_a_pred str = 
  let val len = String.size str
   in (len >= 1)
      andalso (String.sub(str,0) = #"a")
      andalso (String.sub(str, len - 1) = #"a")
  end
  
fun contains_001_010_or_111_pred str = 
  (String.isSubstring "001" str)
  orelse (String.isSubstring "010" str)
  orelse (String.isSubstring "111" str)

fun multiple_of_3_pred str = 
  ((binaryToInt str) mod 3) = 0

(**********************************************************************
 Predefined testing functions 
 **********************************************************************)

fun test_begin_and_end_with_a n = 
   testOnStrings (DFA.input "dfa1.dfa") (* (DFA.input "dfa1.dfa") *)
                 begin_and_end_with_a_pred
                 (genStrings(n, [#"a", #"b"]))

fun test_contains_001_010_or_111 n = 
   testOnStrings (DFA.input "contains_001_010_or_111.dfa")
                 contains_001_010_or_111_pred
                 (genStrings(n, [#"0", #"1"]))

fun test_multiple_of_3 n = 
   testOnStrings (DFA.input "multiple_of_3.dfa")
                 multiple_of_3_pred
                 (genStrings(n, [#"0", #"1"]))







