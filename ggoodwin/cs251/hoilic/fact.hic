(def (fact n) 
  (bind ans 1
    (seq (while (> n 0)
           (seq (<- ans (* ans n))
                (<- n (- n 1))))
          ans)))

