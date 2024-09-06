;; 2.1 COMBINATORS

;; 2.1.1 Function combinators

; Arity
(define (get-arity proc)
    (or (hash-table-ref/default arity-table proc #f)
        (let ((a (procedure-arity proc)))
            (assert (eqv? (procedure-arity-min a)
                          (procedure-arity-max a)))
            (procedure-arity-min a))))
            
(define (spread-combine h f g)
    (let ((n (get-arity f)))
        (define (the-combination . args)
            (h (apply f (list-head args n))
               (apply g (list-tail args n))))
        the-combination))

(define (restrict-arity proc nargs)
    (hash-table-set! arity-table proc nargs)
    proc)

(define arity-table (make-key-weak-eqv-hash-table))
