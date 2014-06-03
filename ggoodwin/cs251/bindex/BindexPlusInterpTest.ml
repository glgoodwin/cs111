module type BINDEX_PLUS_INTERP = sig
  exception EvalError of string
  val run : BindexPlus.pgm -> int list -> int
end

module type BINDEX_PLUS_TEST = sig
  val test : unit -> unit
end

module BindexPlusInterpTest (BindexInterp: BINDEX_PLUS_INTERP): BINDEX_PLUS_TEST = struct

  open IntexInterpTest
  open ListUtils

  module BindexTester = 
    MakeTester (
      struct
        type prog = string
        type arg = int
        type res = result

        let trial progString args = 
          try
            Val(BindexInterp.run (BindexPlus.stringToPgm progString) args)
          with 
            BindexInterp.EvalError(str) -> Err(str)         
          | Sexp.IllFormedSexp(str) -> Err(str)         
            (* Other exceptions will be passed through by default *)
        let argToString = string_of_int
        let resEqual = (=)
        let resToString = resultToString

      end
    )

  let bindexEntries = 

    [

     ("squares",
      "(bindex (a b)
         (bind a_sq (* a a)
           (bind b_sq (* b b)
             (bind numer (+ a_sq b_sq)
               (bind denom (- a_sq b_sq)
                 (/ numer denom))))))",
      [([5;0],Val(1)); 
       ([5;1],Val(1));
       ([5;2],Val(1));
       ([5;3],Val(2));
       ([5;4],Val(4));
       ([5;5],Err("Division by 0: 50"))
       ]); 

    ("scope1", 
     "(bindex (x y)
        (* (bind a (* x y)
             (- a y))
           (bind b (bind c (+ x y)
                     (* c y))
             (/ b x))))", 
     [([2;3], Val(21)); 
      ([3;5], Val(130))
      ]);

    ("scope2", 
     "(bindex (x y)
        (* (bind x (* x y)
             (- x y))
           (bind y (bind x (+ x y)
                     ( * x y))
             (/ y x))))", 
     [([2;3], Val(21)); 
      ([3;5], Val(130))
      ]) 


     ]

  let sigmaEntries = 
   [
    ("sigma1", 
     "(bindex () (sigma k 1 6 k))", 
     [([],Val(21))]); 

    ("sigma2", 
     "(bindex () (sigma k 3 5 ( * k k)))",
     [([],Val(50))]);

    ("sigma3", 
     "(bindex () (sigma k 3 2 ( * k k)))",
     [([],Val(0))]); 

    ("sigma4", 
     "(bindex () (sigma i 2 5
                       (sigma j i 4 ( * i j))))",
     [([],Val(55))]); (* (2*2) + (2*3) + (2*4) + (3*3) + (3*4) + (4*4) *)

    ("sigma5", 
     "(bindex () (sigma i 1 3 
                       (sigma j (* i i) 4 
                              (sigma k (- j i) (+ j i) 
                                     ( * i ( * j k))))))",
     [([],Val(250))]);

    ("sigma6", 
     "(bindex () (sigma i (sigma k 1 3 ( * k k)) 
                          (sigma j 1 5 j) 
                          i))", 
     [([],Val(29))])

    ]


  let seqParEntries = 
   [
    ("bindpar1", 
     "(bindex (a b) ; a = 5, b = 3
        (bindpar ((a (+ a b)) ; 8
                  (b (- a b))) ; 2
          (bindpar ((a (* a b))  ; 16
                    (b (/ a b))) ; 4
            (+ a b)))) ; 20", 
     [([5;3], Val 20)]);

    ("bindseq1", 
     "(bindex (a b) ; a = 5, b = 3
        (bindseq ((a (+ a b)) ; 8
                  (b (- a b))) ; 5
          (bindseq ((a (* a b)) ; 40
                    (b (/ a b))) ; 8
            (+ a b)))) ; 48", 
     [([5;3], Val 48)]);

    ("bindpar2", 
     "(bindex (a b) ; a = 10, b = 2
        (bindpar ((a (/ a b)) ; 5
                  (b (- a b))) ; 8
          (bindpar ((a (* a b))  ; 40
                    (b (+ a b))) ; 13
            (+ a b)))) ; 53", 
     [([10;2], Val 53)]);

    ("bindseq2", 
     "(bindex (a b) ; a = 10, b = 2
        (bindseq ((a (/ a b)) ; 5
                  (b (- a b))) ; 3
          (bindseq ((a (* a b)) ; 15
                    (b (+ a b))) ; 18
            (+ a b)))) ; 33", 
     [([10;2], Val 33)]);

    ]

  (* Translate errors of the form 

      Illegal arg index: <num>

     into 
      
      Unbound variable: $<num> *)

  let translateIntexEntries entries = 
    let err = "Illegal arg index:" in 
    let lenErr = String.length err in 
    let parseAns a = 
      match a with 
        Val _  -> None
      | Err s -> if ((String.length s) >= lenErr)
                    && (String.sub s 0 lenErr) = err then 
		   Some (String.sub s (lenErr + 1) ((String.length s)-(lenErr + 1)))
  	         else 
                   None in 
    map (fun (name,pgm,inouts)-> 
          (name,pgm,
	   map (fun ((args,ans) as inout) -> 
                  match parseAns ans with 
	  	    None -> inout 
	          | Some numString -> (args, Err("Unbound variable: $" ^ numString)))
                inouts))
	 entries

  let entries = bindexEntries 
		@ (translateIntexEntries IntexInterpTest.entries)
	        @ sigmaEntries
                @ seqParEntries

  let test () = BindexTester.testEntries entries

end
