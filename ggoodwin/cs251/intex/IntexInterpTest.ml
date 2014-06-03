module IntexInterpTest = struct

  type result = 
      Val of int
    | Err of string

  let resultToString res = 
    match res with 
      Val(i) -> string_of_int i
    | Err(s) -> s

  module IntexTester = 
    MakeTester (
      struct
        type prog = string
        type arg = int
        type res = result

        let trial progString args = 
          try
            Val(IntexInterp.run (Intex.stringToPgm progString) args)
          with 
            IntexInterp.EvalError(str) -> Err(str)         
          | Sexp.IllFormedSexp(str) -> Err(str)         
            (* Other exceptions will be passed through by default *)
        let argToString = string_of_int
        let resEqual = (=)
        let resToString = resultToString

      end
    )

  let entries = 
    [

     ("lits", 
      "(intex 0 (* (+ 0 (* 1 (/ (- 5 -3) 2)))
                   (+ 1073741823 -1073741824)))", 
      [([],Val(-4))]); 

     ("add", 
      "(intex 2 (+ ($ 1) ($ 2)))", 
      [([3;5],Val(8));
       ([3;-5],Val(-2));
       ([-3;5],Val(2));
       ([-3;-5],Val(-8));
       ([max_int;1],Val(min_int))]);

     ("sub", 
      "(intex 2 (- ($ 1) ($ 2)))", 
      [([3;5],Val(-2));
       ([3;-5],Val(8));
       ([-3;5],Val(-8));
       ([-3;-5],Val(2));
       ([min_int;1],Val(max_int))]);

     ("mul", 
      "(intex 2 (* ($ 1) ($ 2)))", 
      [([3;5],Val(15));
       ([3;-5],Val(-15));
       ([-3;5],Val(-15));
       ([-3;-5],Val(15));
       ([min_int;2],Val(0));
       ([max_int;2],Val(-2))]);

     ("div", 
      "(intex 2 (/ ($ 1) ($ 2)))", 
      List.map (fun (a,r) -> (a, Val r))
      [([9;1],9);([9;2],4);([9;3],3);([9;4],2);([9;5],1);([9;9],1);([9;10],0);
       ([-9;1],-9);([-9;2],-4);([-9;3],-3);([-9;4],-2);([-9;5],-1);([-9;9],-1);([-9;10],0);
       ([-9;-1],9);([-9;-2],4);([-9;-3],3);([-9;-4],2);([-9;-5],1);([-9;-9],1);([-9;-10],0);
       ([9;-1],-9);([9;-2],-4);([9;-3],-3);([9;-4],-2);([9;-5],-1);([9;-9],-1);([9;-10],0)]);

     ("rem", 
      "(intex 2 (% ($ 1) ($ 2)))", 
      List.map (fun (a,r) -> (a, Val r))
      [([9;1],0);([9;2],1);([9;3],0);([9;4],1);([9;5],4);([9;9],0);([9;10],9);
       ([-9;1],0);([-9;2],-1);([-9;3],0);([-9;4],-1);([-9;5],-4);([-9;9],0);([-9;10],-9);
       ([-9;-1],0);([-9;-2],-1);([-9;-3],0);([-9;-4],-1);([-9;-5],-4);([-9;-9],0);([-9;-10],-9);
       ([9;-1],0);([9;-2],1);([9;-3],0);([9;-4],1);([9;-5],4);([9;-9],0);([9;-10],9)]);

     ("divMod", 
      "(intex 2 (+ (* ($ 2) (/ ($ 1) ($ 2))) (% ($ 1) ($ 2))))",
      [([17;4], Val(17));
       ([17;-5], Val(17));
       ([-17;6], Val(-17));
       ([-17;-7], Val(-17))]);

     ("arith", 
      "(intex 2 (* (+ ($ 1) (/ ($ 1) ($ 2)))
                   (% ($ 1) (- ($ 1) ($ 2)))))",
      [([7;3], Val(27));
       ([3;7], Val(9));
       ([7;0], Err("Division by 0: 7")); 
       ([7;7], Err("Remainder by 0: 7"))]);

     ("f2c",
      "(intex 1 (/ (* (+ ($ 1) -32) 5) 9))",
      [([32],Val(0)); 
       ([212],Val(100));
       ([-40],Val(-40));
       ([77],Val(25));
       ([3;4], Err("Program expected 1 arguments but got 2"));
       ([], Err("Program expected 1 arguments but got 0"))]);

     ("tooBigArg", 
      "(intex 1 (+ ($ 1) ($ 2)))",
      [([5], Err("Illegal arg index: 2"))]);

     ("zeroArg", 
      "(intex 1 (+ ($ 1) ($ 0)))",
      [([6], Err("Illegal arg index: 0"))]); 

     ("negArg", 
      "(intex 1 (+ ($ 1) ($ -1)))",
      [([7], Err("Illegal arg index: -1"))])

  ] 

  let test () = IntexTester.testEntries entries

end
