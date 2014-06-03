#use "../utils/load-utils.ml"
#use "../sets/SET.ml"
#use "../sets/StandardSet.ml"
#use "../sets/SetTest.ml"
#use "../sets/SortedListSet.ml"
module SortedListSetTest = SetTest(SortedListSet)
let setFromFile = SortedListSetTest.setFromFile
let testTiny() = SortedListSetTest.testTiny()
let testSmall() = SortedListSetTest.testSmall()
let testMedium() = SortedListSetTest.testMedium()
let testLarge() = SortedListSetTest.testLarge()
