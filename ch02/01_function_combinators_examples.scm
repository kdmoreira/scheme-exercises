;; 2.1 COMBINATORS

;; 2.1.1 Function combinators

(define (compose1 f g)
    (lambda args
        (f (apply g args))))

((compose1 (lambda (x) (list 'foo x))
           (lambda (x) (list 'bar x)))
    'z)

(define (compose2 f g)
    (define (the-composition . args)
        (f (apply g args)))
    the-composition)

((compose2 (lambda (x) (list 'foo x))
          (lambda (x) (list 'bar x)))
    'z)

(define ((iterate n) f)
    (if (= n 0)
        identity
        (compose f ((iterate (-n 1)) f))))

(define (identity x) x)

; (((iterate 3) square) 5)

(define (parallel-combine h f g)
    (define (the-combination . args)
        (h (apply f args) (apply g args)))
    the-combination)

((parallel-combine list
                   (lambda (x y z) (list 'foo x y z))
                   (lambda (u v w) (list 'bar u v w)))
    'a 'b 'c)
