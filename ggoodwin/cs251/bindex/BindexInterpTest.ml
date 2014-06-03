module BindexTestEntries = struct

  open IntexInterpTest
  open ListUtils

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
      ]);

    ("cbv1", 
     "(bindex (a) (bind b (/ a 0) 17))", 
     [([5], Err("Division by 0: 5"))]);

    ("cbv2", 
     "(bindex (a b) (bind c (+ a b) ( * c c)))", 
     [([2;3], Val 25)]); 

    ("unbound", 
     "(bindex (a b) (bind c (+ a b) ( * c d)))", 
    [([2;3], Err("Unbound variable: d"))]);

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

  let entries = (translateIntexEntries IntexInterpTest.entries) @ bindexEntries

end

module type BINDEX_INTERP = sig
  exception EvalError of string
  val run : Bindex.pgm -> int list -> int
end

module type BINDEX_TEST = sig
  val test : unit -> unit
end

module BindexInterpTest (BindexInterp: BINDEX_INTERP): BINDEX_TEST = struct

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
            Val(BindexInterp.run (Bindex.stringToPgm progString) args)
          with 
            BindexInterp.EvalError(str) -> Err(str)         
          | Sexp.IllFormedSexp(str) -> Err(str)         
            (* Other exceptions will be passed through by default *)
        let argToString = string_of_int
        let resEqual = (=)
        let resToString = resultToString

      end
    )

  let test () = BindexTester.testEntries BindexTestEntries.entries

end
