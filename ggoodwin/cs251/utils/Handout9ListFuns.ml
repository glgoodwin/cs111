(* Handout9ListFuns.ml *)

let rec process ps =
  match ps with 
    [(c,d);(e,f)] -> [(d,f);(c,e)]
  | p1::p2::p3::ps' -> p3::(process(p1::p2::ps'))
  | _ -> ps

let condswap xs = 
  match xs with 
    x1::x2::x3::xs' when x1 = x3 -> x2 :: x1 :: x3 :: xs'
  | _ -> xs;;

(* Version of condswap that names a subpattern. *)
let condswap' xs = 
  match xs with 
    x1::x2::((x3::_) as xs'') when x1 = x3 -> x2 :: x1 :: xs''
  | _ -> xs;;

let rec sum ns = 
  match ns with 
    [] -> 0
  | n::ns' -> n + sum ns'

let rec range (lo, hi) = 
  if (lo > hi) then
    []
  else
    lo :: range (lo+1, hi)

let rec squares ns = 
  match ns with 
    [] -> []
  | n::ns' -> (n*n) :: squares ns'

let rec evens ns = 
  match ns with 
    [] -> []
  | n::ns' -> 
      if (n mod 2) == 0 then
	n :: evens ns'
      else
	evens ns'

let sumOfSquaredEvensBetween (lo,hi) =
  sum(squares(evens(range(lo,hi))))

let rec isMember (x,ys) =
  match ys with
    [] -> false
  | y::ys' -> (x = y) || isMember(x,ys')

let rec remove (x,ys) =
  match ys with
    [] -> []
  | y::ys' -> 
      if x = y then remove (x,ys') else y :: remove (x,ys')

let rec removeDups xs =
  match xs with
    [] -> [] 
  | x::xs' -> x::remove(x,removeDups(xs'))

let rec removeDups2 xs = 
  match xs with
    [] -> [] 
  | x::xs' -> x::removeDups2(remove(x,xs'))

let rec removeDups3 xs = 
  match xs with
    [] -> [] 
  | x::xs' -> 
      if isMember(x,xs') then
	removeDups3 xs'
      else
	x::removeDups3 xs'

let rec isSorted xs = 
  match xs with
    [] -> true
  | [x] -> true
  | x1::((x2::_) as xs') -> x1 <= x2 && isSorted xs'

let rec flatten xss = 
  match xss with
    [] -> [] 
  | xs::xss' -> xs @ (flatten xss')

(* quadratic time list reversal *)
let rec reverse xs = 
  match xs with
    [] -> [] 
  | x::xs' -> (reverse xs') @ [x]

(* linear time list reversal *)
let rec reverse2 xs = 
 let rec revloop (olds,news) = 
   match olds with 
     [] -> news
   | (old::olds') -> revloop (olds',old::news)
 in revloop (xs,[])

let rec zip (xs,ys) = 
  match (xs,ys) with
    ([],_) -> []
  | (_,[]) -> []
  | (x::xs',y::ys') -> (x,y)::zip(xs',ys')

let rec unzip xys =
  match xys with
    [] -> ([],[])
  | (x,y)::xys' -> 
      let (xs,ys) = unzip xys'
      in (x::xs, y::ys)

let rec mapcons (x,yss) =
  match yss with
    [] -> []
  | ys::yss' -> (x::ys)::mapcons(x,yss')

let rec subsets xs = 
  match xs with
    [] -> [[]]
  | x::xs' -> 
      let subs = subsets xs'
      in subs @ (mapcons(x,subs))

(* converts a binary representation of a number 
   (expressed as a list of 0/1 bits) to a decimal *)
let rec decimal bits = 
  (* Use Horner's method to turn bits into number *)
  let rec loop bs n = 
    match bs with 
      [] -> n
    | (b::bs') -> loop bs' (2*n + b)
  in loop bits 0

(* A recursive version of decimal that uses last and butlast bits *)
let rec decimal2 bits =
  match bits with 
    [] -> 0
  | _ -> 2*(decimal2(butlast(bits))) + last(bits)
and last xs = List.hd (reverse xs)
and butlast xs = reverse (List.tl(reverse xs))


(* A recursive version of decimal that avoids last and butlast by
   operating on a reversal of the bits. *)
let rec decimal3 bits = rdecimal (reverse bits)
and rdecimal rbits = 
  match rbits with 
    [] -> 0
  | b::bs -> b + 2*(rdecimal(bs))







      






                 




