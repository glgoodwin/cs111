#use "../utils/load-utils.ml"
#use "../intex/load-intex.ml"
#use "../bindex/Bindex.ml"
#use "../bindex/BindexEnvInterp.ml"
#use "../bindex/BindexSubstInterp.ml"
#use "../bindex/BindexInterpTest.ml"
module BindexEnvInterpTest = BindexInterpTest(BindexEnvInterp)
let testEnvInterp = BindexEnvInterpTest.test 
module BindexSubstInterpTest = BindexInterpTest(BindexSubstInterp)
let testSubstInterp = BindexSubstInterpTest.test



