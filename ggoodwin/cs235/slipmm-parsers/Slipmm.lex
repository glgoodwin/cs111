(* 

Filename: Slip.lex

Summary: ML-Lex specification for Slip--, a subset of 
         Appel's straight-line programming language

Author: Lyn Turbak

History: 
+ [lyn, 9/7/00: Created]
  [lyn, 11/19/07: Modified]

*)


(* ML Declarations *)

open Token

type lexresult = token
fun eof () = Token.eof()



fun pluck (SOME(v)) = v
  | pluck NONE = raise Fail ("Shouldn't happen -- pluck(NONE)")

(* Keeping track of nesting level of block comments *)
val commentNestingLevel = ref 0
fun incrementNesting() = 
(print "Incrementing comment nesting level";
 commentNestingLevel := (!commentNestingLevel) + 1)
fun decrementNesting() = 
(print "Decrementing comment nesting level";
 commentNestingLevel := (!commentNestingLevel) - 1)

(* Call this function upon creating a new lexer, before using it. *)
fun init() = commentNestingLevel := 0

%%
%s COMMENT;
alpha=[a-zA-Z];
alphaNumUnd=[a-zA-Z0-9_];
digit=[0-9];
whitespace=[\ \t\n];
any= [^]; 
%%
<INITIAL>"print" => (PRINT);
<INITIAL>"begin" => (BEGIN);
<INITIAL>"end" => (END);
<INITIAL>{alpha}{alphaNumUnd}*  => (ID(yytext));
<INITIAL>{digit}+  => (INT(pluck(Int.fromString(yytext))));
<INITIAL>"+" => (OP(Add));
<INITIAL>"-" => (OP(Sub));
<INITIAL>"*" => (OP(Mul));
<INITIAL>"/" => (OP(Div));
<INITIAL>"(" => (LPAREN);
<INITIAL>")" => (RPAREN);
<INITIAL>"," => (COMMA);
<INITIAL>";" => (SEMI);
<INITIAL>":=" => (GETS);
<INITIAL>"#".*"\n" => (lex() (* read a line comment *));
<INITIAL>"{" => (YYBEGIN COMMENT; incrementNesting(); lex());
<INITIAL>{whitespace} => (lex());
<COMMENT>"{" => (incrementNesting(); lex());
<COMMENT>"}" => (decrementNesting(); if (!commentNestingLevel) = 0 then (YYBEGIN INITIAL; lex()) else lex());
<COMMENT>{any} => (lex()); 
{any} => ((* Signal a failure exception when encounter unexpected character.
             A more flexible implementation might raise a more refined
             exception that could be handled. *)
          raise Fail("Slip-- scanner: unexpected character '" ^ yytext 
                      ^ "' at character number " ^ (Int.toString yypos)));
