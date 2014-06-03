(* Represent a set as a binary search tree (BST). 
   This is a binary tree in which for every node Node(l,v,r),
     all values in l are < v and all values in r are > v. *)

module BSTSet : SET = struct

  open Bintree 
  open ListUtils

  type 'a set = 'a bintree

  let empty = Leaf

  let singleton x = Node(Leaf, x, Leaf)

  let rec insert x t = 
    match t with 
      Leaf -> singleton x
    | Node(l,v,r) -> if x = v then t
                     else if x < v then Node(insert x l, v, r)
                     else Node(l, v, insert x r)

  (* Assume called on non-empty tree *)
  let rec deleteMax t = 
    match t with 
      Leaf -> raise (Failure "shouldn't happen") 
    | Node(l,v,Leaf) -> (v, l)
    | Node(l,v,r) -> let (max, r') = deleteMax r in 
                       (max, Node(l,v,r'))

  let rec delete x s = 
    match s with 
      Leaf -> Leaf
    | Node(l,v,Leaf) when v = x -> l
    | Node(Leaf,v,r) when v = x -> r
    | Node(l,v,r) -> if x = v then 
                       let (pred, l') = deleteMax l in Node(l', pred, r)
                     else if x < v then Node(delete x l, v, r)
                     else Node(l, v, delete x r)

  let isEmpty s = 
    match s with 
      Leaf -> true
    | _ -> false 

  let rec size s = 
    match s with 
      Leaf -> 0
    | Node(l,v,r) -> (size l) + 1 + (size r)

  let rec member x s = 
    match s with 
      Leaf -> false
    | Node(l,v,r) -> if x = v then true
                     else if x < v then member x l
                     else member x r

  let rec union s1 s2 = ListUtils.foldr insert s2 (postlist s1) 
  (* In union and below, postlist helps to preserve balance more than 
     inlist or prelist in a foldr.  For a foldl, prelist would be best. *)


  let rec difference s1 s2 = ListUtils.foldr delete s1 (postlist s2) 

  let rec fromList xs = ListUtils.foldr insert empty xs

  let rec toString eltToString s = Bintree.toString eltToString s

  let toSexp eltToSexp s = Bintree.toSexp eltToSexp s

  let fromSexp eltFromSexp s = Bintree.fromSexp eltFromSexp s

  (* Q: Why is the following intersection function so incredibly slow? *)
  (* [lyn, 2/28/08] Answer: because the tester was making S1 be the
     first 2/3 of a file and S2 be the second 2/3 of the file. 
     The intersection of S1 and S2 is the middle third.
     The sets are build from back forward by foldr, so the middle
     third is all the elements near the leaves of S2. Regardless
     of whether these bottom tree elements are enumerated in 
     prefix, infix, or postfix, they are pretty much enumerated
     in order, which gives very skewed results for the intersection. 

     This is really an artifact of the particular test used. 
     However, we can mitigate this problem by deleting elements
     from the S1 tree rather than building a new tree from scratch. *)

  (*
  (* Version that builds a new tree from scratch *)
  let rec intersection s1 s2 = 
    ListUtils.foldr (fun x s -> if member x s1 then insert x s
                                else s)
                    empty
                    (postlist s2)
  *)

  (* Version that deletes S1 elements that are not in S2 *)
  let rec intersection s1 s2 = 
    ListUtils.foldr (fun x s -> if not (member x s2) then delete x s
                                else s)
                    s1
                    (inlist s1) (* inlist will grab many leafy elements first *)
 
  (*
  (* Testing version of intersection *)
  (* Why is the following intersection function so incredibly slow? *)
  let rec intersection s1 s2 = 
    ListUtils.foldr (fun x s -> if member x s1 then 
                                  (*
                                  let _ = StringUtils.println((Sexp.sexpToString
								 (toSexp (fun e -> Sexp.Sym "X") s))) in 
				     *)
                                  let _ = StringUtils.println((string_of_int (Bintree.height s))) in 
                                  insert x s
                                else s)
                    empty
                    (postlist s2)
   *)

  let rec toList s = inlist s

  let rec toStringAsList eltToString s = StringUtils.listToString eltToString (toList s)

  let rec toStringAsTree eltToString t = (* For debugging purposes *)
    "HEIGHT = " ^ (string_of_int (Bintree.height t)) ^ "\n"
    ^ (foldr (^) "" (map (fun s -> s ^ "\n") (toStringList eltToString t)))
    
  and toStringList eltToString t = 
    let indent str = "  " ^ str in 
    match t with 
      Leaf -> []
    | Node(l,v,r) -> (map indent (toStringList eltToString l)) 
	             @ [eltToString v] @ 
	             (map indent (toStringList eltToString r))

  let toString s = toStringAsList s

end
