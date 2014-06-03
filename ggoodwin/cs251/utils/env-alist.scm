;;; ENV-ALIST.SCM
;;;
;;; An environment is an immutable table-like structure that associates 
;;; keys and values. Here, environments are implemented as 
;;; ASSOCIATION LISTS (lists of key/value bindings). The same key 
;;; (as determined by EQV?) may appear in more than one binding in the 
;;; association list, in which case the first binding encountered in the 
;;; list takes precedence. 

;;; BINDINGS

(define make-binding (lambda (key value) (list key value)))

(define binding-key (lambda (binding) (first binding)))
(define binding-value (lambda (binding) (second binding)))

(define same-key? 
  (lambda (binding1 binding2)
    (eqv? (binding-key binding1) 
          (binding-key binding2))))

(define unbound? 
  (lambda (val) 
    (eq? val unbound)))

(define unbound '(*unbound*)) ;; Special token indicating unbound variable

;;; CREATORS

(define env-make
  ;; Returns an environment with bindings of NAMES to the corresponding VALUES
  (lambda (keys values)
    (if (not (= (length keys) (length values)))
        (error "ENV-MAKE: lengths of keys and values don't match -- "
               (list 'keys= keys 'values= values)
               (map2 make-binding keys values))
	(zip keys values))))

(define bindings->env
  ;; Construct an environment from a list of name/value bindings
  (lambda (bindings)
    bindings))

(define env-empty
  ;; Return an empty environment; equivalent to (ENV-MAKE '() '())
  (lambda () 
    '()))

;;; ACCESSORS

(define env-keys 
  ;; Return a list of the keys that appear in the bindings of ENV.
  ;; The order of the names does not matter, but should be the same
  ;; for different calls to ENV-NAMES on the same environment. 
  ;; Since this representation allows for duplicates, need to remove
  ;; them. 
  (lambda (env) 
    ;; Use a version of remove-duplicates that keeps elements
    ;; in order of first occurrence
    (map binding-key (env->bindings env))))

(define env-values 
  ;; Return a list of the values that appear in the bindings of ENV.
  ;; The order should match the order of names returned by ENV-KEYS
  (lambda (env) 
    ;; Use a version of remove-duplicates that keeps elements
    ;; in order of first occurrence
    (map binding-value (env->bindings env))))

(define env->bindings
  ;; Return a list of the name/value bindings in ENV, without duplicates
  (lambda (env)
    (remove-duplicates same-key? env)))

(define env-lookup
  ;; Return the value of NAME in ENV, or an UNBOUND token if NAME not in ENV.
  (lambda (key env)
    (let ((probe (some (lambda (binding) (eqv? key (binding-key binding)))
                       env)))
      (if (none? probe)
	  unbound
	  (binding-value probe)))))


(define env-bound? 
  ;; Return #t if key is bound in the environment; otherwise return #f.
  (lambda (key env)
    (not (unbound? (env-lookup key env)))))

;;; TRANSFORMERS

(define env-bind
  (lambda (key value env)
    ;; Extend ENV with the association of KEY to VALUE.
    ;; Overrides any existing associations involving KEY 
    (cons (list key value) env)))

(define env-extend
  (lambda (keys values env)
    ;; Extend ENV with the associations of KEYS to the corresponding VALUES.
    ;; Overrides existing associations involving elements of KEYS.
    (if (not (= (length keys) (length values)))
      (error "ENV-EXTEND: lengths of keys and values don't match -- "
             (list 'keys= keys 'values= values))
      (foldr2 env-bind env keys values))))
            
(define env-keep
  ;; Return an environment containing only the bindings of ENV whose
  ;; keys are in the list keys
  (lambda (keys env)
    (filter (lambda (binding) (memv (binding-key binding) keys))
            env)))

(define env-remove
  ;; Return an environment containing all the bindings of ENV whose
  ;; names are not in the list NAMES
  (lambda (keys env)
    (filter (lambda (binding) (not (memv (binding-key binding) keys)))
            env)))

(define env-merge
  (lambda (env1 env2)
    ;; Merge the bindings in ENV1 and ENV2. When a name is bound in both, 
    ;; the binding in ENV1 takes precedence.
    (append env1 env2)))


;;; Auxiliary functions

(define remove-duplicates 
  ;; Returns a list containing the elements of LST with duplicates (as
  ;; determined by EQV?) removed. In this version, the elements are 
  ;; returned in order of their first occurrence.     
  (lambda (isEqual? lst) 
    (if (null? lst)
      '()
      (cons (car lst)
            (remove-duplicates isEqual? 
                               (remove-all isEqual? (car lst) (cdr lst)))))))

(define remove-all
  ;; Removes a list containing all the elements of LST except for 
  ;; occurrences of ELT. 
  (lambda (isEqual? elt lst)
     (filter (lambda (x) (not (isEqual? x elt))) lst)))
