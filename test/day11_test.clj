(ns day11-test
  (:require day11
            [clojure.test :refer [deftest is]]))

(def example
  [{:items [79 98]
    :operation '(* old 19)
    :test {:divisor 23
           true 2
           false 3}}
   {:items [54 65 75 74]
    :operation '(+ old 6)
    :test {:divisor 19
           true 2
           false 0}}
   {:items [79 60 97]
    :operation '(* old old)
    :test {:divisor 13
           true 1
           false 3}}
   {:items [74]
    :operation '(+ old 3)
    :test {:divisor 17
           true 0
           false 1}}])

(deftest parse-input
  (is (= (day11/parse-input (slurp "examples/day11.txt"))
         example)))

(deftest small-monkey-business
  (is (= (day11/small-monkey-business example)
         10605)))

(deftest big-monkey-business
  (is (= (day11/big-monkey-business example)
         2713310158)))
