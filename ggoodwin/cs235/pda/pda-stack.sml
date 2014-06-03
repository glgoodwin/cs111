(*
 *sym-stack.sml
 *
 *Designed to capture npda stack ops. Uses options to determine success
 *or failure.
 *
 *Stacks here are represented as Strs.
 *)

structure PDAStack :> PDA_STACK = 
struct

(*type pdastack = Str.str*)

fun stackop (x,y) s = case Str.removePrefix (x,s) of NONE => NONE
						 | SOME s' => SOME (y @ s')


(*compares strs.*)
fun compareOp ((w,x),(y,z)) = 
    case Str.compare (w,y) of 
	LESS => LESS
      | EQUAL => Str.compare (x,z)
      | GREATER => GREATER

fun compare (s1,s2) = Str.compare (s1,s2)

end;
