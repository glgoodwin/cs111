#use "../valex/load-valex.ml" 
#use "../fofl/Fofl.ml"
#use "../fofl/FoflEnvInterp.ml"

(* For testing PS7, problem 3c *)
let testFoflScope scopeName pgmString = 
  let print = StringUtils.print 
  and println = StringUtils.println
  and nameToScope s = 
    match s with 
      "static" -> FoflEnvInterp.static 
    | "dynamic" -> FoflEnvInterp.dynamic
    | "empty" -> FoflEnvInterp.empty
    | "merged" -> FoflEnvInterp.merged
    | _ -> raise (Failure ("Unknown scope " ^ s)) in
  let _ = print ("Value of expression with " ^ scopeName ^ " scope: ") in
  let valString = 
    try
      Fofl.valuToString (FoflEnvInterp.runString (nameToScope scopeName) pgmString [5])
    with
      Fofl.EvalError(str) -> str
    | Fofl.SyntaxError(str) -> str
    | Sexp.IllFormedSexp(str) -> str in
  let _ = println valString in
  ()

let testFoflString pgmString = 
  let _ = testFoflScope "static" pgmString in
  let _ = testFoflScope "dynamic" pgmString in
  let _ = testFoflScope "empty" pgmString in
  let _ = testFoflScope "merged" pgmString in
  ()

let testFoflFile expFile = testFoflString (File.fileToString expFile)









