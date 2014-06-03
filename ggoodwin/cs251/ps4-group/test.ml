let rec splithalf xs n i = 
    match xs with
      [] -> ([],[])
    | x::xs' -> 
	let (a,b) = splithalf xs' n (i+1) in 
	if i<n then
	  (x::a,b)
	else
	  (a,x::b)
