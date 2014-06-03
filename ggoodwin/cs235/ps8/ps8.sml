(**********************************************************************
 This is the SML code skeleton file for ps8.sml
 **********************************************************************)

(* load grammar testing utilities: 
   testOnString, testOnStrings, testOnAlphabet, mismatches *)
use "../utils/GramTest.sml"; 
use "../utils/FATest.sml"; 
open GramTest; (* open the module, so don't need 'GramTest.' qualifier *)

(********** Froblem 1 **********)

fun test1 n = 
  testOnStrings' "CFG1" 
                 "CNF1" 
                 (Gram.input "CFG1.gram")
                 (testOnString (Gram.input "CNF1.gram"))
                 (genStrings(n,[#"a", #"b", #"c", #"d", #"e"]))

(********** Froblem 2 **********)
(*Helper functions*)
fun first s = String.str (String.sub (s,0))

fun butFirst s = String.substring (s, 1, (String.size s) - 1)

fun countabRec s = 
    if s = "" then 
	(0,0)
    else
	let val (a,b) = countabRec (butFirst s)
	in if  first s = "a" then
	    ((fn z => z+1) a, b)
	   else if first s = "b" then
	       (a,(fn y => y+1) b)
		else 
		    (a,b)
	end 

(* Predicates *)
(* Flesh out the definition of these *)

fun even_as_or_odd_bs_pred s = 
    let val (a,b) = countabRec s
	in (((Int.mod a, 2) = 0) OR ((Int.mod b, 2) != 0))
	    end

fun fewer_as_than_bs_pred s = 
    let val (a,b) = countabRec s
	in
	    a < b
	    end

fun twice_as_many_as_as_bs_pred s = 
    let val (a,b) = countabRec s
	in
	    a = b*2
    end

fun palindrome_pred s = s = String.implode(List.rev(String.explode s))

fun not_an_bn_pred s = let val (a,b) = countabRec s
    in !((a*)(b*) & (a =b))

(* Testing functions *)

fun makeABTester gramFile pred n = testOnAlphabet (Gram.input gramFile) pred [#"a", #"b"] n

val test_even_as_or_odd_bs = makeABTester "even_as_or_odd_bs.gram" even_as_or_odd_bs_pred

val test_fewer_as_than_bs = makeABTester "fewer_as_than_bs.gram" fewer_as_than_bs_pred

val test_twice_as_many_as_as_bs = makeABTester "twice_as_many_as_as_bs.gram" twice_as_many_as_as_bs_pred

val test_palindrome = makeABTester "palindrome.gram" palindrome_pred

val test_not_an_bn = makeABTester "not_an_bn.gram" not_an_bn_pred

fun test_all n = 
  let fun testit (str,fcn) = 
        (print "--------------------------------------------------\n";
         print ("Testing " ^ str ^ "\n"); 
         fcn n (* Now actually test the function *))
      (* A non-short-circuit version of List.all *)
      fun forall pred xs = List.foldl (fn (x,bool) => (pred x) andalso bool) true xs
   in forall testit
             [("even_as_or_odd_bs", test_even_as_or_odd_bs),
	      ("palindrome", test_palindrome), 
	      ("fewer_as_than_bs", test_fewer_as_than_bs),
	      ("twice_as_many_as_as_bs" ,test_twice_as_many_as_as_bs),
	      ("not_an_bn", test_not_an_bn)
	      ]
  end

(********** Froblem 3 **********)

fun testProb3Partb n = 
  testOnStrings' "CFG" 
                 "FA" 
                 (Gram.input "Prob3Partb.gram")
                 (FATest.testOnString (FA.input "Prob3Partb.fa"))
                 (genStrings(n,[#"0", #"1"]))

fun testProb3Partd n = 
  testOnStrings' "CFG" 
                 "FA" 
                 (Gram.input "Prob3Partd.gram")
                 (FATest.testOnString (FA.input "Prob3Partd.fa"))
                 (genStrings(n,[#"0", #"1"]))



