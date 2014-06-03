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

   ]


  (* Translate bindex entries of the form (<ints>, IntexInterpTest.Val <int>) 
     to the form (<ints>, ValexTestEntries.Val (Valex.Int <int>)) 
     and those of the form (<ints>, IntexInterpTest.Val <string>)
     to the form (<ints>, ValexTestEntries.Err <string>) *)

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

