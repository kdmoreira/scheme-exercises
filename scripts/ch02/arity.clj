#!/usr/bin/env bb
(ns arity)

(defn compose-1 [f g]
  (fn [& args]
    (f (apply g args))))

; Arities must be compatible
; OK
((compose-1 (fn [x] (list 'foo x))
            (fn [x] (list 'bar x))) 'z)
((compose-1 (fn [x] (list 'foo x))
            (fn [x y] (list 'bar x y))) 'z 'w)
; NOT OK - ArityException
#_((compose-1 (fn [x y] (list 'foo x y))
              (fn [x] (list 'bar x))) 'z)
#_((compose-1 (fn [x] (list 'foo x))
              (fn [x y] (list 'bar x y))) 'z)

; Arglists Examples
(:arglists (meta #'list)) ; => ([& items])
(:arglists (meta #'str)) ; => ([] [x] [x & ys])
(:arglists (meta #'inc)) ; => ([x])
(defn my-inc-fn [x] (inc x))
(:arglists (meta #'my-inc-fn)) ; => ([x])
(:arglists (meta (fn [x] (inc x)))) ; => nil
(:arglists (meta (defn f [x] (inc x)))) ; => ([x])

(defn check-varargs
  [f]
  (->> (meta f)
       :arglists
       (map #(contains? (set %) '&))))

(defn args-counts
  [f]
  (->> (meta f)
       :arglists
       (map count)))

(defn variadic? [f]
  (let [set (->> (check-varargs f)
                 set)]
    (contains? set true)))

(defn max-args [f]
  (when-not (variadic? f)
    (let [max (->> (args-counts f)
                   (apply max))]
      max)))

(defn single-arity
  [f]
  (= (count (args-counts f)) 1))

(defn min-args [f]
  (if (and (variadic? f)
           (single-arity f))
    (condp = (first (args-counts f))
      2 0
      (- (first (args-counts f)) 2))
    (let [min (->> (args-counts f)
                   (apply min))]
      min)))
