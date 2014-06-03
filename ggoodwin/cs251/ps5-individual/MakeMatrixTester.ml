module type ELT = sig
  type elt
  val toString : elt -> string
end

(* Note: this won't work below if we use ": ELT" here, because
   then int type becomes abstract *)
module IntElt = struct
  type elt = int
  let toString = string_of_int
end

module CharElt = struct
  type elt = char
  let toString c = "'" ^ (String.make 1 c) ^ "'"
end

module MakeMatrixTester = 
  functor (M : MATRIX) -> ((struct

    module GetTester = 
      functor (E : ELT) -> 
        MakeFunTester (
          struct
	    type args = string * E.elt M.matrix * int * int
	    type res = E.elt option list list 
	      
	    let trial (string, m, rows, cols) = 
	      ListUtils.map (fun i -> (ListUtils.map (fun j -> M.get i j m)
                                (ListUtils.range 0 (cols+1))))
		(ListUtils.range 0 (rows+1))

	    let callToString (string, _, rows, cols) = 
	      "ListUtils.map (fun i -> (ListUtils.map (fun j -> get i j "
	      ^ string
              ^ ") (ListUtils.range 0 "
              ^ (string_of_int (cols+1))
              ^ "))) (ListUtils.range 0 "
              ^ (string_of_int (rows+1))
              ^ ")"
	       
	    let resEqual = (=)
	    
	    let resToString = StringUtils.listToString 
		(fun xs -> 
		  "\n" ^ ((StringUtils.listToString 
			     (StringUtils.optionToString E.toString)
			     xs)))
	  end
	  )

    module DimensionsTester = 
      functor (E : ELT) -> 
        MakeFunTester (
          struct
   	    type args = string * E.elt M.matrix 
	    type res = int * int 
	    let trial (string, m) = M.dimensions m
	    let callToString (string, m) = "dimensions " ^ string
	    let resEqual = (=)
	    let resToString = StringUtils.pairToString string_of_int string_of_int
	  end
	  )

    module ToListsTester = 
      functor (E : ELT) -> 
        MakeFunTester (
          struct
	    type args = string * E.elt M.matrix 
	    type res = E.elt list list 
	    let trial (string, m) = M.toLists m 
	    let callToString (string, _) = "toLists " ^ string
	    let resEqual = (=)
	    let resToString = StringUtils.listToString 
		               (StringUtils.listToString E.toString)
	  end
	  )

    module IntGetTester = GetTester(IntElt)
    module IntDimensionsTester = DimensionsTester(IntElt)
    module CharGetTester = GetTester(CharElt)
    module CharDimensionsTester = DimensionsTester(CharElt)
    module IntToListsTester = ToListsTester(IntElt)
    module CharToListsTester = ToListsTester(CharElt)

    let test () = 

      let m1 = M.make 3 4 (fun i j -> 10*(i+5) + j*j) in 
      let _ = StringUtils.println "\nlet m1 = M.make 3 4 (fun i j -> 10*(i+5) + j*j)\n" in 
      let _ = IntToListsTester.testEntry
  	        (("m1", m1), [[61; 64; 69; 76]; [71; 74; 79; 86]; [81; 84; 89; 96]]) in 
      let _ = StringUtils.println "" in 
      let _ = IntGetTester.testEntry
  	        (("m1", m1, 3, 4), 
		 [[None; None; None; None; None; None];
		   [None; Some 61; Some 64; Some 69; Some 76; None];
		   [None; Some 71; Some 74; Some 79; Some 86; None];
		   [None; Some 81; Some 84; Some 89; Some 96; None];
		   [None; None; None; None; None; None]; 
		  ]) in 
      let _ = StringUtils.println "" in 
      let __ = IntDimensionsTester.testEntry(("m1", m1), (3,4)) in
      let m2 = M.transpose m1 in 
      let _ = StringUtils.println "\nlet m2 = transpose m1\n" in 
      let _ = IntToListsTester.testEntry
  	        (("m2", m2), [[61; 71; 81]; [64; 74; 84]; [69; 79; 89]; [76; 86; 96]]) in 
      let _ = StringUtils.println "" in 
      let _ = IntGetTester.testEntry
  	        (("m2", m2, 4, 3), 
		 [[None; None; None; None; None];
	          [None; Some 61; Some 71; Some 81; None]; 
                  [None; Some 64; Some 74; Some 84; None]; 
                  [None; Some 69; Some 79; Some 89; None];
                  [None; Some 76; Some 86; Some 96; None];
		  [None; None; None; None; None]; 
		  ]) in 
      let _ = StringUtils.println "" in 
      let __ = IntDimensionsTester.testEntry(("m2", m2), (4,3)) in
      let m3 = M.map char_of_int m1 in 
      let _ = StringUtils.println "\nlet m3 = map char_of_int m1\n" in 
      let _ = CharToListsTester.testEntry
  	        (("m3", m3), [['='; '@'; 'E'; 'L']; ['G'; 'J'; 'O'; 'V']; ['Q'; 'T'; 'Y'; '`']]) in 
      let _ = StringUtils.println "" in 
      let _ = CharGetTester.testEntry
  	        (("m3", m3, 3, 4), 
		 [[None; None; None; None; None; None];
	          [None; Some '='; Some '@'; Some 'E'; Some 'L'; None]; 
                  [None; Some 'G'; Some 'J'; Some 'O'; Some 'V'; None]; 
                  [None; Some 'Q'; Some 'T'; Some 'Y'; Some '`'; None]; 
		  [None; None; None; None; None; None]; 
		  ]) in 
      let _ = StringUtils.println "" in 
      let __ = CharDimensionsTester.testEntry(("m3", m3), (3,4)) in
      let m4 = M.put 1 3 13 (M.put 3 1 31 m1) in 
      let _ = StringUtils.println "\nlet m4 = put 1 3 13 (put 3 1 31 m1)\n" in 
      let _ = IntToListsTester.testEntry
  	        (("m4", m4), [[61; 64; 13; 76]; [71; 74; 79; 86]; [31; 84; 89; 96]]) in 
      let _ = StringUtils.println "" in 
      let _ = IntGetTester.testEntry
  	        (("m4", m4, 3, 4), 
		 [[None; None; None; None; None; None];
		   [None; Some 61; Some 64; Some 13; Some 76; None];
		   [None; Some 71; Some 74; Some 79; Some 86; None];
		   [None; Some 31; Some 84; Some 89; Some 96; None];
		   [None; None; None; None; None; None]; 
		  ]) in 
      let _ = StringUtils.println "" in 
      let __ = IntDimensionsTester.testEntry(("m4", m4), (3,4)) in
      let m5 = M.put (-2) (-3) (-23) (M.put 0 0 0 (M.put 1 5 15 (M.put 5 1 51 m1))) in
      let _ = StringUtils.println "\nlet m5 = put (-2) (-3) (-23) (put 0 0 0 (put 1 5 15 (put 5 1 51 m1)))\n" in 
      let _ = IntToListsTester.testEntry
  	        (("m5", m5), [[61; 64; 69; 76]; [71; 74; 79; 86]; [81; 84; 89; 96]]) in 
      let _ = StringUtils.println "" in 
      let _ = IntGetTester.testEntry
  	        (("m5", m5, 3, 4), 
		 [[None; None; None; None; None; None];
		   [None; Some 61; Some 64; Some 69; Some 76; None];
		   [None; Some 71; Some 74; Some 79; Some 86; None];
		   [None; Some 81; Some 84; Some 89; Some 96; None];
		   [None; None; None; None; None; None]; 
		  ]) in 
      let _ = StringUtils.println "" in 
      let __ = IntDimensionsTester.testEntry(("m5", m5), (3,4)) in
      ()

  end) : (sig val test : unit -> unit end))
