Standard ML of New Jersey v110.68 [built: Thu Dec 11 21:18:30 2008]
- use "ps3.sml"
= ;
[opening ps3.sml]
[autoloading]
[library $SMLNJ-BASIS/basis.cm is stable]
[autoloading done]
val extend = fn : string * string list -> string list
val caesar = fn : int * string -> string
val twist = fn : char * string -> string
val transform = fn : string -> string
val first = fn : string -> string
val butFirst = fn : string -> string
val LTests =
  ["","a","b","aa","ab","ba","bb","aaa","aab","aba","abb","baa",...]
  : string list
val transform2 = fn : string -> string
val countabRec = fn : string -> int * int
val countabIter = fn : string -> int * int
val isInL1 = fn : string -> bool
val genstringsHelp = fn : string list * string list -> string list
val genStrings = fn : int * char list -> string list
val it = () : unit
- List.partition isInL1 LTests
= ;
val it =
  (["","b","aa","ab","ba","bb","aab","aba","baa","bbb"],
   ["a","aaa","abb","bab","bba"]) : string list * string list


- isInL1 "a"
= ;
val it = false : bool
- isInL1 "b";
val it = true : bool
- isInL1 "ab";
val it = true : bool
- isInL1 "aabb";
val it = true : bool
- isInL1 "aabbb";
val it = true : bool
- isInL1 "aaabbbb";
val it = false : bool
- isInL1 "aaabbbbv";
val it = false : bool
- isInL1 "aaabbbbvba";
val it = true : bool
- isInL1 "aaabbbbvbakgha";
val it = true : bool
- isInL1 "aaaaaaaaammmmmmm";
val it = false : bool
- isInL1 "aaaaaaaaammmmmmmb";
val it = true : bool
- isInL1 "aaaaaab";
val it = true : bool
- isInL1 "aaaaaaabb";
val it = false : bool
- isInL1 "aaaaaabbb";
val it = true : bool
- isInL1 "ab";
val it = true : bool
- isInL1 "abb";
val it = false : bool
- isInL1 "aabb";
val it = true : bool
- isInL1 "b";
val it = true : bool
- isInL1 "bbbbbbbbb";
val it = true : bool


- genStrings (0,[#"a"]);
val it = [""] : string list
- genStrings (1,[#"a"]);
val it = ["a",""] : string list
- genStrings (2,[#"a"]);
val it = ["aa","a",""] : string list
- genStrings (3,[#"a"]);
val it = ["aaa","aa","a",""] : string list
- genStrings (1,[#"a", #"b"]);
val it = ["a","b",""] : string list
- genStrings (2,[#"a", #"b"]);
val it = ["aa","ab","ba","bb","a","b",""] : string list
- genStrings (3,[#"a", #"b"]);
val it =
  ["aaa","aab","aba","abb","baa","bab","bba","bbb","aa","ab","ba","bb",...]
  : string list
- genStrings (4,[#"a", #"b"]);
val it =
  ["aaaa","aaab","aaba","aabb","abaa","abab","abba","abbb","baaa","baab",
   "baba","babb",...] : string list
- Control.Print.printLength := 1000;
val it = () : unit
- genStrings (4,[#"a", #"b"]);
val it =
  ["aaaa","aaab","aaba","aabb","abaa","abab","abba","abbb","baaa","baab",
   "baba","babb","bbaa","bbab","bbba","bbbb","aaa","aab","aba","abb","baa",
   "bab","bba","bbb","aa","ab","ba","bb","a","b",""] : string list
- genStrings (1,[#"a", #"b",#"c"]);
val it = ["a","b","c",""] : string list
- genStrings (2,[#"a", #"b",#"c"]);
val it = ["aa","ab","ac","ba","bb","bc","ca","cb","cc","a","b","c",""]
  : string list
- genStrings (3,[#"a", #"b",#"c"]);
val it =
  ["aaa","aab","aac","aba","abb","abc","aca","acb","acc","baa","bab","bac",
   "bba","bbb","bbc","bca","bcb","bcc","caa","cab","cac","cba","cbb","cbc",
   "cca","ccb","ccc","aa","ab","ac","ba","bb","bc","ca","cb","cc","a","b","c",
   ""] : string list
- genStrings (4,[#"a", #"b",#"c"]);
val it =
  ["aaaa","aaab","aaac","aaba","aabb","aabc","aaca","aacb","aacc","abaa",
   "abab","abac","abba","abbb","abbc","abca","abcb","abcc","acaa","acab",
   "acac","acba","acbb","acbc","acca","accb","accc","baaa","baab","baac",
   "baba","babb","babc","baca","bacb","bacc","bbaa","bbab","bbac","bbba",
   "bbbb","bbbc","bbca","bbcb","bbcc","bcaa","bcab","bcac","bcba","bcbb",
   "bcbc","bcca","bccb","bccc","caaa","caab","caac","caba","cabb","cabc",
   "caca","cacb","cacc","cbaa","cbab","cbac","cbba","cbbb","cbbc","cbca",
   "cbcb","cbcc","ccaa","ccab","ccac","ccba","ccbb","ccbc","ccca","cccb",
   "cccc","aaa","aab","aac","aba","abb","abc","aca","acb","acc","baa","bab",
   "bac","bba","bbb","bbc","bca","bcb","bcc","caa","cab","cac","cba","cbb",
   "cbc","cca","ccb","ccc","aa","ab","ac","ba","bb","bc","ca","cb","cc","a",
   "b","c",""] : string list
- List.length(genStrings (4,[#"a", #"b",#"c"]));
val it = 121 : int

- 