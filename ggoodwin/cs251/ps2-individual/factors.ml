(* Assume n >= 2 *)  
let smallestPrimeFactor n = 
    let sq = int_of_float(sqrt(float_of_int(n)))
    in let rec factor (d, sq) = 
      if d <= sq then
	if n mod d == 0 then
	    d
	else factor(d+1, sq)
      else 
	n
    in factor(2, sq)
;;
(* Assume n >= 2 *)  
let rec majorMinorFactors n =
  let small = smallestPrimeFactor n
  in    
  if n == small then 
    (n,1)
  else 
    let (a,b) = majorMinorFactors(n/small)
    in 
    if a mod small == 0 then
      (a, b*small)
    else 
      (a*small, b)
  ;;

 
  
