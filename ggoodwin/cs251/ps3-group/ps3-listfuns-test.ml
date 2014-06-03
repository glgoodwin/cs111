(************************************************************ 
 Testing for list functions in CS251 Spring'07 PS3 
 ************************************************************)

let print s = (print_string s; flush_all ())

let test (name, fcn, arg_to_string, ans_to_string, entries) = 
  let test_entry (arg,ans) =
    let _ = print (name ^ " " ^ (arg_to_string arg) ^ ": ") in 
    let res = (fcn arg)
     in if res = ans then 
          print "OK!\n"
        else 
          print ("*** ERROR ***"
                 ^ "\n**************************************************"
                 ^ "\nexpected:\n" 
                 ^ (ans_to_string ans)
                 ^ "\nactual:\n" 
                 ^ (ans_to_string res)
                 ^ "\n**************************************************\n")
   in List.iter test_entry entries

let pair_stringer (left_stringer, right_stringer) = 
  fun (x,y) -> 
    "(" ^ (left_stringer x) ^ "," ^ (right_stringer y) ^ ")"

let triple_stringer (left_stringer, middle_stringer, right_stringer) = 
  fun (x,y,z) -> 
    "(" ^ (left_stringer x) ^ 
    "," ^ (middle_stringer y) ^
    "," ^ (right_stringer z) ^ ")"


let list_stringer elt_stringer = 
  fun xs -> 
    match xs with 
      [] -> "[]"
    | [x] -> "[" ^ (elt_stringer x) ^ "]"
    | (x::xs) -> "[" 
                 ^ (elt_stringer x)
                 ^ (List.fold_right 
                         (fun x str -> "," ^ (elt_stringer x) ^ str)
                         xs
                         "]")

let line () = print "------------------------------------------------------------\n"

type sum = 
    Int of int
  | Char of char

let intfn x = Int x
let charfn x = Char x
let pairfn (f,g) (x,y) = (f x, g y)

let string_of_sum x = 
  match x with 
    Int i -> string_of_int i
  | Char c -> String.make 1 c

let testa () = 
  test ("sum_multiples_of_3_or_5", 
        (fun (m,n) -> sum_multiples_of_3_or_5 m n), 
        pair_stringer(string_of_int,string_of_int), 
        string_of_int,
        [
         ((0,10),33);
         ((-9,12),22);
         ((18,18),18);
         ((10,0),0);
         ]
        )

let testb () = 
  test ("all_contain_multiple", 
        (fun (n,nss) -> all_contain_multiple n nss), 
        pair_stringer(string_of_int,
		      list_stringer(list_stringer(string_of_int))),
        string_of_bool,
        [
         ((5, [[17;10;12]; [25]; [3;7;5]]), true);
         ((3, [[17;10;12]; [25]; [3;7;5]]), false);
         ((3, []), true);
         ]	
        )

let testc () =
  test ("inner_product", 
        (fun (xs, ys) -> inner_product xs ys), 
        pair_stringer(list_stringer(string_of_int), 
		      list_stringer(string_of_int)), 
        string_of_int,
        [(([1;2;3], [4;5;6]), 32); 
         (([], []), 0)]
	)

let testd () = 
  test ("alts", 
        alts, 
        list_stringer(string_of_int), 
        pair_stringer(list_stringer(string_of_int), 
		      list_stringer(string_of_int)),
        [([7;5;4;6;9;2;8;3], ([7; 4; 9; 8], [5; 6; 2; 3]));
         ([7;5;4;6;9;2;8], ([7; 4; 9; 8], [5; 6; 2]));
         ([7], ([7], []));
         ([], ([], []))
         ]	
        )

let teste () = 
  test ("cartesian_product", 
        (fun (xs,ys) -> cartesian_product xs ys), 
        pair_stringer(list_stringer(string_of_sum), 
		      list_stringer(string_of_sum)),
        list_stringer(pair_stringer(string_of_sum, string_of_sum)),
        [((List.map intfn [1; 2], List.map charfn ['a'; 'b'; 'c']), 
           List.map (pairfn(intfn,charfn))
                    [(1, 'a'); (1, 'b'); (1, 'c'); (2, 'a'); (2, 'b'); (2, 'c')]);
	  ((List.map intfn [2; 1], List.map charfn ['a'; 'b'; 'c']), 
           List.map (pairfn(intfn,charfn))
                    [(2, 'a'); (2, 'b'); (2, 'c'); (1, 'a'); (1, 'b'); (1, 'c')]);
	  ((List.map charfn ['c'; 'a'; 'b'], List.map intfn [2; 1]),
            List.map (pairfn(charfn,intfn))
                     [('c', 2); ('c', 1); ('a', 2); ('a', 1); ('b', 2); ('b', 1)]);
	  ((List.map intfn [1], List.map charfn ['a']), 
           List.map (pairfn(intfn,charfn)) [(1, 'a')]);
	  (([], List.map charfn ['c'; 'a'; 'b']), [])
	] 
        )

let testf () = 
  test ("bits", 
        bits, 
	string_of_int,
	list_stringer(string_of_int), 
        [(5, [1; 0; 1]); 
         (10, [1; 0; 1; 0]); 
	 (11, [1; 0; 1; 1]); 
         (22, [1; 0; 1; 1; 0]); 
         (23, [1; 0; 1; 1; 1]); 
         (46, [1; 0; 1; 1; 1; 0])
	]
	)

let testg () =
  test ("decimal", 
	decimal,
        list_stringer(string_of_int), 
	string_of_int,
        [
         ([], 0);
         ([0], 0);
         ([1], 1);
         ([1;0], 2);
         ([1;0;0], 4);
         ([1;0;1], 5);
         ([1;0;1;0], 10);
         ([1;0;1;1], 11);
         ([1;0;1;1;0], 22);
         ([1;0;1;1;1], 23);
         ([1;0;1;1;1;0], 46);
	])

let testh () =
  test ("n_fold", 
	(fun (num, fcn, fcnString, arg) -> (n_fold num fcn arg)),
        (fun (num, fcn, fcnString, arg) -> 
	  triple_stringer(string_of_int,id,string_of_int) (num, fcnString,arg)),
	string_of_int,
        [
         ((5, ((+) 1), "((+) 1)", 0), 5);
         ((5, (( * ) 2), "(( * ) 2)", 1), 32);
         ((3, ((flip (/)) 2), "((flip (/)) 2)", 100), 12);
         ((0, ((flip (/)) 2), "((flip (/)) 2)", 100), 100)
	])

let testi () =
  test ("inserts", 
        (fun (x,ys) -> inserts x ys), 
	pair_stringer(string_of_int,
  	              list_stringer(string_of_int)), 
	list_stringer(list_stringer(string_of_int)), 
        [
         ((3, [5;7;1]), [[3; 5; 7; 1]; [5; 3; 7; 1]; [5; 7; 3; 1]; [5; 7; 1; 3]]); 
         ((3, [5;3;1]), [[3; 5; 3; 1]; [5; 3; 3; 1]; [5; 3; 3; 1]; [5; 3; 1; 3]]); 
         ((3, []), [[3]])
	]
	)

let testj () = 
  test ("permutations", 
        (fun xs -> List.sort Pervasives.compare (permutations xs)), 
	list_stringer(string_of_int),
	list_stringer(list_stringer(string_of_int)),
        [
         ([], [[]]); 
         ([4], [[4]]); 
         ([3;4], [[3;4];[4;3]]);
         ([2;3;4], [[2;3;4];[2;4;3];[3;2;4];[3;4;2];[4;2;3];[4;3;2]]); 
         ([1;2;3;4], 
          [[1;2;3;4];[1;2;4;3];[1;3;2;4];[1;3;4;2];[1;4;2;3];[1;4;3;2]; 
           [2;1;3;4];[2;1;4;3];[2;3;1;4];[2;3;4;1];[2;4;1;3];[2;4;3;1];
           [3;1;2;4];[3;1;4;2];[3;2;1;4];[3;2;4;1];[3;4;1;2];[3;4;2;1]; 
           [4;1;2;3];[4;1;3;2];[4;2;1;3];[4;2;3;1];[4;3;1;2];[4;3;2;1]])
      ]	
	  )

let testall () = 
  List.iter 
   (fun tester -> (line(); tester()))
   [testa; testb; testc; testd; teste; 
    testf; testg; testh; testi; testj]

