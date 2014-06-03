(* Load the version of the HOFL environment-model interpreter
   in which (1) *all* names in environments are bound to thunks
   and (2) the BINDREC rule is more complex *)
#use "../valex/load-valex.ml"
#use "../hofl/Env.ml" 
#use "../hofl/HoflThunks.ml"
#use "../hofl/HoflEnvInterpThunks.ml"
(* #use "../hofl/HoflEnvInterpDynamic.ml" *)
(* #use "../hofl/HoflSubstInterp.ml" *)
#use "../hofl/HoflInterpTest.ml"
module HoflEnvInterpTest = HoflInterpTest(HoflEnvInterp)
let testEnvInterp = HoflEnvInterpTest.test 









