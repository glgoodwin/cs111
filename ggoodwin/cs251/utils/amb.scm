;;; The ambiguous operator, based on a hack JAR showed me:

;;; As JAR notes, this is a particular example of a general
;;; approach when one already has ENV, STORE, and CONT -
;;; to add a new conceptual element to semantics, can
;;; just make it a distinguished element of STORE.  Thus, here
;;; we'd really like to have a SUCCESS and FAILURE continuation;
;;; so we make SUCCESS the regular CONT, and add FAILURE to the
;;; STORE as the distinguished name FAIL.

; FAIL represents the backtracking stack.
; Calling FAIL on no args pops the stack and does the operation that used to 
; be on the top.

;;; FOR AN ALTERNATE INTERPRETATION, SEE CODE AT END.

(load "../util/macros.scm") ; For define-macro-global

(define *top-level-fail* (lambda () (error "Empty failure stack")))
(define fail *top-level-fail*)
(define (reset-fail!) (set! fail *top-level-fail*))

(define-macro-global (amb . exps)
  (if (null? exps)
      '(fail)
      `(%AMB ,@(map (lambda (exp) 
		      `(LAMBDA () ,exp))
		    exps))))

(define-macro-global (cut exp)
  `(%CUT (LAMBDA () ,exp)))

(define-macro-global (all-values exp)
  `(%ALL-VALUES (LAMBDA () ,exp)))
	    

; (Same as McAllester's EITHER)
(define (%amb first-thunk second-thunk)
  ((call-with-current-continuation 
    (lambda (cont)			; Name the current continuation ...
      (let ((old-fail fail))
	(set! fail			; ... and PUSH it onto the fail stack ...
	     (lambda ()			; ... remembering that when we backtrack (by calling (FAIL)), we'll ...
	       (set! fail old-fail)	; ... POP the fail stack ... 
	       (cont second-thunk))))	; ... and call the just-popped TOP (= CONT) on the second choice to AMB.
      first-thunk))))                   ; But right now, we'll just return the first choice to AMB.

; For limiting backtracking
; (same as McAllester's ONE-VALUE)
(define (%cut thunk)
  (let ((old-fail fail))		; Remember fail stack at entry to CUT
    (let ((value (thunk)))		; Evaluate THUNK
      (begin
	(set! fail old-fail)		; Forget any backtracking introduced by evaluating THUNK
	value))))			; and return the value of THUNK.

; Return a list of all values associated with a particular thunk
; without backtracking outside the call to ALL-VALUES.
; (McAllester's ALL-VALUES)

(define (%all-values thunk)
  (call-with-current-continuation
   (lambda (return)
     (let ((old-fail fail)
	   (answer-list '()))
       (set! fail (lambda () 
		    (set! fail old-fail)
		    (return (reverse answer-list))))
       (let ((next-value (thunk)))
	 (set! answer-list (cons next-value answer-list)) ; Accumulate this value ...
	 (fail)				; ... and generate next one
	 )))))

#|

; Examples

; Results depend on order of evaluation of subexpressions
(+ (amb 1 2)
   (amb 30 40))

; Yield elements of a list
(define (member-of l)
  (if (null? l)
      (fail)
      (amb (car l) (member-of (cdr l)))))

(all-values (+ (member-of '(1 2 3))
	       (member-of '(10 20 30))))

; Compare the following two expressions
(all-values
 (let ((foo (member-of '(1 2))))
   (+ foo (member-of '(10 20)))))

(all-values
 (let ((foo (member-of '(1 2))))
   (cut (+ foo (member-of '(10 20))))))

(all-values
 (let ((foo (cut (member-of '(1 2)))))
   (+ foo (member-of '(10 20)))))

; AMB to generate infinite stream of integers
(define (ints-from n)
  (amb n (ints-from (+ n 1))))

(define (ints-between lo hi)
  (if (> lo hi)
      (fail)
      (amb lo (ints-between (+ lo 1) hi))))

(define (pyth a b)
  (let ((ans (sqrt (+ (square a) (square b))))) 
    (if (and (< a b) (integer? ans))
	(list a b ans)
        (fail))))

(define (square x) (* x x))

(all-values
 (pyth (ints-between 1 20) (ints-between 1 20)))


|#


#|
;;; Here's an alternate version, due to Dave Espinosa, with a
;;; different semantics. Rather than viewing AMB as a bactracking
;;; construct, he views it rather like a nondeterministic or.
;;; So we only try the second alternative if we know when 
;;; producing the first one that it fails.

(define *top-level-fail*
  (lambda () (error "Empty failure stack")))

(define fail *top-level-fail*)

(define (reset-fail!) 
  (set! fail *top-level-fail*)
  unspecific)

;;; Note this AMB has a different semantics than the one defined above.
(define (amb first-thunk second-thunk)
  ((call-with-current-continuation 
    (lambda (cont)
      (lambda ()
	(fluid-let ((fail (lambda () (cont second-thunk))))
	  (first-thunk)))))))

|#




