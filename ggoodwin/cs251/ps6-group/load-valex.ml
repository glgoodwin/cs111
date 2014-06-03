#use "../bindex/load-bindex.ml"
#use "Valex.ml" (* Use the local version of this file, which *will* be modified in Problem 4 *)
#use "../valex/ValexEnvInterp.ml"
#use "../valex/ValexSubstInterp.ml"
#use "ValexInterpTest.ml" (* Use the local version of this file, which *may* be modified in Problem 4 *)
module ValexEnvInterpTest = ValexInterpTest(ValexEnvInterp)
let testEnvInterp = ValexEnvInterpTest.test 
module ValexSubstInterpTest = ValexInterpTest(ValexSubstInterp)
let testSubstInterp = ValexSubstInterpTest.test 



