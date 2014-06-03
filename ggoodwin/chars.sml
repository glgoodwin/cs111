fun first s = String.sub (s,0)

fun butFirst s = String.substring (s,1,(String.size (s) -1))

(* Recursive version *)
fun countChars1 (s,c) =
    if s = "" then 
	 0
    else if first s = c then 
	1 + (countChars1(butFirst(s),c))
    else
	countChars1(butFirst(s),c)

fun countChars2 (s,c) =
    if s = "" then
	0
    else
	(if first s = c then 1 else 0)
	     +
	(countChars2(butFirst(s),c))

(* Iterative versions *)
fun countChars3 (s,c)=
    countChars3Loop(s,c,0)

and countChars3Loop(s,c,count) = 
    if s = "" then 
	count
    else 
	countChars3Loop(butFirst(s),c, (if first s = c then 1 else 0) + count) 

	
fun range (lo, hi) = 
    if lo > hi then 
	[] 
    else
	lo :: (range(lo+1, hi))
	