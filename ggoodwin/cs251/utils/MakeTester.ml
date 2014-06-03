module type TEST = sig

  type prog
  type arg
  type res

  val trial : prog -> arg list -> res
  val argToString : arg -> string
  val resEqual : res -> res -> bool 
  val resToString : res -> string

end

module MakeTester = 

 functor (T: TEST) -> 

  struct

    (* Unbuffered version of print_string. It's important to use this
       in situations where you want output to appear immediately. *)
    let print = StringUtils.print

    (* 
      Each test entry has the following type:
      (string, pgm, (arg list * result) list) 
     *)

    let rec testEntries entries = 
      try 
        testTrials (entriesToTrials entries)
      with 
        unhandledException -> 
          (print "\n***TESTING TERMINATED BY UNHANDLED EXCEPTION***\n";
           raise unhandledException)

    and entriesToTrials entries = 
      List.flatten 
        (List.map (fun (name,prog,trials) -> 
                    (List.map (fun (args,expected) -> 
                                (name,prog,args,expected))
                              trials))
                  entries)

    and testTrials trials =
      let results = List.map testTrial trials in 
      let mismatches = 
	List.map (fun ((name,prog,args,expected),
		       (actual, isEqual)) -> (name,args,expected,actual))
	  (List.filter (fun ((name,prog,args,expected),
			     (actual, isEqual)) -> not isEqual)
		       (List.combine trials results)) in 
      let numTrials = List.length trials in 
      let numFails = List.length mismatches in 
      let numSuccs = numTrials - numFails in 
      let _ = print 
	       ("\n============================================================\n"
		^ "TESTING SUMMARY:\n"
		^ "Passed " ^ (string_of_int numSuccs) 
		^ " of " ^ (string_of_int numTrials) 
		^ " test cases.\n")
       in if numFails > 0 then 
	 let _ = print ("The following "
			       ^ (string_of_int numFails)
			       ^ " tests failed:\n\n") in 
	 let _ =  List.iter (fun (name,args,expected,actual) -> 
	                      (printTrialPrefix name args;
			       printMismatch expected actual))
 	                      mismatches in 
         let _ = print
	           ("TESTING SUMMARY:\n"
		    ^ "Passed " ^ (string_of_int numSuccs) 
		    ^ " of " ^ (string_of_int numTrials) 
		    ^ " test cases.\n") in 
	 let _ = print ("The above "
			^ (string_of_int numFails)
			^ " tests failed"
			^ "\n============================================================\n"
			    ) in 
	 ()

    and testTrial (name,prog,args,expected) = 
      let _ = printTrialPrefix name args in 
      let actual = T.trial prog args in
      let isEqual = (T.resEqual expected actual) in 
      let _ = if isEqual then 
		print ((T.resToString actual) ^ " OK!\n")
	      else 
		printMismatch expected actual in 
	(actual, isEqual)

    and printTrialPrefix name args = 
      print (name 
	     ^ " " 
	     ^ (String.concat " " (List.map T.argToString args))
	     ^ " = ")

    and printMismatch exp act = 
      print ("***TESTING MISMATCH***"
	     ^ "\n*** Expected: "
	     ^ (T.resToString exp)
	     ^ "\n***   Actual: "
	    ^ (T.resToString act)
	    ^ "\n\n")

  end
