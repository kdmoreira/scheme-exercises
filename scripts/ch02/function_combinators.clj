#!/usr/bin/env bb
(ns function-combinators)

; SIMPLE COMBINE
; -- apply --
(apply + [1 2 3]) ; => 6
(+ 1 2 3) ; => 6
(apply :a [{:a 1}{:b 2}]) ; => 1
(apply :b [{:a 1}{:b 2}]) ; => {:b 2}

; == BOOK
; It receives 2 functions
; and returns a new function that
; applies the first function to the
; result of applying the second function
(defn compose-1 [f g]
  (fn [& args]
    (f (apply g args))))

((compose-1 (fn [x] (list 'foo x)) 
            (fn [x] (list 'bar x))) 'z)
; => (foo (bar z))

(defn compose-2 [f g]
  (defn the-composition [& args] 
    (f (apply g args)))
  the-composition)

((compose-2 (fn [x] (list 'foo x))
            (fn [x] (list 'bar x))) 'z)
; => (foo (bar z))

; ====

; Other example
((compose-1 (fn [x] (* x 2))
            (fn [x] (inc x))) 2) ; => 6
; Increments 2 to 3 and then multiplies by 2

; Experimentation without apply
(defn compose-no-apply [f g]
  (fn [& args]
    (f (g args))))

((compose-no-apply (fn [x] (list 'foo x))
                   (fn [x] (list 'bar x))) 'z)
; => (foo (bar (z)))

; PARALLEL COMBINE
(defn parallel-combine [h f g]
  (fn [& args]
    (h (apply f args) (apply g args))))

; Function h needs to have an arity of at least 2
; because it receives the results of f and g
((parallel-combine list
                   (fn [x y z] (list 'foo x y z))
                   (fn [u v w] (list 'bar u v w)))
 'a 'b 'c)
; => ((foo a b c) (bar a b c))
