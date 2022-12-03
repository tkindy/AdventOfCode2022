(ns day03-test
  (:require day03
            [clojure.test :refer [deftest is]]))

(def example (day03/parse-input (slurp "examples/day03.txt")))

(deftest split-item-priority-sum
  (is (= (day03/split-item-priority-sum example)
         157)))

(deftest badge-priority-sum
  (is (= (day03/badge-priority-sum example)
         70)))
