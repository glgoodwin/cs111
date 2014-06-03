module TestDecorate : 
    (sig 
      val testOne : string -> string -> unit
      val testAll : unit ->  unit
     end) = 

struct
      
let testOne string1 string2 = 
  let decorateString = "(decorate " ^ string1 ^ " " ^ string2 ^ ") " in 
  let _ = StringUtils.print decorateString in 
  let hoilecPgm = "(hoilec () " ^ decorateString ^ " (load \"decorate.hec\"))" in
  let hoilecResultString = 
    try Hoilec.valuToString(HoilecEnvInterp.runString hoilecPgm []) with
      Hoilec.EvalError s -> "Hoilec.EvalError: " ^ s
    | Hoilec.SyntaxError s -> "Hoilec.SyntaxError: " ^ s
    | Sexp.IllFormedSexp s -> "Sexp.IllFormedSexp: " ^ s
    | Sys_error s -> "Sys_error: " ^ s in 
  let foflPgm = "(fofl () " ^ decorateString ^ " (load \"decorate.ffl\"))" in
  let foflResultString = 
    try Fofl.valuToString(FoflEnvInterp.runString FoflEnvInterp.static foflPgm []) with 
      Fofl.EvalError s -> "Fofl.EvalError: " ^ s
    | Fofl.SyntaxError s -> "Fofl.SyntaxError: " ^ s
    | Sexp.IllFormedSexp s -> "Sexp.IllFormedSexp: " ^ s
    | Sys_error s -> "Sys_error: " ^ s in 
  let _ = 
    if (hoilecResultString = foflResultString)
    then StringUtils.println "OK!"
    else let _ = StringUtils.println "\n********** ERROR: MISMATCH BETWEEN RESULTS! **********" in 
         let _ = StringUtils.println ("HOILEC result: " ^ hoilecResultString) in 
	 let _ = StringUtils.println ("  FOFL result: " ^ foflResultString) in 
	 StringUtils.println "******************************************************\n" in 
  ()

let testSuite =
  [ 
    ("(list 1 2)","17");
    ("(list 1 2)","#e");
    ("(list 1 2)","(list 23 #t 'c' (sym foo) \"bar\")"); 
    ("(list 4 5 6)","(list 23 #t 'c' (sym foo) \"bar\")");
    ("(list 'p' \"q\" (sym r))", "(list 23 #t 'c' (sym foo) \"bar\")");
    ("(list 1 2 3 4 5 6 7)","(list 23 #t 'c' (sym foo) \"bar\")");
    ("(list 1 2)","(list (list \"a\" \"b\" (list \"c\" \"d\")) \"e\" (list (list \"f\") \"g\"))");
    ("(list 4 5 6)","(list (list \"a\" \"b\" (list \"c\" \"d\")) \"e\" (list (list \"f\") \"g\"))");
    ("(list 'p' \"q\" (sym r))","(list (list \"a\" \"b\" (list \"c\" \"d\")) \"e\" (list (list \"f\") \"g\"))");
    ("(list 1 2 3 4 5 6 7)","(list (list \"a\" \"b\" (list \"c\" \"d\")) \"e\" (list (list \"f\") \"g\"))");
] 

let testAll () = 
  let _ = StringUtils.println "" in 
  let _ = ListUtils.for_each (fun (str1,str2) -> testOne str1 str2) testSuite in 
  StringUtils.println "" 
end
  


	


									
