(bindrec ((map (fun (f xs)
		 (if (empty? xs)
		     xs
		     (prep (f (head xs)) (map f (tail xs)))))))
  (bindpar ((a 1)
	    (b 1))
    (bindpar ((a 20)
	      (z a))
      (bind add! (abs x (seq (<- z (+ x z)) z)) ; Swapped X and Z in +
	(bindrec ((s (prep b t))
		  (t (map add! s)))
	  (+ (head t) (head (tail t))))))))
