(* Testing functions for the MINI-FP interpreter *)

module MiniFPTest = struct
  
  open MiniFP
  open MiniFPInterp

  (* Here, the "S" prefix indicates "string" *)

  let vector1S = "(2 3 5)"

  let vector2S = "(10 20 30)"

  let vectorsS = "((2 3 5) (10 20 30))"

  let matrixS = "((1 4) (8 6) (7 9))"
      
  let matrices1S = "(((2 3 5) (10 20 30)) ((1 4) (8 6) (7 9)))"

  let matrices2S = "(((1 4) (8 6) (7 9)) ((2 3 5) (10 20 30)))"

  let ipS = "(o (/ +) (a x) trans)"

  let mmS = "(o (a (a (o (/ +) (a x) trans))) (a distl) distr (1 (o trans 2)))"

  let fS = "(o (a (/ +)) (a (a x)) (a distl) distr (id id))"

  let testSuite =
    [
     (("+","(7 3)"), "10"); 
     (("+","(7 -3)"), "4"); 
     (("+","(-7 3)"), "-4"); 
     (("+","(-7 -3)"), "-10"); 
     (("+","(0 7)"), "7"); 
     (("+","(7 0)"), "7"); 
     (("+","1"), "Error: Ill-formed application: apply + 1"); 
     (("+","(1)"), "Error: Ill-formed application: apply + (1)"); 
     (("+","(1 2 3)"), "Error: Ill-formed application: apply + (1 2 3)"); 

     (("-","(7 3)"), "4"); 
     (("-","(7 -3)"), "10"); 
     (("-","(-7 3)"), "-10"); 
     (("-","(-7 -3)"), "-4"); 
     (("-","(0 7)"), "-7"); 
     (("-","(7 0)"), "7"); 

     (("x","(7 3)"), "21"); 
     (("x","(7 -3)"), "-21"); 
     (("x","(-7 3)"), "-21"); 
     (("x","(-7 -3)"), "21"); 
     (("x","(0 7)"), "0"); 
     (("x","(7 0)"), "0"); 

     (("-:","(7 3)"), "2"); 
     (("-:","(7 -3)"), "-2"); 
     (("-:","(-7 3)"), "-2"); 
     (("-:","(-7 -3)"), "2"); 
     (("-:","(0 7)"), "0"); 
     (("-:","(7 0)"), "Error: Division by zero"); 

     (("id","17"), "17"); 
     (("id", vector1S), vector1S);
     (("id", matrixS), matrixS);
     (("id", matrices1S), matrices1S);

     (("($ 17)","5"), "17"); 
     (("($ 17)", vector1S), "17");
     (("($ 17)", matrixS), "17");
     (("($ 17)", matrices1S), "17");

     (("1", "17"), "Error: Ill-formed application: apply 1 17");

     (("0", vector1S), "Error: Selection index 0 out of bounds [1..3]");
     (("1", vector1S), "2");
     (("2", vector1S), "3");
     (("3", vector1S), "5");
     (("4", vector1S), "Error: Selection index 4 out of bounds [1..3]");

     (("0", vectorsS), "Error: Selection index 0 out of bounds [1..2]");
     (("1", vectorsS), "(2 3 5)");
     (("2", vectorsS), "(10 20 30)");
     (("3", vectorsS), "Error: Selection index 3 out of bounds [1..2]");

     (("distl", "(7 " ^ vector1S ^ ")"), "((7 2) (7 3) (7 5))"); 
     (("distl", vectorsS), "(((2 3 5) 10) ((2 3 5) 20) ((2 3 5) 30))"); 
     (("distl", "7"), "Error: Ill-formed application: apply distl 7"); 
     (("distl", "(7 4)"),  "Error: Ill-formed application: apply distl (7 4)"); 

     (("distr", "(" ^ vector1S ^ " 7)"), "((2 7) (3 7) (5 7))"); 
     (("distr", vectorsS), "((2 (10 20 30)) (3 (10 20 30)) (5 (10 20 30)))");
     (("distr", "7"), "Error: Ill-formed application: apply distr 7"); 
     (("distr", "(7 4)"), "Error: Ill-formed application: apply distr (7 4)"); 

     (("trans", vectorsS), "((2 10) (3 20) (5 30))"); 
     (("trans", matrixS), "((1 8 7) (4 6 9))"); 
     (("trans", "(((2 10) (3 20) (5 30)) ((1 4) (8 6) (7 9)))"), 
               "(((2 10) (1 4)) ((3 20) (8 6)) ((5 30) (7 9)))"); 
     (("trans", vector1S), "Error: transpose -- not a sequence of sequences: (2 3 5)"); 
     (("trans", "17"), "Error: Ill-formed application: apply trans 17"); 
     (("trans", matrices1S), "Error: transpose -- not a regular sequence of sequences: (((2 3 5) (10 20 30)) ((1 4) (8 6) (7 9)))"); 

     (("(bu + 1)", "5"), "6"); 
     (("(bu x 2)", "5"), "10"); 
     (("(bu x 2)", "(3 5)"), "Error: Ill-formed application: apply x (2 (3 5))"); 

     (("(/ +)", vector1S), "10");
     (("(/ x)", vector1S), "30");
     (("(/ -)", vector1S), "4");
     (("(/ -:)", vector1S), "Error: Division by zero");
     (("(/ +)", "(1 2 3 4 5 6 7 8 9 10)"), "55");
     (("(/ x)", "(1 2 3 4 5 6 7 8 9 10)"), "3628800");
     (("(/ -)", "(10 9 8 7 6 5 4 3 2 1)"), "5"); 
     (("(/ -:)", "(10 9 8 7 6 5 4 3 2 1)"), "10"); 

     (("(a id)", "17"), "Error: Ill-formed application: apply (a id) 17"); 
     (("(a id)", vector1S), "(2 3 5)"); 
     (("(a ($ 17))", vector1S), "(17 17 17)"); 
     (("(a (bu + 1))", vector1S), "(3 4 6)"); 
     (("(a (bu x 2))", vector1S), "(4 6 10)"); 
     (("(a +)", matrixS), "(5 14 16)"); 
     (("(a (/ +))", vectorsS), "(10 60)"); 
     (("(a (a (/ +)))", matrices1S), "((10 60) (5 14 16))"); 

     (("(o id ($ 17))", vector1S), "17"); 
     (("(o ($ 17) id)", vector1S), "17"); 
     (("(o (a (bu + 1)) (a ($ 17)))", vector1S), "(18 18 18)"); 
     (("(o (a (bu + 1)) (a (bu x 2)))", vector1S), "(5 7 11)"); 
     (("(o (bu + 1) (/ +))", vector1S), "11"); 
     (("(o trans trans)", matrixS), matrixS); 
     (("(a (o (bu -: 50) (bu + 1) (bu x 2)))", vector1S), "(10 7 4)"); 
     (("(o (a (bu -: 50)) (a (bu + 1)) (a (bu x 2)))", vector1S), "(10 7 4)"); 

     (("((bu -: 50) (bu + 1) (bu x 2))", "10"), "(5 11 20)"); 
     (("((a (bu -: 50)) (a (bu + 1)) (a (bu x 2)))", vector1S), "((25 16 10) (3 4 6) (4 6 10))"); 
     (("((a (/ +)) distl trans)", vectorsS), "((10 60) (((2 3 5) 10) ((2 3 5) 20) ((2 3 5) 30)) ((2 10) (3 20) (5 30)))"); 
 
     ((ipS, vectorsS), "230");
     ((ipS, matrixS), "Error: Ill-formed application: apply x (1 8 7)");

     ((mmS, matrices1S), "((61 71) (380 430))"); 
     ((mmS, matrices2S), "((42 83 125) (76 144 220) (104 201 305))"); 
     ((mmS, vectorsS), "Error: transpose -- not a sequence of sequences: (10 20 30)"); 

     ((fS, vector1S), "(20 30 50)");
     ((fS, vector2S), "(600 1200 1800)");

    ] 

  module TestTypes = struct 
    type args = string * string
    type res = string
  end

  module TestFuns = struct
    open StringUtils
    let trial = 
      fun (funString, objString) -> 
	try 
	  objToString (apply (stringToFunForm funString) (stringToObj objString))
	with
	  (EvalError msg) -> "Error: " ^ msg
	| (SyntaxError msg) -> "Error:" ^ msg
    let callToString (funString, objString) = "apply " ^ funString ^ " " ^ objString
    let resEqual = (=) (* String equality *)
    let resToString = FunUtils.id
  end
      
  module Tester = (MakeFunTester(TestTypes)) (TestFuns)

  let test() = Tester.testEntries testSuite

end
