module ValexTestEntries = struct

  open Valex
  open FunUtils
  open ListUtils

  type result = 
      Val of Valex.valu
    | Err of string

  let resultToString res = 
    match res with 
      Val v -> valuToString v
    | Err s -> s

  let valexEntries = 

    [

    ("relop1", 
     "(valex (a b) (< a b))",
     [([1;2], Val (Bool true)); 
      ([2;2], Val (Bool false)); 
      ([3;2], Val (Bool false))]); 

    ("relop2", 
     "(valex (a b) (<= a b))",
     [([1;2], Val (Bool true)); 
      ([2;2], Val (Bool true)); 
      ([3;2], Val (Bool false))]); 

    ("relop3", 
     "(valex (a b) (= a b))",
     [([1;2], Val (Bool false)); 
      ([2;2], Val (Bool true)); 
      ([3;2], Val (Bool false))]); 

    ("relop4", 
     "(valex (a b) (!= a b))",
     [([1;2], Val (Bool true)); 
      ([2;2], Val (Bool false)); 
      ([3;2], Val (Bool true))]); 

    ("relop5", 
     "(valex (a b) (> a b))",
     [([1;2], Val (Bool false)); 
      ([2;2], Val (Bool false)); 
      ([3;2], Val (Bool true))]); 

    ("relop6", 
     "(valex (a b) (>= a b))",
     [([1;2], Val (Bool false)); 
      ([2;2], Val (Bool true)); 
      ([3;2], Val (Bool true))]); 

    ("if1", 
     "(valex (a b) (if (< a b) (+ a b) ( * a b)))",
     [([2;3], Val (Int 5)); 
      ([3;2], Val (Int 6))]); 

    ("if2", 
     "(valex (a b) (if (> a b) (+ a b) (/ a b)))",
     [([3;0], Val (Int 3)); 
      ([-4;0], Err "Division by 0: -4")]);

    ("if3", 
     "(valex (a b) (if (- a b) (+ a b) ( * a b)))",
     [([2;3], Err "Non-boolean test value -1 in if expression")]);

    ("squares",
     "(valex (a b) 
        (bind c (* a a)
          (bind d (* b b)
            (bind numer (+ c d)
              (bind denom (- c d)
                (/ numer denom))))))",
      [([5;0],Val (Int 1)); 
       ([5;1],Val (Int 1));
       ([5;2],Val (Int 1));
       ([5;3],Val (Int 2));
       ([5;4],Val (Int 4));
       ([5;5],Err("Division by 0: 50"))]);

    ("bindpar1", 
     "(bindex (a b) ; a = 5, b = 3
        (bindpar ((a (+ a b)) ; 8
                  (b (- a b))) ; 2
          (bindpar ((a (* a b))  ; 16
                    (b (/ a b))) ; 4
            (+ a b)))) ; 20", 
     [([5;3], Val (Int 20))]);

    ("bindseq1", 
     "(bindex (a b) ; a = 5, b = 3
        (bindseq ((a (+ a b)) ; 8
                  (b (- a b))) ; 5
          (bindseq ((a (* a b)) ; 40
                    (b (/ a b))) ; 8
            (+ a b)))) ; 48", 
     [([5;3], Val (Int 48))]);

    ("bindpar2", 
     "(bindex (a b) ; a = 10, b = 2
        (bindpar ((a (/ a b)) ; 5
                  (b (- a b))) ; 8
          (bindpar ((a (* a b))  ; 40
                    (b (+ a b))) ; 13
            (+ a b)))) ; 53", 
     [([10;2], Val (Int 53))]);

    ("bindseq2", 
     "(bindex (a b) ; a = 10, b = 2
        (bindseq ((a (/ a b)) ; 5
                  (b (- a b))) ; 3
          (bindseq ((a (* a b)) ; 15
                    (b (+ a b))) ; 18
            (+ a b)))) ; 33", 
     [([10;2], Val (Int 33))]);

    ("classify1", 
     "(valex (grade)
        (classify grade
          ((90 100) 'A')
          ((80 89) 'B')
          ((70 79) 'C')
          ((60 69) 'D')
          (otherwise 'F')))", 
      [([95], Val (Char 'A'));
       ([85], Val (Char 'B'));
       ([75], Val (Char 'C'));
       ([65], Val (Char 'D'));
       ([55], Val (Char 'F'));
       ([45], Val (Char 'F'));]); 

    ("classify2", 
     "(valex (a b c d)
        (classify (* c d)
          ((a (- (/ (+ a b) 2) 1)) (* a c))
          (((+ (/ (+ a b) 2) 1) b) (* b d))
          (otherwise (- d c))))", 
      [([10;20;3;4], Val (Int 30));
       ([10;20;3;6], Val (Int 120));
       ([10;20;3;5], Val (Int 2));]);

    ("classify3", 
     "(valex (a)
        (classify a
          ((0 9) a)
          (((/ 20 a) 20) (+ a 1))
          (otherwise (/ 100 (- a 5)))))", 
      [([0], Val (Int 0));
       ([5], Val (Int 5));
       ([10], Val (Int 11));
       ([20], Val (Int 21));
       ([25], Val (Int 5));
       ([30], Val (Int 4));]);

   ]


  (* Translate bindex entries of the form (<ints>, IntexInterpTest.Val <int>) 
     to the form (<ints>, ValexInterpTest.Val (Valex.Int <int>)) 
     and those of the form (<ints>, IntexInterpTest.Err <string>)
     to the form (<ints>, ValexInterpTest.Err <string>) *)

  let translateBindexEntries entries = 
    map (fun (name,pgmString,inouts)-> 
          (name,pgmString,
	   map (fun (args,ans) -> 
                  (match ans with 
                     IntexInterpTest.Val i -> (args, Val (Int i))
                   | IntexInterpTest.Err s -> (args, Err s)))
                inouts))
	 entries

  let entries = (translateBindexEntries BindexTestEntries.entries) @ valexEntries

end

module type VALEX_INTERP = sig
  val run : Valex.pgm -> int list -> Valex.valu
end

module type VALEX_TEST = sig
  val test : unit -> unit
end

module ValexInterpTest (ValexInterp: VALEX_INTERP): VALEX_TEST  = struct

  open Valex
  open ValexTestEntries
  open FunUtils
  open ListUtils

  module ValexTester = 
    MakeTester (
      struct
        type prog = string
        type arg = int
        type res = result

        let trial progString args = 
          try
            Val(ValexInterp.run (Valex.stringToPgm progString) args)
          with 
            Valex.EvalError(str) -> Err(str)         
          | Valex.SyntaxError(str) -> Err(str)         
          | Sexp.IllFormedSexp(str) -> Err(str)         
            (* Other exceptions will be passed through by default *)
        let argToString = string_of_int
        let resEqual = (=)
        let resToString = resultToString

      end
    )

  let test () = ValexTester.testEntries ValexTestEntries.entries

end

