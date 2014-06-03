structure Mlex=
   struct
    structure UserDeclarations =
      struct
(* 

Filename: Intexp.lex

 Summary: ML-Lex specification for Intexp, a simple expresssion language. 

Author: Lyn Turbak

History: 
+ [lyn, 12/6/10: Created from Slipmm.lex

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

end (* end of user routines *)
exception LexError (* raised if illegal leaf action tried *)
structure Internal =
	struct

datatype yyfinstate = N of int
type statedata = {fin : yyfinstate list, trans: string}
(* transition & final state table *)
val tab = let
val s = [ 
 (0, 
"\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000"
),
 (1, 
"\005\005\005\005\005\005\005\005\005\019\019\005\005\005\005\005\
\\005\005\005\005\005\005\005\005\005\005\005\005\005\005\005\005\
\\019\005\005\016\005\005\005\005\015\014\013\012\005\011\005\010\
\\008\008\008\008\008\008\008\008\008\008\005\005\005\005\005\005\
\\005\005\005\005\005\005\005\005\005\005\005\005\005\005\005\005\
\\005\005\005\005\005\005\005\005\005\005\005\005\005\005\007\005\
\\005\005\005\005\005\005\005\005\005\005\005\005\005\005\005\005\
\\005\005\005\005\005\005\005\005\005\005\005\006\005\005\005\005\
\\005"
),
 (3, 
"\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\
\\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\
\\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\
\\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\
\\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\
\\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\
\\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\020\
\\020\020\020\020\020\020\020\020\020\020\020\022\020\021\020\020\
\\020"
),
 (8, 
"\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\009\009\009\009\009\009\009\009\009\009\000\000\000\000\000\000\
\\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\000\
\\000"
),
 (16, 
"\017\017\017\017\017\017\017\017\017\017\018\017\017\017\017\017\
\\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\
\\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\
\\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\
\\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\
\\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\
\\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\
\\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\017\
\\017"
),
(0, "")]
fun f x = x 
val s = map f (rev (tl (rev s))) 
exception LexHackingError 
fun look ((j,x)::r, i: int) = if i = j then x else look(r, i) 
  | look ([], i) = raise LexHackingError
fun g {fin=x, trans=i} = {fin=x, trans=look(s,i)} 
in Vector.fromList(map g 
[{fin = [], trans = 0},
{fin = [], trans = 1},
{fin = [], trans = 1},
{fin = [], trans = 3},
{fin = [], trans = 3},
{fin = [(N 32)], trans = 0},
{fin = [(N 22),(N 32)], trans = 0},
{fin = [(N 12),(N 32)], trans = 0},
{fin = [(N 2),(N 32)], trans = 8},
{fin = [(N 2)], trans = 8},
{fin = [(N 10),(N 32)], trans = 0},
{fin = [(N 6),(N 32)], trans = 0},
{fin = [(N 4),(N 32)], trans = 0},
{fin = [(N 8),(N 32)], trans = 0},
{fin = [(N 16),(N 32)], trans = 0},
{fin = [(N 14),(N 32)], trans = 0},
{fin = [(N 32)], trans = 16},
{fin = [], trans = 16},
{fin = [(N 20)], trans = 0},
{fin = [(N 24),(N 32)], trans = 0},
{fin = [(N 30),(N 32)], trans = 0},
{fin = [(N 28),(N 30),(N 32)], trans = 0},
{fin = [(N 26),(N 30),(N 32)], trans = 0}])
end
structure StartStates =
	struct
	datatype yystartstate = STARTSTATE of int

(* start state definitions *)

val COMMENT = STARTSTATE 3;
val INITIAL = STARTSTATE 1;

end
type result = UserDeclarations.lexresult
	exception LexerError (* raised if illegal leaf action tried *)
end

fun makeLexer yyinput =
let	val yygone0=1
	val yyb = ref "\n" 		(* buffer *)
	val yybl = ref 1		(*buffer length *)
	val yybufpos = ref 1		(* location of next character to use *)
	val yygone = ref yygone0	(* position in file of beginning of buffer *)
	val yydone = ref false		(* eof found yet? *)
	val yybegin = ref 1		(*Current 'start state' for lexer *)

	val YYBEGIN = fn (Internal.StartStates.STARTSTATE x) =>
		 yybegin := x

fun lex () : Internal.result =
let fun continue() = lex() in
  let fun scan (s,AcceptingLeaves : Internal.yyfinstate list list,l,i0) =
	let fun action (i,nil) = raise LexError
	| action (i,nil::l) = action (i-1,l)
	| action (i,(node::acts)::l) =
		case node of
		    Internal.N yyk => 
			(let fun yymktext() = substring(!yyb,i0,i-i0)
			     val yypos = i0+ !yygone
			open UserDeclarations Internal.StartStates
 in (yybufpos := i; case yyk of 

			(* Application actions *)

  10 => (OP(Div))
| 12 => (OP(Expt))
| 14 => (LPAREN)
| 16 => (RPAREN)
| 2 => let val yytext=yymktext() in INT(pluck(Int.fromString(yytext))) end
| 20 => (lex() (* read a line comment *))
| 22 => (YYBEGIN COMMENT; incrementNesting(); lex())
| 24 => (lex())
| 26 => (incrementNesting(); lex())
| 28 => (decrementNesting(); if (!commentNestingLevel) = 0 then (YYBEGIN INITIAL; lex()) else lex())
| 30 => (lex())
| 32 => let val yytext=yymktext() in (* Signal a failure exception when encounter unexpected character.
             A more flexible implementation might raise a more refined
             exception that could be handled. *)
          raise Fail("Slip-- scanner: unexpected character '" ^ yytext 
                      ^ "' at character number " ^ (Int.toString yypos)) end
| 4 => (OP(Add))
| 6 => (OP(Sub))
| 8 => (OP(Mul))
| _ => raise Internal.LexerError

		) end )

	val {fin,trans} = Unsafe.Vector.sub(Internal.tab, s)
	val NewAcceptingLeaves = fin::AcceptingLeaves
	in if l = !yybl then
	     if trans = #trans(Vector.sub(Internal.tab,0))
	       then action(l,NewAcceptingLeaves
) else	    let val newchars= if !yydone then "" else yyinput 1024
	    in if (size newchars)=0
		  then (yydone := true;
		        if (l=i0) then UserDeclarations.eof ()
		                  else action(l,NewAcceptingLeaves))
		  else (if i0=l then yyb := newchars
		     else yyb := substring(!yyb,i0,l-i0)^newchars;
		     yygone := !yygone+i0;
		     yybl := size (!yyb);
		     scan (s,AcceptingLeaves,l-i0,0))
	    end
	  else let val NewChar = Char.ord(Unsafe.CharVector.sub(!yyb,l))
		val NewChar = if NewChar<128 then NewChar else 128
		val NewState = Char.ord(Unsafe.CharVector.sub(trans,NewChar))
		in if NewState=0 then action(l,NewAcceptingLeaves)
		else scan(NewState,NewAcceptingLeaves,l+1,i0)
	end
	end
(*
	val start= if substring(!yyb,!yybufpos-1,1)="\n"
then !yybegin+1 else !yybegin
*)
	in scan(!yybegin (* start *),nil,!yybufpos,!yybufpos)
    end
end
  in lex
  end
end
