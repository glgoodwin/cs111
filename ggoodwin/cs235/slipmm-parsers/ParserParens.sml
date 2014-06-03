(* Filename: ParseParen.lex

  Summary: Parser for version of Slip-- using fully-parenthesized expressions 

*)

structure ParserParens = 

struct

  open Token
  open AST

  (* Token generation *)
  val scanner = ref (Scanner.stringToScanner "")

  val peekedToken = ref (NONE : token option) (* Must specify type else SML can't infer *)

  (* Remove and return next token from token stream *)
  fun nextToken() = 
    case ! peekedToken of 
        SOME(tok) => (peekedToken := NONE; tok)
      | NONE => (!scanner)() (* Yield next token from scanner *)

  (* Return next token from token stream without removing it *)
  fun peekToken() = 
    case ! peekedToken of 
        SOME(tok) => tok
      | NONE => let val tok = (!scanner)() (* Yield next token from scanner *)
	      in (peekedToken := SOME(tok); tok) (* Remember peeked token *)
	      end

  fun initScanner s = (scanner := s; peekedToken := NONE)

  (* Parsing routines *)
  fun eatPgm () =
    let val body = eatStm()
        val _ = eat EOF 
    in Pgm(body)
    end

  and eatStm () = 
    let val token = nextToken() 
    in case token of 
        ID(str) => let val _ = eat GETS
	             val rhs = eatExp()
		 in Assign(str,rhs)
		 end
      | PRINT => let val arg = eatExp()
		  in Print(arg)
		 end
      | BEGIN => let val stms = eatStmList()
		     val _ = eat END
		 in Seq(stms)
		 end
      | _ => raise Fail ("Unexpected token begins stm: " ^ (Token.toString token))
    end

  and eatStmList () = 
    let val token = peekToken() (* Must peek rather than eat *)
    in case token of       
          END => []
	| _ => let val stm = eatStm()
                   val _ = eat SEMI
	           val stms = eatStmList()
	       in stm::stms
	       end
    end

  and eatExp () = 
    let val token = nextToken() 
    in case token of 
        ID(str) => Id(str)
      | INT(i) => Int(i)
      | LPAREN => let val exp1 = eatExp()
	              val bin = eatBinop()
		      val exp2 = eatExp()
		      val _ = eat RPAREN
 		  in BinApp(exp1,bin,exp2)
		  end
      | _ => raise Fail ("Unexpected token begins exp: " ^ (Token.toString token))
    end

  and eatBinop () = 
    let val token = nextToken()
    in case token of 
        OP(binop) => binop
      | _ => raise Fail ("Expect a binop token but got: " ^ (Token.toString token))
    end

  and eat token =
    let val token' = nextToken()
     in if token = token' then ()
        else raise Fail ("Unexpected token: wanted " ^ (Token.toString token)
                         ^ " but got " ^ (Token.toString token'))
    end

  (* Below, "o" is ML's infix composition operator. *)
  fun stringToExp str = (initScanner(Scanner.stringToScanner str); eatExp())
  fun stringToStm str = (initScanner(Scanner.stringToScanner str); eatStm())
  fun stringToPgm str = (initScanner(Scanner.stringToScanner str); eatPgm())

end (* struct *)
