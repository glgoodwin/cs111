#use "../valex/load-valex.ml"
#use "../ps7-individual/Loopex.ml" 
#use "../ps7-individual/LoopexEnvInterp.ml"
#use "../ps7-individual/LoopexSubstInterp.ml"
#use "../ps7-individual/LoopexInterpTest.ml"
module LoopexEnvInterpTest = LoopexInterpTest(LoopexEnvInterp)
let testEnvInterp = LoopexEnvInterpTest.test 
module LoopexSubstInterpTest = LoopexInterpTest(LoopexSubstInterp)
let testSubstInterp = LoopexSubstInterpTest.test 



