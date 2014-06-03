(* 

Filename: Types.lex

Summary: ML-Lex specification for a simplified version of SML types. 

Author: Lyn Turbak

History: 
+ [lyn, 11/25/07: Created]
*)


(* ML Declarations *)

open Token

type lexresult = token
fun eof () = Token.eof()
%%
whitespace=[\ \n\t];
any= [^]; 
%%
"*" => (PROD);
"->" => (ARROW);
"list" => (LIST);
"unit" => (BASE(Unit));
"bool" => (BASE(Bool));
"int" => (BASE(Int));
"real" => (BASE(Real));
"char" => (BASE(Char));
"string" => (BASE(String));
"(" => (LPAREN);
")" => (RPAREN);
{whitespace} => (lex());
{any} => ((* Signal a failure exception when encounter unexpected character.
             A more flexible implementation might raise a more refined
             exception that could be handled. *)
          raise Fail("Types scanner: unexpected character '" ^ yytext 
                      ^ "' at character number " ^ (Int.toString yypos)));

