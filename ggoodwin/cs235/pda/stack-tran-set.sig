(*
 *stack-tran-set.sig
 *)

signature STACK_TRAN_SET = 
sig
    val fromList : StackTran.stack_tran list -> StackTran.stack_tran Set.set
    val compare : StackTran.stack_tran Set.set * StackTran.stack_tran Set.set -> order
    val memb : StackTran.stack_tran * StackTran.stack_tran Set.set -> bool
    val subset : StackTran.stack_tran Set.set * StackTran.stack_tran Set.set -> bool
    val equal : StackTran.stack_tran Set.set * StackTran.stack_tran Set.set -> bool
    val map : ('a -> StackTran.stack_tran) -> 'a Set.set -> StackTran.stack_tran Set.set
    val mapFromList : ('a -> StackTran.stack_tran) -> 'a list -> StackTran.stack_tran Set.set
    val union : StackTran.stack_tran Set.set * StackTran.stack_tran Set.set -> StackTran.stack_tran Set.set
    val genUnion : StackTran.stack_tran Set.set list -> StackTran.stack_tran Set.set
    val inter : StackTran.stack_tran Set.set * StackTran.stack_tran Set.set -> StackTran.stack_tran Set.set
    val genInter : StackTran.stack_tran Set.set list -> StackTran.stack_tran Set.set
    val minus : StackTran.stack_tran Set.set * StackTran.stack_tran Set.set -> StackTran.stack_tran Set.set
    val powSet : StackTran.stack_tran Set.set -> StackTran.stack_tran Set.set Set.set
    val inputFromLabToks : (int * Lex.tok) list
			   -> StackTran.stack_tran Set.set * (int * Lex.tok) list
(*    val fromString : string -> StackTran.stack_tran Set.set*)
(*    val input : string -> StackTran.stack_tran Set.set*)
    val toPP : StackTran.stack_tran Set.set -> PP.pp
    val toString : StackTran.stack_tran Set.set -> string
    val output : string * StackTran.stack_tran Set.set -> unit
end
