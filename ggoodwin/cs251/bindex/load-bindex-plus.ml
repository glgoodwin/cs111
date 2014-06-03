#use "../intex/load-intex.ml"
#use "../bindex/BindexPlus.ml"
#use "../bindex/BindexPlusEnvInterp.ml"
#use "../bindex/BindexPlusSubstInterp.ml"
#use "../bindex/BindexPlusInterpTest.ml"
module BindexPlusEnvInterpTest = BindexPlusInterpTest(BindexPlusEnvInterp)
let testEnvInterp = BindexPlusEnvInterpTest.test 
module BindexPlusSubstInterpTest = BindexPlusInterpTest(BindexPlusSubstInterp)
let testSubstInterp = BindexPlusSubstInterpTest.test



