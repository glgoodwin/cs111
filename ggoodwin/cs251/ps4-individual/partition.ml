(* Gabe Goodwin ps4 individual*)

open FunUtils
open ListUtils

let eqMod n x y = (((max x y) - (min x y)) mod n) = 0

let pairSumEq (a,b) (c,d) = (a+b) = (c+d)

let sameLen s1 s2 = (String.length s1) = (String.length s2)

let sameFirstChar s1 s2 = 
  if (s1 = "") || (s2 = "") then 
    s1 = s2
  else
    (String.get s1 0) = (String.get s2 0)

(* Part a of PS4 Individual Problem *)

let partition eq xs = 
  map (fun y -> filter (fun x  -> eq y x = true) xs) xs

(* Part b of PS4 Individual Problem *)
let isPartitioning eq yss = 
  for_all(foldr' (fun  x xs' ans -> ans && (eq x (List.hd xs') = true)) (yss = [[]]) yss) yss


  
