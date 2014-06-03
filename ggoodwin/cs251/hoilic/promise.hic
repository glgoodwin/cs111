(def (make-promise thunk)
  (bindpar ((flag #f)
	    (memo #f))
    (fun ()
      (if flag
	  memo
	  (seq (<- flag #t)
	       (<- memo (thunk))
	       memo)))))

(def (force promise) (promise))
