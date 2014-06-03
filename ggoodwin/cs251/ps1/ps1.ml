
(* CS251 Spring'13 Problem Set 1 *)

(* Replace the following stub for the function f. *)
<<<<<<< ps1.ml
let rec f n= 
    if n <= 2 then
	n
    else
      n + f(n/2) + f(n/3)
;;
=======
let rec f n =
<<<<<<< ps1.ml
  if n <= 2 then
    n
  else
    n + f(n/2) + f(n/3);;
>>>>>>> 1.2

=======
  0
>>>>>>> 1.4
(* Replace the following stub for the function g. *)
<<<<<<< ps1.ml
let rec g n =  
    if n <= 2 then
      (n,1)
    else
      let rec sum d = 
	if d <= 2 then
	  (n + f(n/2) + f(n/3),1)
	else 
	  let (r,c) = sum (d-1)
	      in (r, c+d)
    in sum (n/2);;


=======
let rec g n =
<<<<<<< ps1.ml
  let rec fAndCount x =
    if x <= 2 then
      (x, 1)
    else
      let ((x1, s1), (x2, s2)) = (fAndCount(x/2), fAndCount(x/3))
        in (x + x1 + x2, 1 + s1 + s2)
    in fAndCount(n);;
>>>>>>> 1.2
=======
  (0,0)

>>>>>>> 1.4
