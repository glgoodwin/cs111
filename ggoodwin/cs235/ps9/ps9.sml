(**********************************************************************
 This is the SML code solution file for ps9.sml
 **********************************************************************)

(* load grammar testing utilities: 
   testOnString, testOnStrings, testOnAlphabet, mismatches *)
use "../pda/load-all.sml";
use "../utils/PDATest.sml";
open PDATest; (* open the module, so don't need 'PDATest.' qualifier *)

(********** Froblem 1 **********)

(* Predicates *)

(* Count occurrences of char in string *)
fun countChar char string = 
  List.length (List.filter (fn c => c = char) (String.explode string))

fun even_as_or_odd_bs_pred s = 
  (((countChar #"a" s) mod 2) = 0) orelse (((countChar #"b" s) mod 2) = 1)

fun fewer_as_than_bs_pred s = (countChar #"a" s) < (countChar #"b" s)

fun twice_as_many_as_as_bs_pred s = (countChar #"a" s) = (2 * (countChar #"b" s))

(* Can be more efficient, but this is really easy to specify! *)
fun palindrome_pred s = 
  let val chars = String.explode s 
  in chars = List.rev chars
  end

(* If string length is even return SOME (left,right) of the two halves.
   If string length is odd, return NONE *)
fun split s =
  let val len = String.size s
   in if len mod 2 = 0 
      then let val halflen = len div 2
            in SOME (String.substring (s,0, halflen), String.substring (s,halflen,halflen))
           end
      else NONE 
  end

fun not_an_bn_pred s = 
  case split s of
    NONE => true
  | SOME(left,right) => not (List.all (fn c => c = #"a") (String.explode left)
                             andalso
			     List.all (fn c => c = #"b") (String.explode right))

fun not_ww_pred s = 
  case split s of
    NONE => true
  | SOME(left,right) => left <> right 

(* Testing functions *)

val test_palindrome = comparePDAFilePred "palindrome.pda" palindrome_pred

val test_fewer_as_than_bs = comparePDAFilePred "fewer_as_than_bs.pda" fewer_as_than_bs_pred

val test_twice_as_many_as_as_bs = comparePDAFilePred "twice_as_many_as_as_bs.pda" twice_as_many_as_as_bs_pred

val test_not_an_bn = comparePDAFilePred "not_an_bn.pda" not_an_bn_pred

val test_not_ww = comparePDAFilePred "not_ww.pda" not_ww_pred

fun test_all n = 
  let fun testit (str,fcn) = 
        (print "--------------------------------------------------\n";
         print ("Testing " ^ str ^ "\n"); 
         fcn n (* Now actually test the function *))
      (* A non-short-circuit version of List.all *)
      fun forall pred xs = List.foldl (fn (x,bool) => (pred x) andalso bool) true xs
   in forall testit
             [("palindrome", test_palindrome), 
	      ("fewer_as_than_bs", test_fewer_as_than_bs),
	      ("twice_as_many_as_as_bs" ,test_twice_as_many_as_as_bs),
	      ("not_an_bn", test_not_an_bn) 
              (* , ("not_ww", test_not_ww) *)
	      ]
  end
