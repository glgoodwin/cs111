(def (pair x y) (list x y))
(def (fst p) (nth 1 p)) ;; Assume NTH is a primop
(def (snd p) (nth 2 p)) ;; Assume NTH is a primop

(def (gen next done? seed)
  (if (done? seed)
      (empty)
      (prep seed (gen next done? (next seed)))))

(def (map f xs)
  (if (empty? xs)
      (empty)
      (prep (f (head xs))
	    (map f (tail xs)))))

(def (map2 f xs ys)
  (if (|| (empty? xs) (empty? ys))
      (empty)
      (prep (f (head xs) (head ys))
	    (map2 f (tail xs) (tail ys)))))

(def (filter pred xs)
  (if (empty? xs)
      (empty)
      (if (pred (head xs))
	  (prep (head xs) (filter pred (tail xs)))
          (filter pred (tail xs)))))

(def (foldr binop init xs)
  (if (empty? xs)
      init
      (binop (head xs)
	     (foldr binop init (tail xs)))))

(def (foldl binop init xs)
  (if (empty? xs)
      init
      (foldl binop (binop (head xs) init) (tail xs))))

(def (zip xs ys)
  (map2 pair xs ys))

(def (unzip pairs)
  (pair (map fst pairs)
        (map snd pairs)))

