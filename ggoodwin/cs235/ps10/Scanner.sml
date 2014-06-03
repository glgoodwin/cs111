(* Filename: Scanner.lex

  Summary: Driver routines for using lexical analyzer
           produced by ML-Lex

*)

signature SCANNER = 

  sig 

    type scanner = unit -> Token.token
    (* Abstractly, a scanner represents a stream of tokens. 
       Concretely, a scanner is a function that consumes and returns 
       the next token in the token stream *)

(*
    val stdInToScanner : unit -> scanner
    (* Construct a scanner for tokens in the standard input stream *)
 *)

    val stringToScanner : string -> scanner
    (* Construct a scanner for tokens in the given string *)

    val fileToScanner : string -> scanner
    (* Construct a scanner for tokens in the contents of the specified file *)

    val scannerToTokens : scanner -> Token.token list
    (* Consume all tokens in the scanner and return a list of them, in order. *)

    val printScanner : scanner -> unit
    (* Print (and consume) all tokens in the scanner, one per line *)

    val stringToTokens : string -> Token.token list
    (* Return the list of tokens for a given string *)

    val fileToTokens : string -> Token.token list
    (* Return the list of tokens for the contents of the specified file *)

    val printTokensInString : string -> unit
    (* Print all the tokens in the given string *)

    val printTokensInFile : string -> unit
    (* Print all the tokens in the given string *)

  end


structure Scanner : SCANNER = 

struct

  type scanner = unit -> Token.token

(*
  fun stdInToScanner () 
        = Mlex.makeLexer (fn n => TextIO.inputLine(TextIO.stdIn))
 *)

  fun stringToScanner str =
    (let val done = ref false
     in Mlex.makeLexer (fn n => if (!done) then 
			"" 
				else 
				  (done := true; str)
				  )
     end)

  fun fileToScanner filename =
    (let val inStream = TextIO.openIn(filename)
     in Mlex.makeLexer (fn n => TextIO.inputAll(inStream))
     end)

  fun scannerToTokens scanner = 
      let fun recur () = 
                let val token = scanner() 
                 in if Token.isEof(token) then 
                      []
                    else 
		      token::(recur())
                end
        in recur()
       end

  fun printScanner scanner = 
      let fun loop () = 
                let val token = scanner() 
                 in if Token.isEof(token) then 
                      ()
                    else 
		      (print(Token.toString(token) ^ "\n");
         	       loop())
                end
        in loop()
       end


  (* Below, "o" is ML's infix composition operator. *)
  val stringToTokens = scannerToTokens o stringToScanner
  val fileToTokens = scannerToTokens o fileToScanner
  val printTokensInString = printScanner o stringToScanner 
  val printTokensInFile = printScanner o fileToScanner 

end (* struct *)
