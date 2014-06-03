(def my-point
  (bind num-points 0 ; class variable
    (fun (cmsg) ; class message
     (cond
       ((str= cmsg "count") num-points) ; acts like a class method
       ((str= cmsg "new") ; acts like a constructor method
        (fun (ix iy)
          (bindpar ((x ix) (y iy)) ; instance variables
            (seq (<- num-points (+ num-points 1)) ; count points
                 (bindrec ; create and return instance dispatcher function. 
                   ((this ; gives the name "this" to instance = instance method dispatcher
                     (fun (imsg) ; instance message
                       (cond 
                         ;; the following are instance methods 
                         ((str= imsg "get-x") x)
                         ((str= imsg "get-y") y)
                         ((str= imsg "set-x") (fun (new-x) (<- x new-x)))
                         ((str= imsg "set-y") (fun (new-y) (<- y new-y)))
                         ((str= imsg "translate")
                          (fun (dx dy) (seq ((this "set-x") (+ x dx))
                                            ((this "set-y") (+ y dy)))))
                         ((str= imsg "toString")
                          (str+ "<"
                                (str+ (toString x)
                                      (str+ ","
                                            (str+ (toString y)
                                                  ">")))))
                         (else "error: unknown instance message" imsg)))))
                   this))))) ; return instance as the result of "new"
       (else "error: unknown class message" cmsg)
       ))))

(def test-my-point
  (fun ()
    (bindseq ((p1 ((my-point "new") 3 4))
              (p2 ((my-point "new") 5 6)))
      (seq
        ((p1 "set-x") (p2 "get-y"))
        ((p2 "set-y") (my-point "count"))
        ((p1 "translate") 1 2)
        (+ (* 1000 (p1 "get-x"))
           (+ (* 100 (p1 "get-y"))
              (+ (* 10 (p2 "get-x"))
                 ((p2 "get-y")))))))))
