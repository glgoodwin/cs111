(**********************************************************************
 This is the SML code skeleton file for ps6.sml
 **********************************************************************)

(* load FA testing utilities: testOnString, testOnStrings, mismatches, genStrings *)
use "../utils/FATest.sml";  
open FATest; (* open the module, so don't need 'FATest.' qualifier *)

(* load regular expression testing utilities: 
   testOnString, testOnStrings, testOnAlphabet, mismatches *)
use "../utils/RegTest.sml"; 

(********** Froblem 1 **********)

(* Useful helper function in this problem *)
fun revString s = String.implode (List.rev (String.explode s))

(*** Redefine this stub ***)
(*fun testL1R n =
 let
     val L1list = List.filter (testOnString (FA.input("L1.fa"), genStrings(n,[#"0", #"1"])))
    
     fun L1R_pred s = List.exists(fn f => f = revString s L1list)
 in	 
   testOnStrings(FA.input "L1R.fa")
                (fn s => L1R_pred s)
		(genStrings(n,[#"0", #"1"]))
end*)

(********** Froblem 2 **********)
fun test2 () =
  let val L2_fa = FA.input "L2.fa"
      val L2_dfa = DFA.input "L2.dfa"
      val faToDFA = nfaToDFA o efaToNFA o faToEFA
   in DFA.relationship(faToDFA L2_fa, L2_dfa)
  end

(********** Froblem 3 **********)

(* Regular expression strings *)
(* Replace these stubs *)
val ab_or_bc_reg = "(((a+b+c)*)(ab+bc)((a+b+c)*))"

val ab_and_bc_reg = "(((a+b+c)*ab(a+b+c)*bc(a+b+c)*)+((a+b+c)*bc(a+b+c)*ab(a+b+c)*)+((a+b+c)*abc(a+b+c)*))"

val cs_at_odd_positions_reg = "((a+b+c)c)*(a+b+c+%)"

val even_as_or_odd_bs_reg = "((((b+c)*a(b+c)*a(b+c)*)*)+((b+c)*)+((a+c)*b(a+c)*(b(a+c)*b(a+c)*)*))"

val geq_2_bs_and_leq_1_c_reg = "((a+b)*c(a+b)*b(a+b)*b(a+b)*)+((a+b)*b(a+b)*c(a+b)*b(a+b)*)+((a+b)*b(a+b)*b(a+b)*c(a+b)*)+((a+b)*b(a+b)*b(a+b)*)"

(* Predicates *)
(*helper functions*)
fun first s = String.str (String.sub (s,0))

fun butFirst s = String.substring (s, 1, (String.size s) - 1)
 
fun countabRec s = 
    if s = "" then 
	(0,0)
    else
	let val (a,b) = countbcRec (butFirst s)
	in if  first s = "a" then
	    ((fn z => z+1) a, b)
	   else if first s = "b" then
	       (a,(fn y => y+1) b)
		else 
		    (a,b)
	end 

fun countbcRec s = 
    if s = "" then 
	(0,0)
    else
	let val (b,c) = countbcRec (butFirst s)
	in if  first s = "b" then
	    ((fn z => z+1) b, c)
	   else if first s = "c" then
	       (b,(fn y => y+1) c)
		else 
		    (b,c)
	end 

fun odd_cs (s,i)=   
    let val len = String.size s
    in
	if i >= len then
	    true
	    else
	    if s = "" 
		then true
	    else
		if String.sub(s,i) = #"c"
		    then odd_cs (s,i+2)
		else 
		    false		    
    end
(* Replace these stubs *)
fun ab_or_bc_pred s = (String.isSubstring "ab" s) orelse (String.isSubstring "bc" s)

fun ab_and_bc_pred s = (String.isSubstring "ab" s) andalso (String.isSubstring "bc" s)

fun cs_at_odd_positions_pred s =  odd_cs (s,1)


fun even_as_or_odd_bs_pred s = 
    let
    val (a,b) = countabRec s
	in
    ((a mod 2) = 0) orelse ((b mod 2) >0)
		end
		

fun geq_2_bs_and_leq_1_c_pred s = 
    let 
    val (b,c) = countbcRec s
	in
    (b >= 2) andalso (c <= 1)
    end
	

(* Testing functions *)

fun makeABCTester regString pred n = RegTest.testOnAlphabet regString pred [#"a", #"b", #"c"] n

val test_ab_or_bc = makeABCTester ab_or_bc_reg ab_or_bc_pred

val test_ab_and_bc = makeABCTester ab_and_bc_reg ab_and_bc_pred

val test_cs_at_odd_positions = makeABCTester cs_at_odd_positions_reg cs_at_odd_positions_pred

val test_even_as_or_odd_bs = makeABCTester even_as_or_odd_bs_reg even_as_or_odd_bs_pred

val test_geq_2_bs_and_leq_1_c = makeABCTester geq_2_bs_and_leq_1_c_reg geq_2_bs_and_leq_1_c_pred

fun test3_all n = 
  let fun testit (str,fcn) = 
        (print "--------------------------------------------------\n";
         print ("Testing " ^ str ^ "\n"); 
         fcn n (* Now actually test the function *))
      (* A non-short-circuit version of List.all *)
      fun forall pred xs = List.foldl (fn (x,bool) => (pred x) andalso bool) true xs
   in forall testit
             [("ab_or_bc", test_ab_or_bc),
	      ("ab_and_bc", test_ab_and_bc),
	      ("cs_at_odd_positions", test_cs_at_odd_positions),
	      ("even_as_or_odd_bs", test_even_as_or_odd_bs),
	      ("geq_2_bs_and_leq_1_c", test_geq_2_bs_and_leq_1_c)]
  end

(********** Froblem 4 **********)

fun test4a () = 
  let val L4a_fa = FA.input "L4a.fa"
      val L4a_reg = Reg.fromString "1(01* + ($0)*)*1"
      val faToDFA = nfaToDFA o efaToNFA o faToEFA
      val regToDFA = faToDFA o regToFA
  in DFA.relationship(faToDFA L4a_fa, regToDFA L4a_reg)
  end

fun test4c () = 
  let val L4c_fa = FA.input "L4c.fa"
      val L4c_reg = Reg.fromString "1(01* + ($0)*)*1"
      val faToDFA = nfaToDFA o efaToNFA o faToEFA
      val regToDFA = faToDFA o regToFA
  in DFA.relationship(faToDFA L4c_fa, regToDFA L4c_reg)
  end

(********** Froblem 5 **********)

(* Replace this stub *)
val R5_string = "(%+(11*)+((0(000+01(111)))*)+((0(00*)(000+01(111)))*)+((11(0(000+01(111))))*)+((11(0(00*)(000+01(111))))*)(%+0+0(00*)+(0(%+01(10+10%)+10(1*)%))+(0(00*)(%+(01(10+10%)+10(1*)%)))))"

fun test5() = 
  let val L5_fa = FA.input "L5.fa"
      val L5_reg = Reg.fromString R5_string
      val faToDFA = nfaToDFA o efaToNFA o faToEFA
      val regToDFA = faToDFA o regToFA
   in DFA.relationship(faToDFA L5_fa, regToDFA L5_reg)
  end




