(*
 *stack-tran.sig
 *)

signature STACK_TRAN =
  sig
    type stack_tran = Sym.sym * Str.str * Str.str * Str.str * Sym.sym
		      (*state1 * consumed str * popped str * pushed str * state2 *)
    val compare : stack_tran * stack_tran -> order
    val equal : stack_tran * stack_tran -> bool
  end
