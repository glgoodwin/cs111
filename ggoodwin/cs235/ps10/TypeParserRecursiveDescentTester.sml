(* Filename: TypeParserRecursiveDescentTester.sml

  Summary: Tester for recursive descent parser for type grammar

*)


structure TypeParserRecursiveDescentTester = 

struct

  open Token
  open AST
  open TypeParserRecursiveDescent


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

  fun makeEntry s = (s, stringToSPrime s, stringToTyp s, stringToTypString s) 

  fun makeEntries() = List.map makeEntry strings

  (* These were automatically created by testEntry above *)
  val entries = 
  [("int",
    AToSPrime
      (ATyp (PTyp (LTyp (FBase Int,LPrimeEmpty),PPrimeEmpty),APrimeEmpty)),
    Base Int,"int"),
   ("int list",
    AToSPrime
      (ATyp
         (PTyp (LTyp (FBase Int,LPrimeList LPrimeEmpty),PPrimeEmpty),
          APrimeEmpty)),List (Base Int),"(int list)"),
   ("int * bool",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeEmpty),
             PPrimeProd (LTyp (FBase Bool,LPrimeEmpty),PPrimeEmpty)),
          APrimeEmpty)),Prod (Base Int,Base Bool),"(int * bool)"),
   ("int -> bool",
    AToSPrime
      (ATyp
         (PTyp (LTyp (FBase Int,LPrimeEmpty),PPrimeEmpty),
          APrimeArrow
            (PTyp (LTyp (FBase Bool,LPrimeEmpty),PPrimeEmpty),APrimeEmpty))),
    Arrow (Base Int,Base Bool),"(int -> bool)"),
   ("int * char -> bool",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeEmpty),
             PPrimeProd (LTyp (FBase Char,LPrimeEmpty),PPrimeEmpty)),
          APrimeArrow
            (PTyp (LTyp (FBase Bool,LPrimeEmpty),PPrimeEmpty),APrimeEmpty))),
    Arrow (Prod (Base Int,Base Char),Base Bool),"((int * char) -> bool)"),
   ("int * (char -> bool)",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeEmpty),
             PPrimeProd
               (LTyp
                  (AToF
                     (ATyp
                        (PTyp (LTyp (FBase Char,LPrimeEmpty),PPrimeEmpty),
                         APrimeArrow
                           (PTyp (LTyp (FBase Bool,LPrimeEmpty),PPrimeEmpty),
                            APrimeEmpty))),LPrimeEmpty),PPrimeEmpty)),
          APrimeEmpty)),Prod (Base Int,Arrow (Base Char,Base Bool)),
    "(int * (char -> bool))"),
   ("int * char list -> bool list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeEmpty),
             PPrimeProd (LTyp (FBase Char,LPrimeList LPrimeEmpty),PPrimeEmpty)),
          APrimeArrow
            (PTyp (LTyp (FBase Bool,LPrimeList LPrimeEmpty),PPrimeEmpty),
             APrimeEmpty))),
    Arrow (Prod (Base Int,List (Base Char)),List (Base Bool)),
    "((int * (char list)) -> (bool list))"),
   ("(int * char) list -> bool list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp (FBase Int,LPrimeEmpty),
                         PPrimeProd
                           (LTyp (FBase Char,LPrimeEmpty),PPrimeEmpty)),
                      APrimeEmpty)),LPrimeList LPrimeEmpty),PPrimeEmpty),
          APrimeArrow
            (PTyp (LTyp (FBase Bool,LPrimeList LPrimeEmpty),PPrimeEmpty),
             APrimeEmpty))),
    Arrow (List (Prod (Base Int,Base Char)),List (Base Bool)),
    "(((int * char) list) -> (bool list))"),
   ("((int * char) list -> bool) list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp
                           (AToF
                              (ATyp
                                 (PTyp
                                    (LTyp (FBase Int,LPrimeEmpty),
                                     PPrimeProd
                                       (LTyp (FBase Char,LPrimeEmpty),
                                        PPrimeEmpty)),APrimeEmpty)),
                            LPrimeList LPrimeEmpty),PPrimeEmpty),
                      APrimeArrow
                        (PTyp (LTyp (FBase Bool,LPrimeEmpty),PPrimeEmpty),
                         APrimeEmpty))),LPrimeList LPrimeEmpty),PPrimeEmpty),
          APrimeEmpty)),
    List (Arrow (List (Prod (Base Int,Base Char)),Base Bool)),
    "((((int * char) list) -> bool) list)"),
   ("int * (char list -> bool list)",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeEmpty),
             PPrimeProd
               (LTyp
                  (AToF
                     (ATyp
                        (PTyp
                           (LTyp (FBase Char,LPrimeList LPrimeEmpty),
                            PPrimeEmpty),
                         APrimeArrow
                           (PTyp
                              (LTyp (FBase Bool,LPrimeList LPrimeEmpty),
                               PPrimeEmpty),APrimeEmpty))),LPrimeEmpty),
                PPrimeEmpty)),APrimeEmpty)),
    Prod (Base Int,Arrow (List (Base Char),List (Base Bool))),
    "(int * ((char list) -> (bool list)))"),
   ("int * ((char list -> bool) list)",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeEmpty),
             PPrimeProd
               (LTyp
                  (AToF
                     (ATyp
                        (PTyp
                           (LTyp
                              (AToF
                                 (ATyp
                                    (PTyp
                                       (LTyp
                                          (FBase Char,LPrimeList LPrimeEmpty),
                                        PPrimeEmpty),
                                     APrimeArrow
                                       (PTyp
                                          (LTyp (FBase Bool,LPrimeEmpty),
                                           PPrimeEmpty),APrimeEmpty))),
                               LPrimeList LPrimeEmpty),PPrimeEmpty),
                         APrimeEmpty)),LPrimeEmpty),PPrimeEmpty)),APrimeEmpty)),
    Prod (Base Int,List (Arrow (List (Base Char),Base Bool))),
    "(int * (((char list) -> bool) list))"),
   ("int -> char -> string -> bool",
    AToSPrime
      (ATyp
         (PTyp (LTyp (FBase Int,LPrimeEmpty),PPrimeEmpty),
          APrimeArrow
            (PTyp (LTyp (FBase Char,LPrimeEmpty),PPrimeEmpty),
             APrimeArrow
               (PTyp (LTyp (FBase String,LPrimeEmpty),PPrimeEmpty),
                APrimeArrow
                  (PTyp (LTyp (FBase Bool,LPrimeEmpty),PPrimeEmpty),
                   APrimeEmpty))))),
    Arrow (Base Int,Arrow (Base Char,Arrow (Base String,Base Bool))),
    "(int -> (char -> (string -> bool)))"),
   ("(int -> char) -> string -> bool",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp (LTyp (FBase Int,LPrimeEmpty),PPrimeEmpty),
                      APrimeArrow
                        (PTyp (LTyp (FBase Char,LPrimeEmpty),PPrimeEmpty),
                         APrimeEmpty))),LPrimeEmpty),PPrimeEmpty),
          APrimeArrow
            (PTyp (LTyp (FBase String,LPrimeEmpty),PPrimeEmpty),
             APrimeArrow
               (PTyp (LTyp (FBase Bool,LPrimeEmpty),PPrimeEmpty),APrimeEmpty)))),
    Arrow (Arrow (Base Int,Base Char),Arrow (Base String,Base Bool)),
    "((int -> char) -> (string -> bool))"),
   ("((int -> char) -> string) -> bool",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp
                           (AToF
                              (ATyp
                                 (PTyp
                                    (LTyp (FBase Int,LPrimeEmpty),PPrimeEmpty),
                                  APrimeArrow
                                    (PTyp
                                       (LTyp (FBase Char,LPrimeEmpty),
                                        PPrimeEmpty),APrimeEmpty))),
                            LPrimeEmpty),PPrimeEmpty),
                      APrimeArrow
                        (PTyp (LTyp (FBase String,LPrimeEmpty),PPrimeEmpty),
                         APrimeEmpty))),LPrimeEmpty),PPrimeEmpty),
          APrimeArrow
            (PTyp (LTyp (FBase Bool,LPrimeEmpty),PPrimeEmpty),APrimeEmpty))),
    Arrow (Arrow (Arrow (Base Int,Base Char),Base String),Base Bool),
    "(((int -> char) -> string) -> bool)"),
   ("int * real * char list -> string list -> bool list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeEmpty),
             PPrimeProd
               (LTyp (FBase Real,LPrimeEmpty),
                PPrimeProd
                  (LTyp (FBase Char,LPrimeList LPrimeEmpty),PPrimeEmpty))),
          APrimeArrow
            (PTyp (LTyp (FBase String,LPrimeList LPrimeEmpty),PPrimeEmpty),
             APrimeArrow
               (PTyp (LTyp (FBase Bool,LPrimeList LPrimeEmpty),PPrimeEmpty),
                APrimeEmpty)))),
    Arrow
      (Prod (Prod (Base Int,Base Real),List (Base Char)),
       Arrow (List (Base String),List (Base Bool))),
    "(((int * real) * (char list)) -> ((string list) -> (bool list)))"),
   ("(int * real * char) list -> string list -> bool list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp (FBase Int,LPrimeEmpty),
                         PPrimeProd
                           (LTyp (FBase Real,LPrimeEmpty),
                            PPrimeProd
                              (LTyp (FBase Char,LPrimeEmpty),PPrimeEmpty))),
                      APrimeEmpty)),LPrimeList LPrimeEmpty),PPrimeEmpty),
          APrimeArrow
            (PTyp (LTyp (FBase String,LPrimeList LPrimeEmpty),PPrimeEmpty),
             APrimeArrow
               (PTyp (LTyp (FBase Bool,LPrimeList LPrimeEmpty),PPrimeEmpty),
                APrimeEmpty)))),
    Arrow
      (List (Prod (Prod (Base Int,Base Real),Base Char)),
       Arrow (List (Base String),List (Base Bool))),
    "((((int * real) * char) list) -> ((string list) -> (bool list)))"),
   ("int * real * (char list -> string) list -> bool list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeEmpty),
             PPrimeProd
               (LTyp (FBase Real,LPrimeEmpty),
                PPrimeProd
                  (LTyp
                     (AToF
                        (ATyp
                           (PTyp
                              (LTyp (FBase Char,LPrimeList LPrimeEmpty),
                               PPrimeEmpty),
                            APrimeArrow
                              (PTyp
                                 (LTyp (FBase String,LPrimeEmpty),PPrimeEmpty),
                               APrimeEmpty))),LPrimeList LPrimeEmpty),
                   PPrimeEmpty))),
          APrimeArrow
            (PTyp (LTyp (FBase Bool,LPrimeList LPrimeEmpty),PPrimeEmpty),
             APrimeEmpty))),
    Arrow
      (Prod
         (Prod (Base Int,Base Real),
          List (Arrow (List (Base Char),Base String))),List (Base Bool)),
    "(((int * real) * (((char list) -> string) list)) -> (bool list))"),
   ("(int * real * (char list -> string)) list -> bool list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp (FBase Int,LPrimeEmpty),
                         PPrimeProd
                           (LTyp (FBase Real,LPrimeEmpty),
                            PPrimeProd
                              (LTyp
                                 (AToF
                                    (ATyp
                                       (PTyp
                                          (LTyp
                                             (FBase Char,
                                              LPrimeList LPrimeEmpty),
                                           PPrimeEmpty),
                                        APrimeArrow
                                          (PTyp
                                             (LTyp (FBase String,LPrimeEmpty),
                                              PPrimeEmpty),APrimeEmpty))),
                                  LPrimeEmpty),PPrimeEmpty))),APrimeEmpty)),
                LPrimeList LPrimeEmpty),PPrimeEmpty),
          APrimeArrow
            (PTyp (LTyp (FBase Bool,LPrimeList LPrimeEmpty),PPrimeEmpty),
             APrimeEmpty))),
    Arrow
      (List
         (Prod
            (Prod (Base Int,Base Real),Arrow (List (Base Char),Base String))),
       List (Base Bool)),
    "((((int * real) * ((char list) -> string)) list) -> (bool list))"),
   ("int * real * (char list -> string list) -> bool list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeEmpty),
             PPrimeProd
               (LTyp (FBase Real,LPrimeEmpty),
                PPrimeProd
                  (LTyp
                     (AToF
                        (ATyp
                           (PTyp
                              (LTyp (FBase Char,LPrimeList LPrimeEmpty),
                               PPrimeEmpty),
                            APrimeArrow
                              (PTyp
                                 (LTyp (FBase String,LPrimeList LPrimeEmpty),
                                  PPrimeEmpty),APrimeEmpty))),LPrimeEmpty),
                   PPrimeEmpty))),
          APrimeArrow
            (PTyp (LTyp (FBase Bool,LPrimeList LPrimeEmpty),PPrimeEmpty),
             APrimeEmpty))),
    Arrow
      (Prod
         (Prod (Base Int,Base Real),
          Arrow (List (Base Char),List (Base String))),List (Base Bool)),
    "(((int * real) * ((char list) -> (string list))) -> (bool list))"),
   ("int * real * ((char list -> string) list) -> bool list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeEmpty),
             PPrimeProd
               (LTyp (FBase Real,LPrimeEmpty),
                PPrimeProd
                  (LTyp
                     (AToF
                        (ATyp
                           (PTyp
                              (LTyp
                                 (AToF
                                    (ATyp
                                       (PTyp
                                          (LTyp
                                             (FBase Char,
                                              LPrimeList LPrimeEmpty),
                                           PPrimeEmpty),
                                        APrimeArrow
                                          (PTyp
                                             (LTyp (FBase String,LPrimeEmpty),
                                              PPrimeEmpty),APrimeEmpty))),
                                  LPrimeList LPrimeEmpty),PPrimeEmpty),
                            APrimeEmpty)),LPrimeEmpty),PPrimeEmpty))),
          APrimeArrow
            (PTyp (LTyp (FBase Bool,LPrimeList LPrimeEmpty),PPrimeEmpty),
             APrimeEmpty))),
    Arrow
      (Prod
         (Prod (Base Int,Base Real),
          List (Arrow (List (Base Char),Base String))),List (Base Bool)),
    "(((int * real) * (((char list) -> string) list)) -> (bool list))"),
   ("(int * real * char list -> string list) -> bool list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp (FBase Int,LPrimeEmpty),
                         PPrimeProd
                           (LTyp (FBase Real,LPrimeEmpty),
                            PPrimeProd
                              (LTyp (FBase Char,LPrimeList LPrimeEmpty),
                               PPrimeEmpty))),
                      APrimeArrow
                        (PTyp
                           (LTyp (FBase String,LPrimeList LPrimeEmpty),
                            PPrimeEmpty),APrimeEmpty))),LPrimeEmpty),
             PPrimeEmpty),
          APrimeArrow
            (PTyp (LTyp (FBase Bool,LPrimeList LPrimeEmpty),PPrimeEmpty),
             APrimeEmpty))),
    Arrow
      (Arrow
         (Prod (Prod (Base Int,Base Real),List (Base Char)),
          List (Base String)),List (Base Bool)),
    "((((int * real) * (char list)) -> (string list)) -> (bool list))"),
   ("(int * real * char list -> string list -> bool) list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp (FBase Int,LPrimeEmpty),
                         PPrimeProd
                           (LTyp (FBase Real,LPrimeEmpty),
                            PPrimeProd
                              (LTyp (FBase Char,LPrimeList LPrimeEmpty),
                               PPrimeEmpty))),
                      APrimeArrow
                        (PTyp
                           (LTyp (FBase String,LPrimeList LPrimeEmpty),
                            PPrimeEmpty),
                         APrimeArrow
                           (PTyp (LTyp (FBase Bool,LPrimeEmpty),PPrimeEmpty),
                            APrimeEmpty)))),LPrimeList LPrimeEmpty),
             PPrimeEmpty),APrimeEmpty)),
    List
      (Arrow
         (Prod (Prod (Base Int,Base Real),List (Base Char)),
          Arrow (List (Base String),Base Bool))),
    "((((int * real) * (char list)) -> ((string list) -> bool)) list)"),
   ("int * real list -> char * string list -> bool * unit list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeEmpty),
             PPrimeProd (LTyp (FBase Real,LPrimeList LPrimeEmpty),PPrimeEmpty)),
          APrimeArrow
            (PTyp
               (LTyp (FBase Char,LPrimeEmpty),
                PPrimeProd
                  (LTyp (FBase String,LPrimeList LPrimeEmpty),PPrimeEmpty)),
             APrimeArrow
               (PTyp
                  (LTyp (FBase Bool,LPrimeEmpty),
                   PPrimeProd
                     (LTyp (FBase Unit,LPrimeList LPrimeEmpty),PPrimeEmpty)),
                APrimeEmpty)))),
    Arrow
      (Prod (Base Int,List (Base Real)),
       Arrow
         (Prod (Base Char,List (Base String)),
          Prod (Base Bool,List (Base Unit)))),
    "((int * (real list)) -> ((char * (string list)) -> (bool * (unit list))))"),
   ("int list * char list list -> bool list list list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeList LPrimeEmpty),
             PPrimeProd
               (LTyp (FBase Char,LPrimeList (LPrimeList LPrimeEmpty)),
                PPrimeEmpty)),
          APrimeArrow
            (PTyp
               (LTyp
                  (FBase Bool,
                   LPrimeList (LPrimeList (LPrimeList LPrimeEmpty))),
                PPrimeEmpty),APrimeEmpty))),
    Arrow
      (Prod (List (Base Int),List (List (Base Char))),
       List (List (List (Base Bool)))),
    "(((int list) * ((char list) list)) -> (((bool list) list) list))"),
   ("int list * (char list list -> bool list list list)",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeList LPrimeEmpty),
             PPrimeProd
               (LTyp
                  (AToF
                     (ATyp
                        (PTyp
                           (LTyp
                              (FBase Char,LPrimeList (LPrimeList LPrimeEmpty)),
                            PPrimeEmpty),
                         APrimeArrow
                           (PTyp
                              (LTyp
                                 (FBase Bool,
                                  LPrimeList
                                    (LPrimeList (LPrimeList LPrimeEmpty))),
                               PPrimeEmpty),APrimeEmpty))),LPrimeEmpty),
                PPrimeEmpty)),APrimeEmpty)),
    Prod
      (List (Base Int),
       Arrow (List (List (Base Char)),List (List (List (Base Bool))))),
    "((int list) * (((char list) list) -> (((bool list) list) list)))"),
   ("int list * (char list list -> bool list list) list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeList LPrimeEmpty),
             PPrimeProd
               (LTyp
                  (AToF
                     (ATyp
                        (PTyp
                           (LTyp
                              (FBase Char,LPrimeList (LPrimeList LPrimeEmpty)),
                            PPrimeEmpty),
                         APrimeArrow
                           (PTyp
                              (LTyp
                                 (FBase Bool,
                                  LPrimeList (LPrimeList LPrimeEmpty)),
                               PPrimeEmpty),APrimeEmpty))),
                   LPrimeList LPrimeEmpty),PPrimeEmpty)),APrimeEmpty)),
    Prod
      (List (Base Int),
       List (Arrow (List (List (Base Char)),List (List (Base Bool))))),
    "((int list) * ((((char list) list) -> ((bool list) list)) list))"),
   ("int list * (char list list -> bool list) list list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeList LPrimeEmpty),
             PPrimeProd
               (LTyp
                  (AToF
                     (ATyp
                        (PTyp
                           (LTyp
                              (FBase Char,LPrimeList (LPrimeList LPrimeEmpty)),
                            PPrimeEmpty),
                         APrimeArrow
                           (PTyp
                              (LTyp (FBase Bool,LPrimeList LPrimeEmpty),
                               PPrimeEmpty),APrimeEmpty))),
                   LPrimeList (LPrimeList LPrimeEmpty)),PPrimeEmpty)),
          APrimeEmpty)),
    Prod
      (List (Base Int),
       List (List (Arrow (List (List (Base Char)),List (Base Bool))))),
    "((int list) * (((((char list) list) -> (bool list)) list) list))"),
   ("int list * (char list list -> bool) list list list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp (FBase Int,LPrimeList LPrimeEmpty),
             PPrimeProd
               (LTyp
                  (AToF
                     (ATyp
                        (PTyp
                           (LTyp
                              (FBase Char,LPrimeList (LPrimeList LPrimeEmpty)),
                            PPrimeEmpty),
                         APrimeArrow
                           (PTyp (LTyp (FBase Bool,LPrimeEmpty),PPrimeEmpty),
                            APrimeEmpty))),
                   LPrimeList (LPrimeList (LPrimeList LPrimeEmpty))),
                PPrimeEmpty)),APrimeEmpty)),
    Prod
      (List (Base Int),
       List (List (List (Arrow (List (List (Base Char)),Base Bool))))),
    "((int list) * ((((((char list) list) -> bool) list) list) list))"),
   ("(int list * char list list -> bool list list) list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp (FBase Int,LPrimeList LPrimeEmpty),
                         PPrimeProd
                           (LTyp
                              (FBase Char,LPrimeList (LPrimeList LPrimeEmpty)),
                            PPrimeEmpty)),
                      APrimeArrow
                        (PTyp
                           (LTyp
                              (FBase Bool,LPrimeList (LPrimeList LPrimeEmpty)),
                            PPrimeEmpty),APrimeEmpty))),
                LPrimeList LPrimeEmpty),PPrimeEmpty),APrimeEmpty)),
    List
      (Arrow
         (Prod (List (Base Int),List (List (Base Char))),
          List (List (Base Bool)))),
    "((((int list) * ((char list) list)) -> ((bool list) list)) list)"),
   ("(int list * char list list -> bool list) list list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp (FBase Int,LPrimeList LPrimeEmpty),
                         PPrimeProd
                           (LTyp
                              (FBase Char,LPrimeList (LPrimeList LPrimeEmpty)),
                            PPrimeEmpty)),
                      APrimeArrow
                        (PTyp
                           (LTyp (FBase Bool,LPrimeList LPrimeEmpty),
                            PPrimeEmpty),APrimeEmpty))),
                LPrimeList (LPrimeList LPrimeEmpty)),PPrimeEmpty),APrimeEmpty)),
    List
      (List
         (Arrow
            (Prod (List (Base Int),List (List (Base Char))),List (Base Bool)))),
    "(((((int list) * ((char list) list)) -> (bool list)) list) list)"),
   ("(int list * (char list) list -> bool list) list list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp (FBase Int,LPrimeList LPrimeEmpty),
                         PPrimeProd
                           (LTyp
                              (AToF
                                 (ATyp
                                    (PTyp
                                       (LTyp
                                          (FBase Char,LPrimeList LPrimeEmpty),
                                        PPrimeEmpty),APrimeEmpty)),
                               LPrimeList LPrimeEmpty),PPrimeEmpty)),
                      APrimeArrow
                        (PTyp
                           (LTyp (FBase Bool,LPrimeList LPrimeEmpty),
                            PPrimeEmpty),APrimeEmpty))),
                LPrimeList (LPrimeList LPrimeEmpty)),PPrimeEmpty),APrimeEmpty)),
    List
      (List
         (Arrow
            (Prod (List (Base Int),List (List (Base Char))),List (Base Bool)))),
    "(((((int list) * ((char list) list)) -> (bool list)) list) list)"),
   ("((int list * char list) list -> bool) list list list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp
                           (AToF
                              (ATyp
                                 (PTyp
                                    (LTyp (FBase Int,LPrimeList LPrimeEmpty),
                                     PPrimeProd
                                       (LTyp
                                          (FBase Char,LPrimeList LPrimeEmpty),
                                        PPrimeEmpty)),APrimeEmpty)),
                            LPrimeList LPrimeEmpty),PPrimeEmpty),
                      APrimeArrow
                        (PTyp (LTyp (FBase Bool,LPrimeEmpty),PPrimeEmpty),
                         APrimeEmpty))),
                LPrimeList (LPrimeList (LPrimeList LPrimeEmpty))),PPrimeEmpty),
          APrimeEmpty)),
    List
      (List
         (List
            (Arrow (List (Prod (List (Base Int),List (Base Char))),Base Bool)))),
    "(((((((int list) * (char list)) list) -> bool) list) list) list)"),
   ("((int list * char) list list -> bool) list list list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp
                           (AToF
                              (ATyp
                                 (PTyp
                                    (LTyp (FBase Int,LPrimeList LPrimeEmpty),
                                     PPrimeProd
                                       (LTyp (FBase Char,LPrimeEmpty),
                                        PPrimeEmpty)),APrimeEmpty)),
                            LPrimeList (LPrimeList LPrimeEmpty)),PPrimeEmpty),
                      APrimeArrow
                        (PTyp (LTyp (FBase Bool,LPrimeEmpty),PPrimeEmpty),
                         APrimeEmpty))),
                LPrimeList (LPrimeList (LPrimeList LPrimeEmpty))),PPrimeEmpty),
          APrimeEmpty)),
    List
      (List
         (List
            (Arrow (List (List (Prod (List (Base Int),Base Char))),Base Bool)))),
    "((((((((int list) * char) list) list) -> bool) list) list) list)"),
   ("((int list * (char list list -> bool list)) list) list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp
                           (AToF
                              (ATyp
                                 (PTyp
                                    (LTyp (FBase Int,LPrimeList LPrimeEmpty),
                                     PPrimeProd
                                       (LTyp
                                          (AToF
                                             (ATyp
                                                (PTyp
                                                   (LTyp
                                                      (FBase Char,
                                                       LPrimeList
                                                         (LPrimeList
                                                            LPrimeEmpty)),
                                                    PPrimeEmpty),
                                                 APrimeArrow
                                                   (PTyp
                                                      (LTyp
                                                         (FBase Bool,
                                                          LPrimeList
                                                            LPrimeEmpty),
                                                       PPrimeEmpty),
                                                    APrimeEmpty))),
                                           LPrimeEmpty),PPrimeEmpty)),
                                  APrimeEmpty)),LPrimeList LPrimeEmpty),
                         PPrimeEmpty),APrimeEmpty)),LPrimeList LPrimeEmpty),
             PPrimeEmpty),APrimeEmpty)),
    List
      (List
         (Prod
            (List (Base Int),Arrow (List (List (Base Char)),List (Base Bool))))),
    "((((int list) * (((char list) list) -> (bool list))) list) list)"),
   ("((int list * char list) list -> (bool list) list) list",
    AToSPrime
      (ATyp
         (PTyp
            (LTyp
               (AToF
                  (ATyp
                     (PTyp
                        (LTyp
                           (AToF
                              (ATyp
                                 (PTyp
                                    (LTyp (FBase Int,LPrimeList LPrimeEmpty),
                                     PPrimeProd
                                       (LTyp
                                          (FBase Char,LPrimeList LPrimeEmpty),
                                        PPrimeEmpty)),APrimeEmpty)),
                            LPrimeList LPrimeEmpty),PPrimeEmpty),
                      APrimeArrow
                        (PTyp
                           (LTyp
                              (AToF
                                 (ATyp
                                    (PTyp
                                       (LTyp
                                          (FBase Bool,LPrimeList LPrimeEmpty),
                                        PPrimeEmpty),APrimeEmpty)),
                               LPrimeList LPrimeEmpty),PPrimeEmpty),
                         APrimeEmpty))),LPrimeList LPrimeEmpty),PPrimeEmpty),
          APrimeEmpty)),
    List
      (Arrow
         (List (Prod (List (Base Int),List (Base Char))),
          List (List (Base Bool)))),
    "(((((int list) * (char list)) list) -> ((bool list) list)) list)")]

  datatype testResult = OK 
                    | Error of string * S' * string * S'
                               * string * typ * string * typ
                               * string * string * string * string
                    | Failure of string

  fun testEntry (string, sprime, typ, typString) =
    let val sprime' = stringToSPrime string
        val typ' = SPrimeToTyp sprime'
        val typString' = AST.typToString typ'
     in (string, 
	 if sprime = sprime'
	 then OK 
	 else Error("Expected S' =", sprime, "but actually got S' =", sprime',
                    "Expected typ =", typ, "but actually got typ =", typ',
                    "Expected typString =", typString, "but actually got typ =", typString'))
    end
    handle Fail s => (string, Failure s)
	      
  fun testParser() = List.map testEntry entries

end (* struct *)
