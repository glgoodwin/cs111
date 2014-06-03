(*
 *stacktran.sml
 *)

structure StackTran :> STACK_TRAN =
struct

(*********Type********)
(*state a * consumed string * popped string * pushed string * state b *)
type stack_tran = Sym.sym * Str.str * Str.str * Str.str * Sym.sym

(******Functions*******)

(*lexicographical*)
fun compare ((q, x, y, z, r),(q', x', y', z', r')) =
    case Sym.compare (q,q') of
	LESS => LESS
      |	EQUAL => (case Str.compare (x,x') of
		     LESS => LESS
		   | EQUAL => (case PDAStack.compareOp 
				       ((y,z),(y',z')) 
			       of LESS => LESS
				| EQUAL => Sym.compare (r,r')
				| GREATER => GREATER)
		   | GREATER => GREATER)
      |	GREATER => GREATER

fun equal (m,n) = ((compare (m,n)) = EQUAL)

end;
