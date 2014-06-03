#use "../utils/load-list-utils.ml"
#use "../utils/StringUtils.ml"
#use "../utils/MakeFunTesterOld.ml"
#use "MATRIX.ml"
#use "MakeMatrixTester.ml"
#use "FunMatrix.ml"

module FunMatrixTester = MakeMatrixTester(FunMatrix) 

let testFunMatrix = FunMatrixTester.test

