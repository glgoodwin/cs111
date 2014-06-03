let rec marbles m c = 
  if m = 1 then
    [[1]]
  else if c == 1 then
    [[m]]
  else 
    permutations([0]::marbles(m c-1))

and rec permutations xs = 
  match xs with 
    [] -> [[]]
  | (x::xs') -> insertions (x, permutations xs')

and insertions (x, yss) =
  match yss with 
    [] -> []
  | (ys::yss')-> (inserts (x,ys) @ (insertions (x,yss'))

and inserts (x, ys) = 
  match ys with 
    [] -> [[x]]
  | (y::ys') -> (x::ys) :: (mapcons (y, inserts (x,ys')))

and mapcons (y,zss) =
  match zss with 
    [] -> []
  | (zs::zss') -> (y::zs) :: (mapcons(y, zss'))	

;;

