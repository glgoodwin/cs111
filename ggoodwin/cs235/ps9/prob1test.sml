Standard ML of New Jersey Version 110.65 with Forlan Version 4.1 loaded
val it = () : unit
- test_palindrome 9;
stdIn:5.1-5.16 Error: unbound variable or constructor: test_palindrome
- use "ps9.sml";
[opening ps9.sml]
[opening ../pda/load-all.sml]
[opening ../pda/pda-stack.sig]
signature PDA_STACK =
  sig
    val stackop : str * str -> str -> str option
    val compare : str * str -> order
    val compareOp : (str * str) * (str * str) -> order
  end
val it = () : unit
[opening ../pda/pda-stack.sml]
structure PDAStack : PDA_STACK
val it = () : unit
[opening ../pda/stack-tran.sig]
signature STACK_TRAN =
  sig
    type stack_tran = sym * str * str * str * sym
    val compare : stack_tran * stack_tran -> order
    val equal : stack_tran * stack_tran -> bool
  end
val it = () : unit
[opening ../pda/stack-tran.sml]
structure StackTran : STACK_TRAN
val it = () : unit
[opening ../pda/stack-tran-set.sig]
signature STACK_TRAN_SET =
  sig
    val fromList : StackTran.stack_tran list -> StackTran.stack_tran set
    val compare : StackTran.stack_tran set * StackTran.stack_tran set -> order
    val memb : StackTran.stack_tran * StackTran.stack_tran set -> bool
    val subset : StackTran.stack_tran set * StackTran.stack_tran set -> bool
    val equal : StackTran.stack_tran set * StackTran.stack_tran set -> bool
    val map : ('a -> StackTran.stack_tran)
              -> 'a set -> StackTran.stack_tran set
    val mapFromList : ('a -> StackTran.stack_tran)
                      -> 'a list -> StackTran.stack_tran set
    val union : StackTran.stack_tran set * StackTran.stack_tran set
                -> StackTran.stack_tran set
    val genUnion : StackTran.stack_tran set list -> StackTran.stack_tran set
    val inter : StackTran.stack_tran set * StackTran.stack_tran set
                -> StackTran.stack_tran set
    val genInter : StackTran.stack_tran set list -> StackTran.stack_tran set
    val minus : StackTran.stack_tran set * StackTran.stack_tran set
                -> StackTran.stack_tran set
    val powSet : StackTran.stack_tran set -> StackTran.stack_tran set set
    val inputFromLabToks : (int * Lex.tok) list
                           -> StackTran.stack_tran set * (int * Lex.tok) list
    val toPP : StackTran.stack_tran set -> PP.pp
    val toString : StackTran.stack_tran set -> string
    val output : string * StackTran.stack_tran set -> unit
  end
val it = () : unit
[opening ../pda/stack-tran-set.sml]
[autoloading]
[autoloading done]
structure StackTranSet : STACK_TRAN_SET
val it = () : unit
[opening ../pda/stack-lp.sig]
signature STACK_LP =
  sig
    datatype raw
      = Cons of (sym * str) * (str * str * str) * raw | Symstack of sym * str
    type stack_lp
    val symstack : sym * str -> stack_lp
    val cons : (sym * str) * (str * str * str) * stack_lp -> stack_lp
  end
val it = () : unit
[opening ../pda/pda.sml]
[autoloading]
[autoloading done]
structure PDA :
  sig
    structure M : <sig>
    structure L : <sig>
    type raw =
      {accepting:sym set, start:sym, stats:sym set,
       trans:StackTran.stack_tran set}
    type pda = raw
    val checkStart : sym * sym set -> unit
    val checkAccepting : sym list * sym set -> unit
    val checkTransitions : (sym * 'a * 'b * 'c * sym) list * sym set -> unit
    val check : {accepting:sym set, start:sym, stats:sym set,
                 trans:(sym * 'a * 'b * 'c * sym) set}
                -> unit
    val valid : {accepting:sym set, start:sym, stats:sym set,
                 trans:(sym * 'a * 'b * 'c * sym) set}
                -> bool
    val fromRaw : raw -> pda
    val toRaw : pda -> raw
    val inpPDA : (int * Lex.tok) list -> pda * (int * Lex.tok) list
    val inputFromLabToks : (int * Lex.tok) list -> pda * (int * Lex.tok) list
    val fromString : string -> pda
    val input : string -> pda
    val toPP : {accepting:sym set, start:sym, stats:sym set,
                trans:StackTran.stack_tran set}
               -> PP.pp
    val toString : {accepting:sym set, start:sym, stats:sym set,
                    trans:StackTran.stack_tran set}
                   -> string
    val output : string * 
                 {accepting:sym set, start:sym, stats:sym set,
                  trans:StackTran.stack_tran set}
                 -> unit
    val states : pda -> sym set
    val startState : pda -> sym
    val acceptingStates : pda -> sym set
    val transitions : pda -> StackTran.stack_tran set
    val compare : pda * pda -> order
    val equal : pda * pda -> bool
    val numStates : pda -> int
    val numTransitions : pda -> int
    val alphabet : pda -> sym set
    val stackAlphabet : pda -> sym set
    val transTo : pda -> sym -> StackTran.stack_tran set option
    val transFrom : pda -> sym -> StackTran.stack_tran set option
    val listToString : ('a -> string) -> 'a list -> string
    val BFSTraversal : pda -> sym list
    val DFSTraversal : pda -> sym list
    val processStr : pda -> (sym * str) set * str -> (sym * str) set
    val emptySet : pda
    val emptyStr : pda
    val renameStackAlphabet : pda * sym_rel -> pda
    val renameStates : pda * sym_rel -> pda
    val renameStatesCanonicallyByOrdering : pda -> sym list -> pda
    val renameStatesCanonically : pda -> pda
    val concat : pda * pda -> pda
    val normalize : pda -> pda
    val reachableFrom : pda -> sym set -> sym set
    val toGram : pda -> gram
    val accepted : pda -> str -> bool
  end
val it = () : unit
val it = () : unit
[opening ../utils/PDATest.sml]
[autoloading]
[autoloading done]
structure PDATest :
  sig
    val strToString : str -> string
    val genStrs : sym list -> int -> sym list list
    val comparePDAPred : PDA.pda -> (string -> bool) -> int -> bool
    val comparePDAFilePred : string -> (string -> bool) -> int -> bool
  end
val it = () : unit
opening PDATest
  val strToString : str -> string
  val genStrs : sym list -> int -> sym list list
  val comparePDAPred : PDA.pda -> (string -> bool) -> int -> bool
  val comparePDAFilePred : string -> (string -> bool) -> int -> bool
val countChar = fn : char -> string -> int
val even_as_or_odd_bs_pred = fn : string -> bool
val fewer_as_than_bs_pred = fn : string -> bool
val twice_as_many_as_as_bs_pred = fn : string -> bool
val palindrome_pred = fn : string -> bool
val split = fn : string -> (string * string) option
val not_an_bn_pred = fn : string -> bool
val not_ww_pred = fn : string -> bool
val test_palindrome = fn : int -> bool
val test_fewer_as_than_bs = fn : int -> bool
val test_twice_as_many_as_as_bs = fn : int -> bool
val test_not_an_bn = fn : int -> bool
val test_not_ww = fn : int -> bool
val test_all = fn : int -> bool
val it = () : unit
- test_palindrome 9;
Passed all test cases for strings up to length 9
val it = true : bool
- test_fewer_as_than_bs 9;
Passed all test cases for strings up to length 9
val it = true : bool
- test_twice_as_many_as_as_bs 9;
Passed all test cases for strings up to length 9
val it = true : bool
- test_not_an_bn 9;
Passed all test cases for strings up to length 9
val it = true : bool
- test_all 9;
--------------------------------------------------
Testing palindrome
Passed all test cases for strings up to length 9
--------------------------------------------------
Testing fewer_as_than_bs
Passed all test cases for strings up to length 9
--------------------------------------------------
Testing twice_as_many_as_as_bs
Passed all test cases for strings up to length 9
--------------------------------------------------
Testing not_an_bn
Passed all test cases for strings up to length 9
val it = true : bool
- 