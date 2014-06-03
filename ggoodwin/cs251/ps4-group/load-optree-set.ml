#use "../utils/load-utils.ml"
#use "../sets/SET.ml"
#use "../sets/StandardSet.ml"
#use "../sets/SetTest.ml"
#use "../sets/SortedListSet.ml"
#use "../ps4-group/OperationTreeSet.ml"
module OperationTreeSetTest = SetTest(OperationTreeSet)
let setFromFile = OperationTreeSetTest.setFromFile
let testTiny() = OperationTreeSetTest.testTiny()
let testSmall() = OperationTreeSetTest.testSmall()
let testMedium() = OperationTreeSetTest.testMedium()
let testLarge() = OperationTreeSetTest.testLarge()



