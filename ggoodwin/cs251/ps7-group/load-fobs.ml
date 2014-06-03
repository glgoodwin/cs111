#use "../valex/load-valex.ml"
#use "../fobs/Fobs.ml"
#use "../hofl/Env.ml" (* environments with fixed points *)
#use "../fobs/FobsEnvInterp.ml" 
let runFile filename args = 
  FobsEnvInterp.runFile FobsEnvInterp.static FobsEnvInterp.static filename args

(* For testing PS7, problem 3b *)
let testFobsScope varScopeName funScopeName expString = 
  let print = StringUtils.print 
  and println = StringUtils.println
  and pgmString = "(fobs () " ^ expString ^ ")" 
  and nameToScope s = 
    match s with 
      "static" -> FobsEnvInterp.static 
    | "dynamic" -> FobsEnvInterp.dynamic
    | _ -> raise (Failure ("Unknown scope " ^ s)) in
  let _ = print ("Value of expression with " ^ varScopeName ^ 
    " variable scope and " ^ funScopeName ^ " function scope: ") in
  let valString = 
    try
      Fobs.valuToString (FobsEnvInterp.runString (nameToScope varScopeName) (nameToScope funScopeName) pgmString [])
    with
      Fobs.EvalError(str) -> str
    | Fobs.SyntaxError(str) -> str
    | Sexp.IllFormedSexp(str) -> str in
  let _ = println valString in
  ()

let testFobsString expString = 
  let _ = testFobsScope "static" "static" expString in
  let _ = testFobsScope "dynamic" "static" expString in
  let _ = testFobsScope "static" "dynamic" expString in
  let _ = testFobsScope "dynamic" "dynamic" expString in
  ()

let testFobsFile expFile = testFobsString (File.fileToString expFile)

