(* Filename: TypeParserShiftReduceTester.sml

  Summary: Tester for shift-reduce parser for type grammar

*)

structure TypeParserShiftReduceTester = 

struct

  open Token
  open AST
  open TypeParserShiftReduce


  val strings = 
    ["int",
     "int list",
     "int * bool",
     "int -> bool",
     "int * char -> bool",
     "int * (char -> bool)",
     "int * char list -> bool list",
     "(int * char) list -> bool list",
     "((int * char) list -> bool) list",
     "int * (char list -> bool list)",
     "int * ((char list -> bool) list)",
     "int -> char -> string -> bool",
     "(int -> char) -> string -> bool",
     "((int -> char) -> string) -> bool",
     "int * real * char list -> string list -> bool list",
     "(int * real * char) list -> string list -> bool list",
     "int * real * (char list -> string) list -> bool list",
     "(int * real * (char list -> string)) list -> bool list",
     "int * real * (char list -> string list) -> bool list",
     "int * real * ((char list -> string) list) -> bool list",
     "(int * real * char list -> string list) -> bool list",
     "(int * real * char list -> string list -> bool) list", 
     "int * real list -> char * string list -> bool * unit list",
     "int list * char list list -> bool list list list", 
     "int list * (char list list -> bool list list list)", 
     "int list * (char list list -> bool list list) list", 
     "int list * (char list list -> bool list) list list",
     "int list * (char list list -> bool) list list list", 
     "(int list * char list list -> bool list list) list", 
     "(int list * char list list -> bool list) list list",
     "(int list * (char list) list -> bool list) list list",
     "((int list * char list) list -> bool) list list list", 
     "((int list * char) list list -> bool) list list list", 
     "((int list * (char list list -> bool list)) list) list", 
     "((int list * char list) list -> (bool list) list) list"
     ]

  fun makeEntry s = (s, stringToTyp s, stringToTypString s) 

  fun makeEntries() = List.map makeEntry strings

  (* These were automatically created by testEntry above *)
  val entries = 
  [("int",Base Int,"int"),("int list",List (Base Int),"(int list)"),
   ("int * bool",Prod (Base Int,Base Bool),"(int * bool)"),
   ("int -> bool",Arrow (Base Int,Base Bool),"(int -> bool)"),
   ("int * char -> bool",Arrow (Prod (Base Int,Base Char),Base Bool),
    "((int * char) -> bool)"),
   ("int * (char -> bool)",Prod (Base Int,Arrow (Base Char,Base Bool)),
    "(int * (char -> bool))"),
   ("int * char list -> bool list",
    Arrow (Prod (Base Int,List (Base Char)),List (Base Bool)),
    "((int * (char list)) -> (bool list))"),
   ("(int * char) list -> bool list",
    Arrow (List (Prod (Base Int,Base Char)),List (Base Bool)),
    "(((int * char) list) -> (bool list))"),
   ("((int * char) list -> bool) list",
    List (Arrow (List (Prod (Base Int,Base Char)),Base Bool)),
    "((((int * char) list) -> bool) list)"),
   ("int * (char list -> bool list)",
    Prod (Base Int,Arrow (List (Base Char),List (Base Bool))),
    "(int * ((char list) -> (bool list)))"),
   ("int * ((char list -> bool) list)",
    Prod (Base Int,List (Arrow (List (Base Char),Base Bool))),
    "(int * (((char list) -> bool) list))"),
   ("int -> char -> string -> bool",
    Arrow (Base Int,Arrow (Base Char,Arrow (Base String,Base Bool))),
    "(int -> (char -> (string -> bool)))"),
   ("(int -> char) -> string -> bool",
    Arrow (Arrow (Base Int,Base Char),Arrow (Base String,Base Bool)),
    "((int -> char) -> (string -> bool))"),
   ("((int -> char) -> string) -> bool",
    Arrow (Arrow (Arrow (Base Int,Base Char),Base String),Base Bool),
    "(((int -> char) -> string) -> bool)"),
   ("int * real * char list -> string list -> bool list",
    Arrow
      (Prod (Prod (Base Int,Base Real),List (Base Char)),
       Arrow (List (Base String),List (Base Bool))),
    "(((int * real) * (char list)) -> ((string list) -> (bool list)))"),
   ("(int * real * char) list -> string list -> bool list",
    Arrow
      (List (Prod (Prod (Base Int,Base Real),Base Char)),
       Arrow (List (Base String),List (Base Bool))),
    "((((int * real) * char) list) -> ((string list) -> (bool list)))"),
   ("int * real * (char list -> string) list -> bool list",
    Arrow
      (Prod
         (Prod (Base Int,Base Real),
          List (Arrow (List (Base Char),Base String))),List (Base Bool)),
    "(((int * real) * (((char list) -> string) list)) -> (bool list))"),
   ("(int * real * (char list -> string)) list -> bool list",
    Arrow
      (List
         (Prod
            (Prod (Base Int,Base Real),Arrow (List (Base Char),Base String))),
       List (Base Bool)),
    "((((int * real) * ((char list) -> string)) list) -> (bool list))"),
   ("int * real * (char list -> string list) -> bool list",
    Arrow
      (Prod
         (Prod (Base Int,Base Real),
          Arrow (List (Base Char),List (Base String))),List (Base Bool)),
    "(((int * real) * ((char list) -> (string list))) -> (bool list))"),
   ("int * real * ((char list -> string) list) -> bool list",
    Arrow
      (Prod
         (Prod (Base Int,Base Real),
          List (Arrow (List (Base Char),Base String))),List (Base Bool)),
    "(((int * real) * (((char list) -> string) list)) -> (bool list))"),
   ("(int * real * char list -> string list) -> bool list",
    Arrow
      (Arrow
         (Prod (Prod (Base Int,Base Real),List (Base Char)),
          List (Base String)),List (Base Bool)),
    "((((int * real) * (char list)) -> (string list)) -> (bool list))"),
   ("(int * real * char list -> string list -> bool) list",
    List
      (Arrow
         (Prod (Prod (Base Int,Base Real),List (Base Char)),
          Arrow (List (Base String),Base Bool))),
    "((((int * real) * (char list)) -> ((string list) -> bool)) list)"),
   ("int * real list -> char * string list -> bool * unit list",
    Arrow
      (Prod (Base Int,List (Base Real)),
       Arrow
         (Prod (Base Char,List (Base String)),
          Prod (Base Bool,List (Base Unit)))),
    "((int * (real list)) -> ((char * (string list)) -> (bool * (unit list))))"),
   ("int list * char list list -> bool list list list",
    Arrow
      (Prod (List (Base Int),List (List (Base Char))),
       List (List (List (Base Bool)))),
    "(((int list) * ((char list) list)) -> (((bool list) list) list))"),
   ("int list * (char list list -> bool list list list)",
    Prod
      (List (Base Int),
       Arrow (List (List (Base Char)),List (List (List (Base Bool))))),
    "((int list) * (((char list) list) -> (((bool list) list) list)))"),
   ("int list * (char list list -> bool list list) list",
    Prod
      (List (Base Int),
       List (Arrow (List (List (Base Char)),List (List (Base Bool))))),
    "((int list) * ((((char list) list) -> ((bool list) list)) list))"),
   ("int list * (char list list -> bool list) list list",
    Prod
      (List (Base Int),
       List (List (Arrow (List (List (Base Char)),List (Base Bool))))),
    "((int list) * (((((char list) list) -> (bool list)) list) list))"),
   ("int list * (char list list -> bool) list list list",
    Prod
      (List (Base Int),
       List (List (List (Arrow (List (List (Base Char)),Base Bool))))),
    "((int list) * ((((((char list) list) -> bool) list) list) list))"),
   ("(int list * char list list -> bool list list) list",
    List
      (Arrow
         (Prod (List (Base Int),List (List (Base Char))),
          List (List (Base Bool)))),
    "((((int list) * ((char list) list)) -> ((bool list) list)) list)"),
   ("(int list * char list list -> bool list) list list",
    List
      (List
         (Arrow
            (Prod (List (Base Int),List (List (Base Char))),List (Base Bool)))),
    "(((((int list) * ((char list) list)) -> (bool list)) list) list)"),
   ("(int list * (char list) list -> bool list) list list",
    List
      (List
         (Arrow
            (Prod (List (Base Int),List (List (Base Char))),List (Base Bool)))),
    "(((((int list) * ((char list) list)) -> (bool list)) list) list)"),
   ("((int list * char list) list -> bool) list list list",
    List
      (List
         (List
            (Arrow (List (Prod (List (Base Int),List (Base Char))),Base Bool)))),
    "(((((((int list) * (char list)) list) -> bool) list) list) list)"),
   ("((int list * char) list list -> bool) list list list",
    List
      (List
         (List
            (Arrow (List (List (Prod (List (Base Int),Base Char))),Base Bool)))),
    "((((((((int list) * char) list) list) -> bool) list) list) list)"),
   ("((int list * (char list list -> bool list)) list) list",
    List
      (List
         (Prod
            (List (Base Int),Arrow (List (List (Base Char)),List (Base Bool))))),
    "((((int list) * (((char list) list) -> (bool list))) list) list)"),
   ("((int list * char list) list -> (bool list) list) list",
    List
      (Arrow
         (List (Prod (List (Base Int),List (Base Char))),
          List (List (Base Bool)))),
    "(((((int list) * (char list)) list) -> ((bool list) list)) list)")]

  datatype testResult = OK 
                    | Error of string * typ * string * typ
                               * string * string * string * string
                    | Failure of string

  fun testEntry (string, typ, typString) =
    let val typ' = stringToTyp string
        val typString' = AST.typToString typ'
     in (string, 
	 if typ = typ'
	 then OK 
	 else Error("Expected typ =", typ, "but actually got typ =", typ',
                    "Expected typString =", typString, "but actually got typ =", typString'))
    end
    handle Fail s => (string, Failure s)
	      
  fun testParser() = List.map testEntry entries

end (* struct *)
