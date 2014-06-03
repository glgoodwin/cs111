(*
 *stack-lp.sig
 *
 *Like an FA LP, but with Stack state encoded at each state, op encoded in label.
 *)
signature STACK_LP =
  sig
    datatype raw
	     (*state, stack state*)
      = Symstack of Sym.sym * Str.str
      (*((state, stack state),(consumed str, (popped, pushed strs)),
	 (rest of LP))*)
      | Cons of (Sym.sym * Str.str) * (Str.str * Str.str * Str.str) * raw
    type stack_lp
    val symstack : Sym.sym * Str.str -> stack_lp
    val cons : (Sym.sym * Str.str) * (Str.str * Str.str * Str.str) * stack_lp -> stack_lp
    (**other funs tbd**)
  end
