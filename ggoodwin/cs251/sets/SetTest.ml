module SetTest (Set: SET) : sig 
  val setPrintFlag: bool -> unit
  val setFromFile: string -> string Set.set 
  val testFile: string -> unit 
  val testTiny: unit -> unit
  val testSmall: unit -> unit
  val testMedium: unit -> unit
  val testLarge: unit -> unit
  val testSomeSmall: unit -> unit
  val testAll: unit -> unit
end = struct 

  module SU = StringUtils
  module SS = StandardSet(String) (* The reference implementation against which we compare Set *)

  let printFlag = ref false

  let setPrintFlag bool = printFlag := bool

  let rec fileToList filename = 
    let _ = SU.print ("Reading " ^ filename ^ " into list ...") in 
    ListUtils.map String.lowercase (File.fileToWords filename)

  let setFromFile filename = 
    let _ = SU.print ("Reading " ^ filename ^ " into list ...") in 
    let words = ListUtils.map String.lowercase (File.fileToWords filename) in
    let _ = SU.print ("done; read " ^ (string_of_int (List.length words)) ^ " words from file\n") in 
    let _ = SU.print ("Creating set ...") in 
    let start = Sys.time() in 
    let result = Set.fromList(words) in
    let stop = Sys.time() in 
    let _ = SU.print ("done (time = " ^ (string_of_float (stop-.start)) ^ " seconds)\n") in 	
    result
    
  let rec fromList kind setFromList words = 
    let len = List.length words in 
    let _ = SU.print (
      "done\n"
      ^ "List has "
      ^ (string_of_int len)
      ^ " elements\nCreating " ^ kind ^ " set1 from first 2/3 of list ...") in 
    let start1 = Sys.time() in 
    let set1 = setFromList (ListUtils.take (2*len/3) words) in 
    let stop1 = Sys.time() in 
    let _ = SU.print ("done (time = " ^ (string_of_float (stop1-.start1)) ^ " seconds)\n") in
    let _ = SU.print ("Creating " ^ kind ^ " set2 from last 2/3 of list ...") in 
    let start2 = Sys.time() in 
    let set2 = setFromList (ListUtils.drop (len/3) words) in 
    let stop2 = Sys.time() in 
    let _ = SU.print ("done (time = " ^ (string_of_float (stop2-.start2)) ^ " seconds)\n") in
    (set1, set2)

  let rec compareLists xs ys = 
    match (xs,ys) with
      ([], []) -> "OK!\n"
    | (x::xs', y::ys') -> 
	if x = y then compareLists xs' ys'
	else "\n***ERROR***: First set begins with " 
          ^ x ^ " but second set begins with " ^ y ^ "\n"
    |  ([], y::ys') -> 
        "\n***ERROR***: First set exhausted "
        ^ "when second begins with " ^ y ^ "\n"
    |  (x::xs', []) -> 
        "\n***ERROR***: Second set exhausted "
        ^ "when second begins with " ^ x ^ "\n"

  let test1 name (s1,_,s1',_) s3_thunk s3'_thunk toString toString' compare = 
    let _ = SU.print ("Testing " ^ name ^ " ...") in 
    let start = Sys.time() in 
    let s3 = s3_thunk() in 
    let stop = Sys.time() in 
    let _ = SU.print ("(time = " ^ (string_of_float (stop-.start)) ^ " seconds) ") in
    let s3' = s3'_thunk() in 
    let str = compare s3 s3'
    in if str = "OK!\n" then SU.print str else 
    let _ = SU.print str in 
    let _ = SU.print ("\nYour set:\n" ^ (Set.toString FunUtils.id s1)) in
    let _ = SU.print ("\n\nStandard set:\n" ^ (SS.toString FunUtils.id s1')) in 
    let _ = SU.print ("\n\nYour result:\n" ^ (toString s3)) in
    let _ = SU.print ("\n\nStandard result:\n" ^ (toString' s3') ^ "\n\n") in 
    ()

  let test2 name (s1,s2,s1',s2') s3_thunk s3'_thunk toString toString' compare = 
    let _ = SU.print ("Testing " ^ name ^ " ...") in 
    let _ = if (! printFlag) then SU.print ("\ns1:\n" ^ (Set.toString FunUtils.id s1)) in 
    let _ = if (! printFlag) then SU.print ("\n\ns2:\n" ^ (Set.toString FunUtils.id s2)) in 
    let start = Sys.time() in 
    let s3 = s3_thunk() in 
    let stop = Sys.time() in 
    let _ = SU.print ("(time = " ^ (string_of_float (stop-.start)) ^ " seconds) ") in 
    let _ = if (! printFlag) then SU.print ("\n\nresult:\n" ^ (Set.toString FunUtils.id s3)) in 
    let s3' = s3'_thunk() in
    let str = compare s3 s3' 
    in if str = "OK!\n" then SU.print str else 
    let _ = SU.print str in 
    let _ = SU.print ("\nYour set 1:\n" ^ (Set.toString FunUtils.id s1)) in
    let _ = SU.print ("\n\nStandard set 1:\n" ^ (SS.toString FunUtils.id s1')) in 
    let _ = SU.print ("\n\nYour set 2:\n" ^ (Set.toString FunUtils.id s2)) in
    let _ = SU.print ("\n\nStandard set 2:\n" ^ (SS.toString FunUtils.id s2')) in 
    let _ = SU.print ("\n\nYour result:\n" ^ (toString s3)) in
    let _ = SU.print ("\n\nStandard result:\n" ^ (toString' s3') ^ "\n\n") in 
    ()
      
  let rec testFile filename = 
    let words = fileToList filename in 				       
    (* (* Testing code *)
    let _ = SU.print (SU.listToString FunUtils.id words) in
       *)
    let (set1,set2) = fromList "test" Set.fromList words in 
    let (set1',set2') = fromList "standard" SS.fromList words in 
    let sets = (set1,set2,set1',set2') in 
    (testToList sets; testInsert sets; testDelete sets ; 
     testMember sets; testSize sets; testIsEmpty sets; 
     testUnion sets; testIntersection sets; testDifference sets; 
     testSexp sets; testToString sets;)

  (* No other tests will work properly if toList doesn't work! *)
  and testToList ((s1,_,s1',_) as sets) = 
    test1 "toList" sets
      (fun () -> (Set.toList s1))
      (fun () -> (SS.toList s1'))
      (SU.listToString FunUtils.id)
      (SU.listToString FunUtils.id)
      compareLists

  (* (* This won't work properly since different sets can have
        different s-expression representations *)
  and testToSexp ((s1,_,s1',_) as sets) = 
    let stringToSexp s = Sexp.Str s in 
    test1 "toSexp" sets
      (fun () -> (Set.toSexp stringToSexp s1))
      (fun () -> (SS.toSexp stringToSexp s1'))
      Sexp.sexpToString 
      Sexp.sexpToString
      (fun sexp sexp' -> 
	if sexp = sexp' then 
	  "OK!\n" 
	else 
	  "\n***ERROR***: Unequal s-expressions.")
  *)

  and testMember ((s1,s2,s1',s2') as sets) =
    let xs = Set.toList s2
    and xs' = SS.toList s2' in 
    test1 "member" sets 
      (fun () -> ListUtils.filter (fun x -> Set.member x s1) xs)
      (fun () -> ListUtils.filter (fun x -> SS.member x s1') xs')
      (SU.listToString FunUtils.id)
      (SU.listToString FunUtils.id)
      compareLists

  and testInsert ((s1,_,s1',_) as sets) =
    test1 "insert" sets 
      (fun () -> (Set.insert "aaa" (Set.insert "zzz" (Set.insert "mmm" s1))))
      (fun () -> (SS.insert "aaa" (SS.insert "zzz" (SS.insert "mmm" s1'))))
      (Set.toString FunUtils.id)
      (SS.toString FunUtils.id)
      compareToLists
      
  and testDelete ((s1,_,s1',_) as sets) = 
    test1 "delete" sets
      (fun () -> (Set.delete "aaa" (Set.delete "zzz" (Set.delete "mmm" s1))))
      (fun () -> (SS.delete "aaa" (SS.delete "zzz" (SS.delete "mmm" s1'))))
      (Set.toString FunUtils.id)
      (SS.toString FunUtils.id)
      compareToLists

  and testSize ((s1,_,s1',_) as sets) = 
    (test1 "size (nonEmpty)" sets
       (fun () -> (Set.size s1))
       (fun () -> (SS.size s1'))
       SU.intToString
       SU.intToString
       compareInts;
     let empty = Set.empty
     and empty' = SS.empty in 
     test1 "size (empty)" sets
       (fun () -> (Set.size empty))
       (fun () -> (SS.size empty'))
       SU.intToString
       SU.intToString
       compareInts)

  and testIsEmpty ((s1,_,s1',_) as sets) = 
    (test1 "isEmpty (nonEmpty)" sets
       (fun () -> (Set.isEmpty s1))
       (fun () -> (SS.isEmpty s1'))
       SU.boolToString
       SU.boolToString
       compareBools;
     let empty = Set.empty
     and empty' = SS.empty in 
     test1 "isEmpty (empty)" sets
       (fun () -> (Set.isEmpty empty))
       (fun () -> (SS.isEmpty empty'))
       SU.boolToString
       SU.boolToString
       compareBools)

  and testUnion ((s1,s2,s1',s2') as sets) =
    test2 "union" sets 
      (fun () -> (Set.union s1 s2)) 
      (fun () -> (SS.union s1' s2'))
      (Set.toString FunUtils.id)
      (SS.toString FunUtils.id)
      compareToLists
      
  and testIntersection ((s1,s2,s1',s2') as sets) = 
    test2 "intersection" sets 
      (fun () -> (Set.intersection s1 s2))
      (fun () -> (SS.intersection s1' s2'))
      (Set.toString FunUtils.id)
      (SS.toString FunUtils.id)
      compareToLists
      
  and testDifference ((s1,s2,s1',s2') as sets) = 
    (* (* Testing code *)
    let s1Sexp =  Set.toSexp (fun str -> Sexp.Sym str) s1 in 
    let s1Bintree = Bintree.fromSexp Sexp.sexpToString s1Sexp in 
    let s2Sexp =  Set.toSexp (fun str -> Sexp.Sym str) s2 in 
    let s2Bintree = Bintree.fromSexp Sexp.sexpToString s2Sexp in 
    let _ = StringUtils.print("\nheight(s1)= " ^ (string_of_int (Bintree.height s1Bintree))) in 
    let _ = StringUtils.print("\nheight(s2)= " ^ (string_of_int (Bintree.height s2Bintree))) in 
    let _ = StringUtils.print("\ns2 =\n " ^ (Sexp.sexpToString s2Sexp)) in
    let _ = StringUtils.print("\n(postlist s2) =\n " ^
			      (StringUtils.listToString
				 FunUtils.id 
				 (Bintree.postlist s2Bintree))) in 
       *)
    test2 "difference" sets 
      (fun () -> (Set.difference s1 s2))
      (fun () -> (SS.difference s1' s2'))
      (Set.toString FunUtils.id)
      (SS.toString FunUtils.id)
      compareToLists

  (* Since sexp representations differ for different sets, we test
     toSexp and fromSexp at the same time by converting a set to 
     an s-expression and then back to a set again. This does not 
     necessarily mean that toSexp and fromSexp are working correctly,
     but at least it shows they are consistent. *)
  and testSexp ((s1,_,s1',_) as sets) = 
    let stringToSexp s = Sexp.Str s in 
    test1 "toSexp/fromSexp" sets 
      (fun () -> (Set.fromSexp Sexp.sexpToString (Set.toSexp stringToSexp s1)))
      (fun () -> (SS.fromSexp Sexp.sexpToString (SS.toSexp stringToSexp s1')))
      (Set.toString FunUtils.id)
      (SS.toString FunUtils.id)
      compareToLists

  and testToString ((s1,_,s1',_) as sets) = 
    test1 "toString" sets 
      (fun () -> (Set.toString FunUtils.id s1))
      (fun () -> (SS.toString FunUtils.id s1'))
      FunUtils.id
      FunUtils.id
      (fun str str' -> 
	if str = str' then 
	  "OK!\n" 
	else 
	  "\n***ERROR***: Unequal strings.")

  and compareToLists s s' = compareLists (Set.toList s) (SS.toList s')
  and compareInts i i' = if i = i' then "OK!\n" else 
                         "The integers " ^ (string_of_int i) ^
                         " and " ^ (string_of_int i') ^ " are not the same."
  and compareBools b b' = if b = b' then "OK!\n" else 
                         "The booleans " ^ (string_of_bool b) ^
                         " and " ^ (string_of_bool b') ^ " are not the same."
					     
  and testTiny () = testFile "../text/tiny-unsorted.txt" 
  and testSmall () = testFile "../text/small-unsorted.txt" 
  and testMedium () = testFile "../text/medium-unsorted.txt" 
  and testLarge () = testFile "../text/large-unsorted.txt" 
  and testSomeSmall () = 
    (testFile "../text/20-unsorted.txt"; 
     testFile "../text/40-unsorted.txt"; 
     testFile "../text/80-unsorted.txt")
  and testAll () = 
    (testFile "../text/1000-unsorted.txt"; 
     testFile "../text/2000-unsorted.txt"; 
     testFile "../text/4000-unsorted.txt"; 
     testFile "../text/8000-unsorted.txt"; 
     testFile "../text/16000-unsorted.txt"; 
     testFile "../text/32000-unsorted.txt")

      
end

