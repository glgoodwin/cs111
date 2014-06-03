(* Filename: TypeParserShiftReduce.sml

  Summary: A shift-reduce parser for infix type syntax implemented by hand 

  History: 
   + [lyn, 11/23/08] Created

*)

structure TypeParserShiftReduce = 

struct

  open AST   (* Defines abstract sytntax tree types and related functions *)
  open Token (* Defines token type and related functions *)

  (* datatype that allows both tokens and types to be on stack *)
  datatype stkval = Tok of token
                |   Typ of typ

  fun stkvalToString (Tok t) = Token.toString(t)
    | stkvalToString (Typ t) = AST.typToString(t)

  and step( [Typ(t)], [] ) = t (* final parsed type *)

    (* flesh out the other cases here *)

    (* All other configurations are ill-defined *)
    | step( stk, toks ) = 
		raise Fail ("Unexpected configuration:" 
                            ^ " stack = " ^ (ListUtils.listToString stkvalToString stk)
                            ^ "; token = " ^ (ListUtils.listToString Token.toString toks))

  fun stringToTyp str = step([], Scanner.stringToTokens str) 
      (* Note: stringToTokens does *not* include EOF in list! *)
  fun stringToTypString str = AST.typToString (stringToTyp str)

end (* struct *)
