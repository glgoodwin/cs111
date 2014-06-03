(def (new-stack)
  (bind elts (empty)
    ;; Dispatch function representing stack instance
    (fun (msg)
      (cond
        ((str= msg "empty?") (empty? elts))
        ((str= msg "push")
	 (fun (val)
	   (seq (<- elts (prep val elts))
		val))) ; Return pushed val
	((str= msg "top")
	 (if (empty? elts)
	     (error "Attempt to top an empty stack!" elts)
	     (head elts)))
	((str= msg "pop")
	 (if (empty? elts)
	     (error "Attempt to pop an empty stack!" elts)
	     (bind result (head elts)
	       (seq (<- elts (tail elts))
		    result))))
	(else (error "Unknown stack message:" msg))
	))))