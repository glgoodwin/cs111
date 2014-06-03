;;;-------------------------------------------------------------------	
;; Simple list ops

;; Put an element at the end of a list 
(define snoc 
  (lambda (lst elt)
    (append lst (list elt))))

;; A duple is a two element list
(define duple (lambda (a b) (list a b)))

;; Returns the last element of a non-empty list
(define last
  (lambda (lst)
    (cond ((null? lst)
	   (error "LAST: called on empty list"))
	  ((null? (cdr lst)) (car lst))
	  (else (last (cdr lst))))))

;; Returns all but the last element of a non-empty list
(define but-last
  (lambda (lst)
    (cond ((null? lst)
	   (error "BUT-LAST: called on empty list"))
	  ((null? (cdr lst)) '())
	  (else (cons (car lst) (but-last (cdr lst)))))))

;;;-------------------------------------------------------------------	
;; Classic higher-order list operations

;; ID is the identity function. This is the identity element
;; for COMPOSE. 
(define id (lambda (x) x))

;; Given F and G, COMPOSE returns the composition of F and G, 
;; that is, the function that performs F on the result of G.
(define compose 
  (lambda (f g)
    (lambda (x)
      (f (g x)))))

;; GENERATE returns a list containing the elements
;; SEED, (NEXT SEED), (NEXT (NEXT SEED)) ...
;; until reaching and element for which DONE? is true.
;; This terminating element is *not* included in the list. 
(define generate
  (lambda (seed next done?)
    (if (done? seed)
	'()
	(cons seed (generate (next seed) next done?)))))

;; GENERATE-KEEP_LAST returns a list containing the elements
;; SEED, (NEXT SEED), (NEXT (NEXT SEED)) ...
;; until reaching and element for which DONE? is true.
;; This terminating element is the last element of the list. 
(define generate-keep-last 
  (lambda (seed next done?)
    (if (done? seed)
	(list seed) 
	(cons seed (generate (next seed) next done?)))))

;; FROM-TO returns a list of all the numbers from LO to HI.
(define from-to
  (lambda (lo hi)
    (generate lo
	      (lambda (x) (+ x 1))
	      (lambda (y) (> y hi)))))

;; MAP returns a list of the same length as LST in which
;; every element is the result of applying F to the
;; corresponding element of LST. 
(define map 
  (lambda (f lst)
    (if (null? lst)
	'()
	(cons (f (car lst))
	      (map f (cdr lst))))))

;; FILTER returns a list containing, in order, all the 
;; elements of LST satisfying the predicate PRED.
(define filter
  (lambda (pred lst)
    (if (null? lst)
	'()
	(if (pred (car lst))
	    (cons (car lst) (filter pred (cdr lst)))
	    (filter pred (cdr lst))))))

;; Given a list (CONS e1 (CONS e2 ...  (CONS en '()))), 
;; FOLDR returns the value (OP e1 (OP e2 ... (OP en INIT)))
(define foldr
  (lambda (op init lst)
    (if (null? lst)
	init
	(op (car lst) (foldr op init (cdr lst))))))

;; Given a list (CONS e1 (CONS e2 ...  (CONS en '()))), 
;; FOLDL returns the value (OP en ... (OP e2 (OP e1 INIT)))
(define foldl
  (lambda (op init lst)
    (if (null? lst)
	init
	(foldl op (op (car lst) init) (cdr lst)))))

;; FORALL? returns true if the predicate PRED is satisfied by 
;; all elements in LST, and false otherwise.
(define forall?
  (lambda (pred lst)
    (if (null? lst)
        #t
        (and (pred (car lst))
             (forall? pred (cdr lst))))))

;; EXISTS? returns true if the predicate PRED is satisfied by 
;; at least one element in LST, and false otherwise.
(define exists? 
  (lambda (pred lst)
    (if (null? lst)
        #f
        (or (pred (car lst))
            (exists? pred (cdr lst))))))

;; SOME returns the first element in LST that satisfies the
;; predicate PRED. Returns the distinguished NONE token
;; if there is no such element. 
(define some
  (lambda (pred lst)
    (if (null? lst)
        none
        (if (pred (car lst))
            (car lst)
            (some pred (cdr lst))))))

;; NONE is a disintinguished token indicating "no value" 
(define none '(*none*))

;; NONE? returns true for the NONE token and false for 
;; every other value. 
(define none? (lambda (x) (eq? x none)))

;; A duple is a two-element list. 
(define duple (lambda (a b) (list a b)))

;; ZIP returns a list of duples whose first elements range 
;; in order over LST1 and whose second elements range in order
;; over LST2. The length of the resuling list is the length
;; of the shorter of the two input lists. 
(define zip
  (lambda (lst1 lst2)
    (if (or (null? lst1) (null? lst2))
        '()
        (cons (duple (car lst1) (car lst2))
              (zip (cdr lst1) (cdr lst2))))))

;;----------------------------------------------------------------------
;; Using ZIP, it's easy to make versions of MAP, FOLDR, FOLDL, 
;; FORALL?, EXISTS?, and SOME that work on two lists. 

(define one-list-op->two-list-op
  (lambda (one-list-op)
    (lambda (binop lst1 lst2)
      (one-list-op (lambda (duple) (binop (first duple) (second duple)))
		   (zip lst1 lst2)))))

(define one-list-fold->two-list-fold
  (lambda (one-list-fold)
    (lambda (ternop init lst1 lst2)
      (one-list-fold (lambda (duple result) (ternop (first duple) (second duple) result))
		     init
		     (zip lst1 lst2)))))

;; MAP2 returns a list of the same length as the shorter of
;; LST1 and LST2 in which every element is the result of applying 
;; binary function F to the corresponding elements of LST1 and LST2.
(define map2 (one-list-op->two-list-op map))

; (define map2 
;   (lambda (f lst1 lst2)
;     (map (lambda (duple) (f (first duple) (second duple)))
;          (zip lst1 lst2))))


;; Given LST1 = (CONS a1 (CONS a2 ...  (CONS am '())))
;;    and LST = (CONS b1 (CONS b2 ...  (CONS bn '())))
;; FOLDR2 returns the value (OP a1 b1 (OP a2 b2 ... (OP ak bk INIT))), 
;; where k is min(m,n). 
(define foldr2 (one-list-fold->two-list-fold foldr))

; (define foldr2 
;   (lambda (ternop init lst1 lst2)
;     (foldr (lambda (duple result) 
;              (ternop (first duple) (second duple) result))
;            init             
;            (zip lst1 lst2))))

;; Given LST1 = (CONS a1 (CONS a2 ...  (CONS am '())))
;;    and LST = (CONS b1 (CONS b2 ...  (CONS bn '())))
;; FOLDR2 returns the value (OP ak bk ... (OP a2 b2 (OP a1 b1 INIT)))
;; where k is min(m,n). 
(define foldl2 (one-list-fold->two-list-fold foldl))

; (define foldl2
;   (lambda (ternop init lst1 lst2)
;     (foldl (lambda (duple result) 
;              (ternop (first duple) (second duple) result))
;            init             
;            (zip lst1 lst2))))

;; FORALL2? returns true if the binary predicate PRED is satisfied by 
;; all pairs of corresponding elements in LST1 and LST2, and false otherwise.
(define forall2? (one-list-op->two-list-op forall?))

; (define forall2?
;   (lambda (binpred lst1 lst2)
;     (forall? (lambda (duple) 
; 	       (binpred (first duple) (second duple)))
; 	     (zip lst1 lst2))))

;; EXISTS2? returns true if the binary predicate PRED is satisfied by at least 
;; one pair of corresponding elements in LST1 and LST2, and false otherwise.
(define exists2? (one-list-op->two-list-op exists?))

; (define exists2? 
;   (lambda (binpred lst1 lst2)
;     (exists? (lambda (duple) 
; 	       (binpred (first duple) (second duple)))
; 	     (zip lst1 lst2))))

;; SOME2 returns the first duple of corresponding elements from LST1 and LST2
;; that satisfy the binary predicate PRED. Returns the distinguished NONE token
;; if there is no such duple of elements. 
(define some2 (one-list-op->two-list-op some))

; (define some
;   (lambda (binpred lst1 lst2)
;     (some (lambda (duple) 
; 	    (binpred (first duple) (second duple)))
; 	  (zip lst1 lst2))))


;;----------------------------------------------------------------------
;; Using ZIP3, it's easy to make versions of MAP, FOLDR, FOLDL, 
;; FORALL?, EXISTS?, and SOME that work on three lists. 

;; A triple is a three-element list. 
(define triple (lambda (a b c) (list a b c)))

(define zip3
  (lambda (lst1 lst2 lst3)
    (if (or (null? lst1) (null? lst2) (null? lst3))
        '()
        (cons (triple (car lst1) (car lst2) (car lst3))
              (zip3 (cdr lst1) (cdr lst2) (cdr lst3))))))

(define one-list-op->three-list-op
  (lambda (one-list-op)
    (lambda (ternop lst1 lst2 lst3)
      (one-list-op (lambda (triple) (ternop (first triple) 
					    (second triple)
					    (third triple)))
		   (zip3 lst1 lst2 lst3)))))

(define one-list-fold->three-list-fold
  (lambda (one-list-fold)
    (lambda (quadop init lst1 lst2 lst3)
      (one-list-fold (lambda (triple result) 
		       (quadop (first triple) 
			       (second triple) 
			       (third triple) 
			       result))
		     init
		     (zip3 lst1 lst2 lst3)))))

(define map3 (one-list-op->three-list-op map))
(define foldr3 (one-list-fold->three-list-fold foldr))
(define foldl3 (one-list-fold->three-list-fold foldl))
(define forall3? (one-list-op->three-list-op forall?))
(define exists3? (one-list-op->three-list-op exists?))
(define some3 (one-list-op->three-list-op some))

