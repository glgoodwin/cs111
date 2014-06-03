signature PARSER = 

  sig 

    val stringToPgm : string -> AST.pgm
    (* Construct an AST for a program *)

    val stringToExp : string -> AST.exp
    (* Construct an AST for an expression *)

  end


