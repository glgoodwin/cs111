(*
 *sym-stack.sig
 *)

signature PDA_STACK = 
  sig
      (*pop str * push str -> stack str -> stack str option*)
      val stackop : Str.str * Str.str -> Str.str -> Str.str option

      (*lyn: GET RID OF?*)
      val compare : Str.str * Str.str -> order

      (*useful for comparing stackops across PDAs or LPs; lexicographical*)
      val compareOp : (Str.str * Str.str) * (Str.str * Str.str) -> order

  end
