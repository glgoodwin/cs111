signature PARSER = 

  sig 

    val stringToPgm : string -> AST.pgm
    (* Construct an AST for a program *)

    val stringToStm : string -> AST.stm
    (* Construct an AST for a statement *)

    val stringToExp : string -> AST.exp
    (* Construct an AST for an expression *)

  end


