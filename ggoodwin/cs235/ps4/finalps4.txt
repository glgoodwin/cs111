(* ps4.sml skeleton *)

(* load DFA testing utilities from class:
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

fun doesnt_contain_0100_pred str = 
    if String.isSubstring "0100" str then
	false
	else
	    true

fun penultimate_0_pred str = 
    if size str <= 1 then 
	false
	else if Char.toString( String.sub (str, (size str) -2)) = "0" then
	    true
	    else 
		false


(* Helper function for testing eq_01_10 *)
fun loop (str) = 

    (* a = # of "01"s, b = # of "10"s*)
    if size str < 2 then
  	(0,0)
    else
	let val (a,b) = loop (String.extract (str, 1, NONE))
	in if (String.substring (str, 0, 2)) = "01" then
	   (*(print (String.substring (str, 0, 2));*)
	    (a+1,b)
	   else if (String.substring (str, 0, 2)) = "10" then
	      (* print (String.substring (str, 0, 2));*)
	       (a, b+1)
		else 
		  (* print (String.substring (str, 0, 2));*)
		    (a,b)
	end

fun eq_01s_10s_pred str = 
    let val (a,b) = loop (str) 
    in if a = b then
	    true
       else
	   false
    end
   		
fun binaryToInt binaryNumeral =
    let fun loop num [] = num
	  | loop num (#"0" :: cs) = loop (2*num) cs
	  | loop num (#"1" :: cs) = loop (2*num +1) cs
	  | loop num (c :: cs) = raise (Fail ("Unexpected char in binary numeral: " ^ (String.str c)))
    in loop 0 (String.explode binaryNumeral)
    end

fun multiple_of_5_pred str = 
    if (binaryToInt str) mod 5 = 0 then
	true
	else 
	    false


fun contains_substrings_pred str = 
    if String.isSubstring "111" str then
	true
	else if String.isSubstring "10101" str then
	    true
	     else if String.isSubstring "10011" str then
		 true
		 else if String.isSubstring "001011" str then
		     true
		     else
			 false

(**********************************************************************
 Predefined testing functions 
 **********************************************************************)

fun test_doesnt_contain_0100 n = 
   testOnStrings (DFA.input "doesnt_contain_0100.dfa")
                 doesnt_contain_0100_pred
                 (genStrings(n, [#"0", #"1"]))

fun test_penultimate_0 n = 
   testOnStrings (DFA.input "penultimate_0.dfa")
                 penultimate_0_pred
                 (genStrings(n, [#"0", #"1"]))

fun test_eq_01s_10s n = 
   testOnStrings (DFA.input "eq_01s_10s.dfa")
                 eq_01s_10s_pred
                 (genStrings(n, [#"0", #"1"]))

fun test_multiple_of_5 n = 
   testOnStrings (DFA.input "multiple_of_5.dfa")
                 multiple_of_5_pred
                 (genStrings(n, [#"0", #"1"]))

fun test_contains_substrings n = 
   testOnStrings (DFA.input "contains_substrings.dfa")
                 contains_substrings_pred
                 (genStrings(n, [#"0", #"1"]))

fun testall n = 
  let fun testit (str,fcn) = 
        (print "--------------------------------------------------\n";
         print ("Testing " ^ str ^ "\n"); 
         fcn n (* Now actually test the function *))
      (* A non-short-circuit version of List.all *)
      fun forall pred xs = List.foldl (fn (x,bool) => (pred x) andalso bool) true xs
   in forall testit
             [("doesnt_contain_0100", test_doesnt_contain_0100),
	      ("penultimate_0", test_penultimate_0),
	      ("eq_01s_10s", test_eq_01s_10s),
	      ("multiple_of_5", test_multiple_of_5),
	      ("contains_substrings", test_contains_substrings)]
  end







