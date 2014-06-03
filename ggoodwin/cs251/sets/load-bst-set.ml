#use "../utils/load-utils.ml"
#use "../sets/SET.ml"
#use "../sets/StandardSet.ml"
#use "../sets/SetTest.ml"
#use "../sets/BSTSet.ml"
module BSTSetTest = SetTest(BSTSet)
let setFromFile = BSTSetTest.setFromFile
let testTiny() = BSTSetTest.testTiny()
let testSmall() = BSTSetTest.testSmall()
let testMedium() = BSTSetTest.testMedium()
let testLarge() = BSTSetTest.testLarge()
