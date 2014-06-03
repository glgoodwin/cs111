;; Put your definition of Hoilic cell operations here.

(def (cell contents) (sym cell-body))

(def (^ c) (c #t))

(def (:= c v) (sym set-body))
