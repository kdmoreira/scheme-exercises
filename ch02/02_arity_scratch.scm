(define (list-fn x)
    (list x))

(list-fn "foo")

(define (sum-fn x y)
    (+ x y))

(sum-fn 1 2)

(define (restrict-arity proc nargs)
    (hash-table-set! arity-table proc nargs)
    proc)

(define arity-table (make-key-weak-eqv-hash-table))

(restrict-arity list-fn 1)

(define (get-arity proc)
    (or (hash-table-ref/default arity-table proc #f)
        (let ((a (procedure-arity proc)))
            (assert (eqv? (procedure-arity-min a)
                          (procedure-arity-max a)))
            (procedure-arity-min a))))

(get-arity list-fn)
(get-arity sum-fn)

(procedure-arity list-fn)
(procedure-arity sum-fn)
(procedure-arity-min (procedure-arity list-fn))
(procedure-arity-min (procedure-arity sum-fn))
(procedure-arity-max (procedure-arity list-fn))
(procedure-arity-max (procedure-arity sum-fn))
