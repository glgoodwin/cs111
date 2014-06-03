;; The expression from PS9 Problem 2

(bind a 1 
  (bind inc! (fun () (seq (<- a (+ a 1)) a))
    (bind f (fun (y z)
              (seq (<- y (+ y 3))
                   (+ a (* z z))))
      (f a (inc!)))))
