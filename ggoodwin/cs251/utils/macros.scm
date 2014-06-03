;;;----------------------------------------------------------------------------
;;; Macrology magic 

; The local version
(define-syntax define-syntax-global
  (macro (name expander)
    `(begin
       (define-syntax ,name ,expander)
       (syntax-table-define system-global-syntax-table ',name ,expander))))

; The exported version
(syntax-table-define system-global-syntax-table 
  'define-syntax-global
  (macro (name expander)
    `(begin
       (define-syntax ,name ,expander)
       (syntax-table-define system-global-syntax-table ',name ,expander))))

(define-syntax-global define-macro-global
  (macro (pattern . body)
    `(DEFINE-SYNTAX-GLOBAL ,(car pattern)
       (MACRO ,(cdr pattern) ,@body))))
