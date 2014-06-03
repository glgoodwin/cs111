let process xs = 
  let rec scan1 ys f = 
    match ys with 
      [] -> mapxs f
    | (y::ys') -> 
	if (y mod 2) = 0 then
	  scan2 ys' f 
	else 
	  let y' = y / 2 
	  in y' :: (scan1 ys' (fun a -> f (a + y')))
  and scan2 zs g = 
    match zs with 
      [] -> mapxs g
    | (z::zs') -> 
	if (z mod 2) = 0 then
	  scan1 zs' g
	else 
	  let z' = z / 2 
	  in z' :: (scan2 zs' (fun b -> g (b * z')))
  and mapxs q = 
    let rec mapq ws = 
      match ws with 
	[] -> []
      | (w::ws') -> (q w)::(mapq ws')
    in mapq xs
  in scan1 xs (fun x -> x) 
