fun step (a,b) = (a+b, a*b)

fun stepUntil ((a,b), limit) =
  if a >= limit then
    (a,b)
  else
    stepUntil (step(a,b), limit)


