#use "../utils/load-list-utils.ml"
#use "../utils/StringUtils.ml"
#use "../utils/MakeFunTesterOld.ml"
#use "MATRIX.ml"
#use "MakeMatrixTester.ml"
#use "ListMatrix.ml"

module ListMatrixTester = MakeMatrixTester(ListMatrix) 

let testListMatrix = ListMatrixTester.test
