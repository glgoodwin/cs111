let rec sum_multiples_of_3_or_5 (m,n) = 
  (* replace this stub *)
  0 

let rec contains_multiple (n, xs) =
  (* replace this stub *)
  false

let rec all_contain_multiple (n, xss) = 
  match xss with 
    [] -> false
;;
    

let rec merge (ys,zs) =
  match (ys,zs) with 
    ([],[]) -> []
  | ([],zs) -> zs
  | (ys,[]) -> ys
  | (y::ys',z::zs') ->
    if (y<z) then
      y :: merge(ys',zs)
    else 
      z :: merge(ys,zs')
;;

let rec alts xs = 
(*doesn't work in case where there is an odd # of elements*)
  match xs with
    [] -> ([],[])
  |  x1::x2::xs' ->
     let (x,y) = 
     alts xs' in (x1::x, x2::y)
  | x::[] -> (x::[],[])
;;

let rec cartesian_product (xs,ys) =
  match (xs, ys) with
    ([],_) -> []
  | (_,[]) -> []
  | (x::xs',y::ys') ->
	(x,y) :: cartesian_product(xs,ys')
 
    

(* assume n a non-negative integer *)
let rec bits n = 
  (* replace this stub *)
  []

let rec inserts (x, ys) = 
  (* replace this stub *)
  []

(* assume xs has no dups *)
let rec permutations xs = 
  (* replace this stub *)
  []




