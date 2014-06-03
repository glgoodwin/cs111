(* Filename: TypeParserRecursiveDescent.sml

  Summary: Recursive descent parser for predictive version of type grammar

*)


structure TypeParserRecursiveDescent = 

struct

  open Token
  open AST

  (* Datatatypes for transformed grammar *)
  datatype S' = AToSPrime of A
  and A =  ATyp of P * A'
  and A' = APrimeEmpty | APrimeArrow of P * A'
  and P = PTyp of L * P'
  and P' = PPrimeEmpty | PPrimeProd of L * P'
  and L = LTyp of F * L'
  and L' = LPrimeEmpty | LPrimeList of L'
  and F = FBase of baseTy | AToF of A

  (* Converting the above alternative AST datatype to AST.typ *)
  fun SPrimeToTyp (AToSPrime a) = AToTyp a
  and AToTyp (ATyp(p,a')) = APrimeToTyp(PToTyp(p),a')
  and APrimeToTyp(typ,APrimeEmpty) = typ
    | APrimeToTyp(typ,APrimeArrow(p,t')) = 
       let val typ' = APrimeToTyp(PToTyp(p),t') (* Ensure that arrow is right associative *)
       in Arrow(typ,typ') 
       end
  and PToTyp (PTyp(l,p')) = PPrimeToTyp(LToTyp(l), p')
  and PPrimeToTyp (typ,PPrimeEmpty) = typ
    | PPrimeToTyp (typ,PPrimeProd(l,p')) = PPrimeToTyp(Prod(typ,LToTyp(l)),p')
       (* Ensure that prod is left associative *)
  and LToTyp (LTyp(f,l')) = LPrimeToTyp(FToTyp(f), l')
  and LPrimeToTyp (typ,LPrimeEmpty) = typ
    | LPrimeToTyp (typ,LPrimeList(l')) = LPrimeToTyp(List(typ),l')
  and FToTyp (FBase(b)) = Base(b)
    | FToTyp (AToF(a)) = AToTyp(a)

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

  fun eatS'() = (* flesh this out *)
    raise Fail ("eatS' is unimplemented")

  and eatA() = (* flesh this out *)
    raise Fail ("eatA is unimplemented")

  and eatA'() = (* flesh this out *)
    raise Fail ("eatA' is unimplemented")

  and eatP() =  (* flesh this out *)
    raise Fail ("eatP is unimplemented")

  and eatP'() = (* flesh this out *)
    raise Fail ("eatP' is unimplemented")

  and eatL() = (* flesh this out *)
    raise Fail ("eatL is unimplemented")

  and eatL'() = (* flesh this out *)
    raise Fail ("eatL' is unimplemented")

  and eatF() = (* flesh this out *)
    raise Fail ("eatLF is unimplemented")

  and eat token =
    let val token' = nextToken()
     in if token = token' then ()
        else raise Fail ("Unexpected token in eat: wanted " ^ (Token.toString token)
                         ^ " but got " ^ (Token.toString token'))
    end

  fun stringToSPrime str = (initScanner(Scanner.stringToScanner str); eatS'())
  fun stringToTyp str = SPrimeToTyp (stringToSPrime str)
  fun stringToTypString str = AST.typToString (stringToTyp str)

end (* struct *)
