(ns day08-test
  (:require day08
            [clojure.test :refer [deftest is]]))

(def example [[3 0 3 7 3]
              [2 5 5 1 2]
              [6 5 3 3 2]
              [3 3 5 4 9]
              [3 5 3 9 0]])

(deftest parse-input
  (is (= (day08/parse-input (slurp "examples/day08.txt"))
         example)))

(deftest num-visible
  (is (= (day08/num-visible example)
         21)))
