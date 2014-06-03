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
let testSubstInterp = HoflSubstInterpTest.test 








