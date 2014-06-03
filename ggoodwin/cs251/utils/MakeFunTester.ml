(* [lyn, 02/24/07]
   Previous version of MakeFunTester was unusable because
   args and res were abstract types. So could not create any entries of these types!
   Now they are concretely passed in a structure with signature TEST_TYPES.
   *)

module type TEST_TYPES = sig
  type args
  type res
end

module type TEST = 
    functor (TestTypes: TEST_TYPES) -> 
      sig
	val trial : TestTypes.args -> TestTypes.res
	val callToString : TestTypes.args -> string
	val resEqual : TestTypes.res -> TestTypes.res -> bool 
	val resToString : TestTypes.res -> string
      end

module MakeFunTester = 
 functor (TT: TEST_TYPES) -> 
   functor (TestFuns : sig 
     val trial : TT.args -> TT.res
     val callToString : TT.args -> string
     val resEqual : TT.res -> TT.res -> bool 
     val resToString : TT.res -> string
   end) -> 
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
      let actual = TestFuns.trial args in
      let isEqual = (TestFuns.resEqual expected actual) in 
      let _ = if isEqual then 
		print ((TestFuns.resToString actual) ^ " OK!\n")
	      else 
		printMismatch expected actual in 
	(actual, isEqual)

    and printTrialPrefix args = 
      print ((TestFuns.callToString args)
	     ^ " = ")

    and printMismatch exp act = 
      print ("***TESTING MISMATCH***"
	     ^ "\n*** Expected: "
	     ^ (TestFuns.resToString exp)
	     ^ "\n***   Actual: "
	    ^ (TestFuns.resToString act)
	    ^ "\n\n")

  end) : (sig 
            val testEntry : (TT.args * TT.res) -> unit 
            val testEntries : (TT.args * TT.res) list -> unit 
          end))
