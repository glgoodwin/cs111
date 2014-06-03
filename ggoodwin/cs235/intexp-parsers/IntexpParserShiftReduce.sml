(* Filename: IntextpParserShiftReduce.sml

  Summary: A shift-reduce parser for infix expression syntax implemented by hand 

  History: 
   + [lyn, 12/08/10] Implemented by adapting 11/23/08 shift-reduce type parser

*)

structure IntexpParserShiftReduce = 

struct

  open AST   (* Defines abstract sytntax tree types and related functions *)
  open Token (* Defines token type and related functions *)

  (* datatype that allows both tokens and types to be on stack *)
  datatype stkval = Tok of token
                |   Exp of exp

  fun stkvalToString (Tok t) = Token.toString(t)
    | stkvalToString (Exp e) = AST.expToString(e)

  and step( [Exp(e)], [] ) = e (* final parsed expression *)

    (* Int tokens are shifted ... *)
    | step(             [],  INT(i)::toks ) = 
      step( [Tok(INT(i))],          toks ) (*shift*)

    | step( Tok(tok as (LPAREN|OP(_)))::stk, INT(i)::toks ) = 
      step( Tok(INT(i))::Tok(tok)     ::stk,         toks ) (*shift*)

    (* ... and then reduced *)
    | step( Tok(INT(i))::stk, toks ) = 
      step( Exp(Int(i))::stk, toks ) (*reduce*)

    (* Always shift operators onto singleton stack *)
    | step(            [Exp(e)],  OP(b)::toks ) = 
      step( [Tok(OP(b)),Exp(e)],         toks ) (*shift*)

    (* Always shift operators onto parenthesized type *)
    | step(             Exp(e)::Tok(LPAREN)::stk,  OP(b)::toks ) = 
      step( Tok(OP(b))::Exp(e)::Tok(LPAREN)::stk,         toks ) (*shift*)

    (* {+,-} case *)

    (* Reduce any binapp on stack when see + or - token *)
    | step( Exp(e2)::Tok(OP(b1))::Exp(e1)::stk, OP(b2 as (Add|Sub))::toks ) =
      step( Exp(BinApp(e1,b1,e2))        ::stk, OP(b2)             ::toks ) (*reduce*)

    (* {*,/} cases *)

    (* Reduce ^ on stack when see {*,/} token since ^ has higher precedence *)
    (* Reduce {*,/} on stack when see {*,/} token since {*,/} is left associative *)
    | step( Exp(e2)::Tok(OP(b1 as (Expt|Mul|Div)))::Exp(e1)::stk, OP(b2 as (Mul|Div))::toks ) =
      step( Exp(BinApp(e1,b1,e2))                          ::stk, OP(b2)::toks ) (*reduce*)

    (* Shift {*,/} when see {+,-} on stack since {*,/} has higher precedence *)
    | step( Exp(e2)::Tok(OP(b1 as (Add|Sub)))::Exp(e1)::stk, OP(b2 as (Mul|Div))::toks ) =
      step( Tok(OP(b2))::Exp(e2)::Tok(OP(b1))::Exp(e1)::stk,                      toks ) (*shift*)

    (* {^} cases *)

    (* Shift ^ when see {*,/,+,-} on stack since ^ has higher precedence *)
    (* Shift ^ when see ^ on stack since ^ is right associative *)
    | step(                Exp(e2)::Tok(OP(b))::Exp(e1)::stk, OP(Expt)::toks ) =
      step( Tok(OP(Expt))::Exp(e2)::Tok(OP(b))::Exp(e1)::stk,           toks ) (*shift*)

    (* Always reduce operators when no more tokens *)
    | step( Exp(e2)::Tok(OP(b))::Exp(e1)::stk,  [] ) =
      step( Exp(BinApp(e1,b,e2))        ::stk,  [] ) (*reduce*)

    (* Left paren cases *)

    (* Left parens are shifted *)
    | step(            [],  LPAREN::toks ) = 
      step( [Tok(LPAREN)],          toks ) (*shift*)

    | step( Tok(tok as (LPAREN|OP(_)))::stk,  LPAREN::toks ) = 
      step( Tok(LPAREN)::Tok(tok)     ::stk,          toks ) (*shift*)

    (* Right paren cases *)

    (* Right parens are shifted to match left parens *)
    | step(              Exp(e)::Tok(LPAREN)::stk,  RPAREN::toks ) = 
      step( Tok(RPAREN)::Exp(e)::Tok(LPAREN)::stk,          toks ) (*shift*)

    (* ... and then reduced *)
    | step( Tok(RPAREN)::Exp(e)::Tok(LPAREN)::stk,  toks ) =
      step(              Exp(e)::             stk,  toks ) (*reduce*)

    (* Always reduce operators on right paren *)
    | step( Exp(e2)::Tok(OP(b))::Exp(e1)::stk,  RPAREN::toks ) =
      step( Exp(BinApp(e1,b,e2))        ::stk,  RPAREN::toks ) (*reduce*)

    (* All other configurations are ill-defined *)
    | step( stk, toks ) = 
		raise Fail ("Unexpected configuration:" 
                            ^ " stack = " ^ (ListUtils.listToString stkvalToString stk)
                            ^ "; token = " ^ (ListUtils.listToString Token.toString toks))

  fun stringToExp str = step([], Scanner.stringToTokens str) 
  fun stringToPgm str = Pgm(stringToExp(str))
  fun stringToExpString str = AST.expToString (stringToExp str)

end (* struct *)
