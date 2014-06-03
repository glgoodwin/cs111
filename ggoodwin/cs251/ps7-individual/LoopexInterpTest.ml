module LoopexTestEntries = struct

  open Loopex
  open FunUtils
  open ListUtils

  type result = 
      Val of Loopex.valu
    | Err of string

  let resultToString res = 
    match res with 
      Val v -> valuToString v
    | Err s -> s

  let loopexEntries = 

   [

    ("fact", 
     "(loopex (n)
        (loop ((i n (- i 1))
               (prod 1 (* i prod)))
              (> i 0)
          prod))", 
     [([0], Val (Int 1)); 
      ([1], Val (Int 1)); 
      ([2], Val (Int 2)); 
      ([3], Val (Int 6)); 
      ([4], Val (Int 24)); 
      ([5], Val (Int 120))]);

    ("fib", 
     "(loopex (n)
        (loop ((i 0 (+ 1 i))
               (fib_i 0 fib_i+1)
               (fib_i+1 1 (+ fib_i fib_i+1)))
              (< i n)
          fib_i))", 
     [([0], Val (Int 0)); 
      ([1], Val (Int 1)); 
      ([2], Val (Int 1)); 
      ([3], Val (Int 2)); 
      ([4], Val (Int 3)); 
      ([5], Val (Int 5));
      ([6], Val (Int 8));]);

    (* Add extra test entries here *)

   ]

  let entries = loopexEntries

end

module type LOOPEX_INTERP = sig
  val run : Loopex.pgm -> int list -> Loopex.valu
end

module type LOOPEX_TEST = sig
  val test : unit -> unit
end

module LoopexInterpTest (LoopexInterp: LOOPEX_INTERP): LOOPEX_TEST  = struct

  open Loopex
  open LoopexTestEntries
  open FunUtils
  open ListUtils

  module LoopexTester = 
    MakeTester (
      struct
        type prog = string
        type arg = int
        type res = result

        let trial progString args = 
          try
            Val(LoopexInterp.run (Loopex.stringToPgm progString) args)
          with 
            Loopex.EvalError(str) -> Err(str)         
          | Loopex.SyntaxError(str) -> Err(str)         
          | Sexp.IllFormedSexp(str) -> Err(str)         
            (* Other exceptions will be passed through by default *)
        let argToString = string_of_int
        let resEqual = (=)
        let resToString = resultToString

      end
    )

  let test () = LoopexTester.testEntries LoopexTestEntries.entries

end

