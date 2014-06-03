Standard ML of New Jersey Version 110.65 with Forlan Version 2.8 loaded
val it = () : unit
- use "ps8.sml";
[opening ps8.sml]
[opening ../utils/GramTest.sml]
[autoloading]
[autoloading done]
structure GramTest :
  sig
    val testOnString : gram -> string -> bool
    val mismatches : gram -> (string -> bool) -> string list -> string list
    val testOnStrings' : string
                         -> string
                            -> gram -> (string -> bool) -> string list -> bool
    val testOnStrings : gram -> (string -> bool) -> string list -> bool
    val genStrings : int * char list -> string list
    val testOnAlphabet : gram -> (string -> bool) -> char list -> int -> bool
    val parseString : gram -> string -> pt
  end
val it = () : unit
[opening ../utils/FATest.sml]
structure FATest :
  sig
    val testOnString : fa -> string -> bool
    val mismatches : fa -> (string -> bool) -> string list -> string list
    val testOnStrings : fa -> (string -> bool) -> string list -> bool
    val genStrings : int * char list -> string list
    val testOnAlphabet : fa -> (string -> bool) -> char list -> int -> bool
  end
val it = () : unit
opening GramTest
  val testOnString : gram -> string -> bool
  val mismatches : gram -> (string -> bool) -> string list -> string list
  val testOnStrings' : string
                       -> string
                          -> gram -> (string -> bool) -> string list -> bool
  val testOnStrings : gram -> (string -> bool) -> string list -> bool
  val genStrings : int * char list -> string list
  val testOnAlphabet : gram -> (string -> bool) -> char list -> int -> bool
  val parseString : gram -> string -> pt
val test1 = fn : int -> bool
val first = fn : string -> string
val butFirst = fn : string -> string
val countabRec = fn : string -> int * int
val even_as_or_odd_bs_pred = fn : 'a -> bool
val fewer_as_than_bs_pred = fn : string -> bool
val twice_as_many_as_as_bs_pred = fn : string -> bool
val palindrome_pred = fn : string -> bool
val not_an_bn_pred = fn : 'a -> bool
val makeABTester = fn : string -> (string -> bool) -> int -> bool
val test_even_as_or_odd_bs = fn : int -> bool
val test_fewer_as_than_bs = fn : int -> bool
val test_twice_as_many_as_as_bs = fn : int -> bool
val test_palindrome = fn : int -> bool
val test_not_an_bn = fn : int -> bool
val test_all = fn : int -> bool
val testProb3Partb = fn : int -> bool
val testProb3Partd = fn : int -> bool
val it = () : unit
- test1 6;
Passed all test cases
val it = true : bool
- 