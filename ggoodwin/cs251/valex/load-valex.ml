#use "../bindex/load-bindex.ml"
#use "../valex/Valex.ml" 
(* #use "../valex/ValexDesugarAllAtOnce.ml" *)
#use "../valex/ValexEnvInterp.ml"
#use "../valex/ValexSubstInterp.ml"
#use "../valex/ValexInterpTest.ml"
module ValexEnvInterpTest = ValexInterpTest(ValexEnvInterp)
let testEnvInterp = ValexEnvInterpTest.test 
module ValexSubstInterpTest = ValexInterpTest(ValexSubstInterp)
let testSubstInterp = ValexSubstInterpTest.test 



