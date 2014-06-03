module type TEST = sig

  type args
  type res

  val trial : args -> res
  val callToString : args -> string
  val resEqual : res -> res -> bool 
  val resToString : res -> string

end

module MakeFunTester = 

 functor (T: TEST) -> 

  ((struct

    (* Unbuffered version of print_string. It's important to use this
       in situations where you want output to appear immediately. *)
    let print = StringUtils.print

    (* 
      Each test entry has the following type:
      (args * result) list
     *)

    let rec testEntries entries = 
      let results = List.map testTrial entries in 
      let mismatches = 
	List.map (fun ((args,expected),
		       (actual, isEqual)) -> (args,expected,actual))
	  (List.filter (fun ((args,expected),
			     (actual, isEqual)) -> not isEqual)
		       (List.combine entries results)) in 
      let numTrials = List.length entries in 
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
	   List.iter (fun (args,expected,actual) -> 
			(printTrialPrefix args;
			 printMismatch expected actual))
		     mismatches

    and testEntry (args,expected) =
      let _ = testTrial (args,expected) in () 

    and testTrial (args,expected) = 
      let _ = printTrialPrefix args in 
      let actual = T.trial args in
      let isEqual = (T.resEqual expected actual) in 
      let _ = if isEqual then 
		print ((T.resToString actual) ^ " OK!\n")
	      else 
		printMismatch expected actual in 
	(actual, isEqual)

    and printTrialPrefix args = 
      print ((T.callToString args)
	     ^ " = ")

    and printMismatch exp act = 
      print ("***TESTING MISMATCH***"
	     ^ "\n*** Expected: "
	     ^ (T.resToString exp)
	     ^ "\n***   Actual: "
	    ^ (T.resToString act)
	    ^ "\n\n")

  end) : (sig 
            val testEntry : (T.args * T.res) -> unit 
            val testEntries : (T.args * T.res) list -> unit 
          end))
