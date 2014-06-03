#use "../valex/load-valex.ml"
#use "../fobs/Fobs.ml"
#use "../hofl/Env.ml" (* environments with fixed points *)
#use "../fobs/FobsEnvInterp.ml" 
let runFile filename args = 
  FobsEnvInterp.runFile FobsEnvInterp.static FobsEnvInterp.static filename args
(* 
#use "../folf/FoflSubstInterp.ml" 
#use "../folf/FoflInterpTest.ml"
module FoflEnvInterpTest = FoflInterpTest(FoflEnvInterp)
let testEnvInterp = FoflEnvInterpTest.test 
module FoflSubstInterpTest = FoflInterpTest(FoflSubstInterp)
let testSubstInterp = FoflSubstInterpTest.test 
*)

let runFileScopeName varScopeName funScopeName file args = 
  let print = StringUtils.print 
  and println = StringUtils.println
  and nameToScope s = 
    match s with 
      "static" -> FobsEnvInterp.static 
    | "dynamic" -> FobsEnvInterp.dynamic
    | _ -> raise (Failure ("Unknown scope " ^ s)) in
  let _ = print ("Value of expression with " ^ varScopeName ^ 
    " variable scope and " ^ funScopeName ^ " function scope: ") in
  let valString = 
    try
      Fobs.valuToString (FobsEnvInterp.runFile (nameToScope varScopeName) (nameToScope funScopeName) file args)
    with
      Fobs.EvalError(str) -> str
    | Fobs.SyntaxError(str) -> str
    | Sexp.IllFormedSexp(str) -> str in
  let _ = println valString in
  ()


let runFileAll file args = 
  let _ = runFileScopeName "static" "static" file args in 
  let _ = runFileScopeName "dynamic" "static" file args in 
  let _ = runFileScopeName "static" "dynamic" file args in 
  let _ = runFileScopeName "dynamic" "dynamic" file args in 
  ()
  







