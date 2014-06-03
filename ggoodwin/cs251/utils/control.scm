(load "~/cs251/utils/env-alist.scm")
(load "~/cs251/utils/macros.scm")
(load "~/cs251/utils/list-ops.scm")

;; ------------------------------------------------------------------------
;; Debugging

(define *debug-control* #f)
(define (debug-control) (set! *debug-control* #t))
(define (undebug-control) (set! *debug-control* #f))

(define (debug-thunk thunk)
  (if *debug-control* (thunk) #f))

(define (println x) 
  (begin (display x) (display "\n")))

(define-macro-global (debug-println stuff)
  `(debug-thunk (lambda () (println ,stuff))))

;; ----------------------------------------------------------------------
;; SCHEME IMPLEMENTATION OF LABEL AND JUMP
;; (You need not understand this code)

(define (jump cont val)
  (if (continuation? cont)
      (cont val)
      (error "jump called on a non-continuation")))
  
(define-macro-global (label lab body)
  `(call-with-current-continuation 
     (lambda (,lab)
         ,body)))

;; Examples

#|
(+ 1 (label exit (* 2 (- 3 (/ 4 1)))))

(+ 1 (label exit (* 2 (- 3 (/ 4 (jump exit 5))))))

(+ 1 (label exit 
       (* 2 (- 3 (/ 4 (jump exit (+ 5 (jump exit 6))))))))

; Recall Scheme evaluates operand right-to-left.
(+ 1 (label exit1
       (* 2 (label exit2
              (- 3 (/ 4 (+ (jump exit2 5)
			   (jump exit1 6))))))))

; This simulates left-to-right.
(+ 1 (label exit1
       (* 2 (label exit2
              (- 3 (/ 4 (let* ((v1 (jump exit2 5))
			       (v2 (jump exit1 6)))
			  (+ v1 v2))))))))

|#

#|

(let ((g (lambda (x) x)))
  (letrec ((fact (lambda (n)
		   (if (= n 0)
		       (label base
			      (begin (set! g (lambda (y)
					       (begin
						 (set! g (lambda (z) z))
						 (jump base y))))
				     1))
		       (* n (fact (- n 1)))))))
    (+ (g 10)
       (+ (fact 3)
	  (+ (g 10)
	     (+ (fact 4)
		(g 10)))))))

|#

#|

;; Continuation-passing style
(define fact-rec-cps
  (lambda (n k) ; k is the explicit continuation
    (if (= n 0)
	(k 1)
	(fact-rec-cps (- n 1) (lambda (v) (k (* n v)))))))

(fact-rec-cps 3 (lambda (v) v))

(define product
  (lambda (outer-list)
    (letrec ((inner (lambda (lst k) ; k is the explicit continuation
		      (if (null? lst)
			  (k 1)
			  (if (= (car lst) 0)
			      0 ; return 0 directly, thus punting continuation
			      (inner (cdr lst)
				     (lambda (v) (k (* (car lst) v)))))))))
      (inner outer-list (lambda (v) v)))))

|#

;; ------------------------------------------------------------------------
;; RAISE, HANDLE, and TRAP

(define with-handler
  (lambda (tag make-handler try-thunk)
       (begin
	 (let ((old-env (get-handler-env)))
	   (begin
	     ;; Remember handler in dynamic environment
	     (set-handler-env! (env-bind tag 
					 (make-handler old-env)
					 (get-handler-env)))
	     ;; Evaluate try-thunk
	     (let ((try-value (try-thunk)))
	       ;; In normal case, pop handler
	       (begin
		 (set-handler-env! old-env) ; reinstate old handler env. 
		 try-value))))))) ;; Return value

(define raise-tag
  ;; TAG is a symbol indicating the type of exception.
  ;; VALUE is the "argument" of the exception.
  (lambda (tag value)
    (let ((handler (env-lookup tag (get-handler-env))))
      (if (unbound? handler)
	  (error (string-append "Unhandled exception " 
				(symbol->string tag)
				": "))
	  (handler value)))))

(define-macro-global (handle tag handler body)
  `(let ((*handler* ,handler) ; only evaluate once
	 (*thunk* (lambda () ,body))) ; put this here to avoid capturing *handler*
     (call-with-current-continuation 
      (lambda (handle-cont)
	(debug-println (list 'handle ',tag))
	(with-handler ',tag
		      (lambda (old-env)
			(lambda (value)
			  ;; Invoking HANDLE-CONT returns directly to the appropriate handle, 
			  ;; ignoring the current continuation.
			  (begin 
			    (debug-println (list 'handle-handler ',tag))
			    (set-handler-env! old-env) ; reinstall old-env
			    (handle-cont (*handler* value)))))
		      *thunk*)))))


(define-macro-global (trap tag handler body)
  `(let ((*handler* ,handler) ; only evaluate once
	 (*thunk* (lambda () ,body))) ; put this here to avoid capturing *handler*
     (debug-println (list 'trap ',tag))
     (with-handler ',tag
		  (lambda (old-env) 
		    (lambda (value) 
		      (debug-println (list 'trap-handler ',tag))
		      (*handler* value))) ; ignores old-env
		  *thunk*)))

(define-macro-global (raise tag value)
  `(raise-tag ',tag ,value))

;; ------------------------------------------------------------------------
;; The global handler environment

(define *handler-env* (env-empty))

(define set-handler-env! 
  (lambda (env) 
    (debug-println (list 'setting '*handler-env* 'from *handler-env* 'to env))
    (set! *handler-env* env)))

(define get-handler-env 
  (lambda () 
    (debug-println (list 'getting '*handler-env* *handler-env*))
    *handler-env*))

;; Just in case things get out of whack, call this. 
(define reset-control
  (lambda () 
    (set-handler-env! (env-empty))))


;; ------------------------------------------------------------------------
;; Test cases

(define exception-test
  (lambda () 
    (let ((raiser (lambda (x) 
		    (if (< x 0) 
			(raise negative x)
			(if (even? x)
			    (raise even x)
			    x)))))
      (let ((test (lambda () (+ (raiser 1) (+ (raiser -3) (raiser 4))))))
	(list 
              (handle negative (lambda (v) (- v)) 
	              (handle even (lambda (v) (* v v)) (test)))
              (handle negative (lambda (v) (- v)) 
	              (trap even (lambda (v) (* v v)) (test)))
              (trap negative (lambda (v) (- v)) 
	            (handle even (lambda (v) (* v v)) (test)))
	      (trap negative (lambda (v) (- v)) 
	            (trap even (lambda (v) (* v v)) (test)))

              ;; Why are these here? They just repeat work from above!
              (handle even (lambda (v) (* v v))
		      (handle negative (lambda (v) (- v)) (test)))
              (handle even (lambda (v) (* v v))
		      (trap negative (lambda (v) (- v)) (test)))
              (trap even (lambda (v) (* v v))
		    (handle negative (lambda (v) (- v)) (test)))
              (trap even (lambda (v) (* v v))
		    (trap negative (lambda (v) (- v)) (test)))
	      )))))


(define handle-test
  (lambda () 
    (handle foo (lambda (v) v) (+ 1 (raise foo 3)))))

(define error-test
  (lambda () 
    (handle foo (lambda (v) v) (+ 1 (error "blah")))))
