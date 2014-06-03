module type BINTREE = sig 
  type 'a bintree = Leaf | Node of 'a bintree * 'a * 'a bintree
  val int_tree : int bintree
  val string_tree : string bintree
  val map : ('a -> 'b) -> 'a bintree -> 'b bintree 
  val fold : ('a -> 'b -> 'a -> 'a) -> 'a -> 'b bintree -> 'a 
  val nodes : 'a bintree -> int 
  val height : 'a bintree -> int 
  val sum : int bintree -> int 
  val prelist : 'a bintree -> 'a list
  val inlist : 'a bintree -> 'a list 
  val postlist : 'a bintree -> 'a list 
  val toString : ('a -> string) -> 'a bintree -> string 
  val toVerboseSexp : ('a -> Sexp.sexp) -> 'a bintree -> Sexp.sexp
  val toCompactSexp : ('a -> Sexp.sexp) -> 'a bintree -> Sexp.sexp
  val toDenseSexp : ('a -> Sexp.sexp) -> 'a bintree -> Sexp.sexp
  val toSexp : ('a -> Sexp.sexp) -> 'a bintree -> Sexp.sexp
  val fromVerboseSexp : (Sexp.sexp -> 'a) -> Sexp.sexp -> 'a bintree 
  val fromCompactSexp : (Sexp.sexp -> 'a) -> Sexp.sexp -> 'a bintree 
  val fromDenseSexp : (Sexp.sexp -> 'a) -> Sexp.sexp -> 'a bintree 
  val fromSexp : (Sexp.sexp -> 'a) -> Sexp.sexp -> 'a bintree 
end

module Bintree : BINTREE = struct 

  open Sexp

  (* Binary tree datatype abstracted over type of node value *)
  type 'a bintree = 
      Leaf
    | Node of 'a bintree * 'a * 'a bintree (* left subtree, value, right subtree *)

  (* Sample tree of integers *)
  let int_tree = 
      Node(Node(Leaf, 2, Leaf), 
           4, 
           Node(Node(Leaf, 1, Node(Leaf, 5, Leaf)),
                6,
                Node(Leaf, 3, Leaf)));;

  (* Sample tree of strings *) 
  let string_tree = 
      Node(Node(Leaf, "like", Leaf), 
           "green", 
           Node(Node(Leaf, "eggs", Leaf),
                "and",
                Node(Leaf, "ham", Leaf)));;

  (* Map a function over every value in a tree *)
  let rec map f tr = 
    match tr with 
      Leaf -> Leaf
    | Node(l,v,r) -> Node(map f l, f v, map f r)

  (* Divide/conquer/glue on trees *)
  let rec fold glue lfval tr = 
    match tr with 
      Leaf -> lfval 
    | Node(l,v,r) -> glue (fold glue lfval l) v (fold glue lfval r)

  let nodes tr = fold (fun l v r -> 1 + l + r) 0 tr

  let height tr = fold (fun l v r -> 1 + (max l r)) 0 tr 

  let sum tr = fold (fun l v r -> l + v + r) 0 tr

  (* (* Simple but inefficient versions *)
  let prelist tr = fold (fun l v r -> v :: l @ r) [] tr

  let inlist tr = fold (fun l v r -> l @ [v] @ r) [] tr

  let postlist tr = fold (fun l v r -> l @ r @ [v]) [] tr
  *)

  let prelist tr = 
    let rec preWalk t ans = 
      match t with 
        Leaf -> ans
      |	Node(l,v,r) -> v::(preWalk l (preWalk r ans))
    in preWalk tr [] 

  let inlist tr = 
    let rec inWalk t ans = 
      match t with 
        Leaf -> ans
      |	Node(l,v,r) -> inWalk l (v:: (inWalk r ans))
    in inWalk tr [] 

  let postlist tr = 
    let rec postWalk t ans = 
      match t with 
        Leaf -> ans
      |	Node(l,v,r) -> postWalk l (postWalk r (v:: ans))
    in postWalk tr [] 

  let toString valToString tr = 
    fold (fun l v r -> "(" ^ l ^ " " ^ (valToString v) ^ " " ^ r ^ ")") "*" tr

  (* Example of verbose notation: 
     (Node (Node (Leaf) 2 (Leaf))
           4
           (Node (Node (Leaf) 1 (Node (Leaf) 5 (Leaf)))
                 6
                 (Node (Leaf) 3 (Leaf)))) *)
   let rec toVerboseSexp eltToSexp tr = 
    match tr with 
     Leaf -> Seq [Sym "Leaf"]
   | Node(l,v,r) -> Seq [Sym "Node"; 
			  toVerboseSexp eltToSexp l; 
			  eltToSexp v;
			  toVerboseSexp eltToSexp r]

  (* Example of compact notation: "((* 2 *) 4 ((* 1 (* 5 *)) 6 (* 3 *)))" *)
  let rec toCompactSexp eltToSexp tr = 
    match tr with 
      Leaf -> Sym "*"
    | Node(l,v,r) -> Seq [toCompactSexp eltToSexp l; 
			  eltToSexp v;
			  toCompactSexp eltToSexp r]

  (* Example of dense notation: ((2) 4 ((1 (5)) 6 (3))) *)
  let rec toDenseSexp eltToSexp s = 
    match s with 
      Leaf -> Seq([]) (* Special case for tree with just a leaf *)
    | Node(Leaf,v,Leaf) -> Seq([eltToSexp v]) 
    | Node(l,v,Leaf) -> Seq([toDenseSexp eltToSexp l; eltToSexp v]) 
    | Node(Leaf,v,r) -> Seq([eltToSexp v; toDenseSexp eltToSexp r]) 
    | Node(l,v,r) -> Seq([toDenseSexp eltToSexp l; eltToSexp v; toDenseSexp eltToSexp r])

  let rec fromVerboseSexp eltFromSexp sexp = 
    match sexp with 
      Seq [Sym "Leaf"] -> Leaf
    | Seq([ls; x; rs]) -> Node(fromVerboseSexp eltFromSexp ls, 
                               eltFromSexp x,
                               fromVerboseSexp eltFromSexp rs)
    | _ -> raise (Failure ("Bintree.fromVerboseSexp -- can't handle sexp:\n"
			   ^ (Sexp.sexpToString sexp)))

  let rec fromCompactSexp eltFromSexp sexp = 
    match sexp with 
      Sym "*" -> Leaf
    | Seq([ls; x; rs]) -> Node(fromCompactSexp eltFromSexp ls, 
                               eltFromSexp x,
                               fromCompactSexp eltFromSexp rs)
    | _ -> raise (Failure ("Bintree.fromCompactSexp -- can't handle sexp:\n"
			   ^ (Sexp.sexpToString sexp)))

  let rec fromDenseSexp eltFromSexp sexp = 
    match sexp with 
      Sexp.Seq([]) -> Leaf
    | Sexp.Seq([x]) -> Node(Leaf, eltFromSexp x, Leaf)
    | Sexp.Seq([(Sexp.Seq _) as ls; x]) -> 
        Node(fromDenseSexp eltFromSexp ls, eltFromSexp x, Leaf)
    | Sexp.Seq([x; (Sexp.Seq _) as rs]) -> 
        Node(Leaf, eltFromSexp x, fromDenseSexp eltFromSexp rs)
    | Sexp.Seq([ls; x; rs]) -> Node(fromDenseSexp eltFromSexp ls, 
                                    eltFromSexp x,
                                    fromDenseSexp eltFromSexp rs)
    | _ -> raise (Failure ("Bintree.fromDenseSexp -- can't handle sexp:\n"
			   ^ (Sexp.sexpToString sexp)))

  let toSexp = toCompactSexp (* Treat compact notation as the default *)
  let fromSexp = fromCompactSexp (* Treat compact notation as the default *)

end 

	
  
