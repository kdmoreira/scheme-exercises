(ns arity-test
  (:require [clojure.test :refer [deftest is testing]]
            [arity :refer :all]))

(defn f1 [& args] (conj args 'foo))
(defn f2 [x & args] (conj args x))
(defn f3 [x] (* x 2))
(defn f4 [x y] (* x y 2))

(deftest check-varargs-test
  (testing "returns a boolean for every arity"
    (is (= [false false true] (check-varargs #'str)))
    (is (= [true] (check-varargs #'list)))
    (is (= [false] (check-varargs #'inc)))
    (is (= [true] (check-varargs #'f1)))
    (is (= [true] (check-varargs #'f2)))
    (is (= [false] (check-varargs #'f3)))
    (is (= [false] (check-varargs #'f4)))))

(deftest args-counts-test
  (testing "returns an arg count for every arity"
    (is (= [0 1 3] (args-counts #'str)))
    (is (= [2] (args-counts #'list)))
    (is (= [1] (args-counts #'inc)))
    (is (= [2] (args-counts #'f1)))
    (is (= [3] (args-counts #'f2)))
    (is (= [1] (args-counts #'f3)))
    (is (= [2] (args-counts #'f4)))))

(deftest variadic?-test
  (testing "returns true if the function is variadic"
    (is (= true (variadic? #'str)))
    (is (= true (variadic? #'list)))
    (is (= false (variadic? #'inc)))
    (is (= true (variadic? #'f1)))
    (is (= true (variadic? #'f2)))
    (is (= false (variadic? #'f3)))
    (is (= false (variadic? #'f4)))))

(deftest single-arity-test
  (testing "returns true if the function has a single arity"
    (is (= false (single-arity #'str)))
    (is (= true (single-arity #'list)))
    (is (= true (single-arity #'inc)))
    (is (= true (single-arity #'f1)))
    (is (= true (single-arity #'f2)))
    (is (= true (single-arity #'f3)))
    (is (= true (single-arity #'f4)))))

(deftest max-args-test
  (testing "returns a function's max number of args,
            when it's not variadic"
    (is (= nil (max-args #'str)))
    (is (= nil (max-args #'list)))
    (is (= 1 (max-args #'inc)))
    (is (= nil (max-args #'f1)))
    (is (= nil (max-args #'f2)))
    (is (= 1 (max-args #'f3))
    (is (= 2 (max-args #'f4))))))

(deftest min-args-test
  (testing "returns a functions' min number of args"
    (is (= 0 (min-args #'str)))
    (is (= 0 (min-args #'list)))
    (is (= 1 (min-args #'inc)))
    (is (= 0 (min-args #'f1)))
    (is (= 1 (min-args #'f2)))
    (is (= 1 (min-args #'f3)))
    (is (= 2 (min-args #'f4)))))
