#use "../valex/load-valex.ml"
#use "../hofl/Env.ml" (* new version of env that has a fixpoint operator *)
#use "../hofl/Hofl.ml"
#use "../hofl/HoflEnvInterp.ml"
#use "../hofl/HoflEnvInterpDynamic.ml"
#use "../hofl/HoflSubstInterp.ml" 
#use "../hofl/HoflInterpTest.ml"
module HoflEnvInterpTest = HoflInterpTest(HoflEnvInterp)
let testEnvInterp = HoflEnvInterpTest.test 
module HoflSubstInterpTest = HoflInterpTest(HoflSubstInterp)

(* For testing PS7, problem 3a *)
let testSubstInterp = HoflSubstInterpTest.test 

let testHoflString expString = 
  let print = StringUtils.print 
  and println = StringUtils.println
  and pgmString = "(hofl () " ^ expString ^ ")" in
  let _ = print "Value of expression in static scope: " in 
  let staticValString = 
    try
      Hofl.valuToString (HoflEnvInterp.runString pgmString [])
    with
      Hofl.EvalError(str) -> str
    | Hofl.SyntaxError(str) -> str
    | Sexp.IllFormedSexp(str) -> str in
  let _ = println  staticValString in
  let _ = print "Value of expression in dynamic scope: " in 
  let dynamicValString = 
    try
      Hofl.valuToString (HoflEnvInterpDynamic.runString pgmString [])
    with
      Hofl.EvalError(str) -> str
    | Hofl.SyntaxError(str) -> str
    | Sexp.IllFormedSexp(str) -> str in
  let _ = println dynamicValString in
  ()

let testHoflFile expFile = testHoflString (File.fileToString expFile)

let runFileAll file args = 
  let println = StringUtils.println in 
  let static_string =  
    try
      Hofl.valuToString (HoflEnvInterp.runFile file args)
    with
      Hofl.EvalError(str) -> str
    | Hofl.SyntaxError(str) -> str
    | Sexp.IllFormedSexp(str) -> str in
  let _ = println ("Value of program with static scope: " ^ static_string) in
  let dynamic_string =  
    try
      Hofl.valuToString (HoflEnvInterpDynamic.runFile file args)
    with
      Hofl.EvalError(str) -> str
    | Hofl.SyntaxError(str) -> str
    | Sexp.IllFormedSexp(str) -> str in
  let _ = println ("Value of program with dynamic scope: " ^ dynamic_string) in
  ()

  
  
					      








