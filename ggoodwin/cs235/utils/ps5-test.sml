use "ps5.sml";

fun test1 n = 
  let fun testit (str,fcn) = 
        (print "--------------------------------------------------\n";
         print ("Testing " ^ str ^ "\n"); 
         fcn n (* Now actually test the function *))
      (* A non-short-circuit version of List.all *)
      fun forall pred xs = List.foldl (fn (x,bool) => (pred x) andalso bool) true xs
   in forall testit
             [("test_ab_and_bc", test_ab_and_bc),
	      ("test_ab_or_bc", test_ab_and_bc),
	      ("test_ab_minus_bc", test_ab_minus_bc),
	      ("test_neither_ab_nor_bc", test_neither_ab_nor_bc)]
  end

fun testall () =
    (test1 10;
     print "--------------------------------------------------\n";
     print ("Testing Hamming FA on 5\n"); 
     testHammingFA 5;
     print "--------------------------------------------------\n";
     print ("Testing Hamming DFA on 5\n"); 
     testHammingDFA 5
     )
     
