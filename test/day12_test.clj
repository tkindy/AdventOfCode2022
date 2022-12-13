(ns day12-test
  (:require day12
            [clojure.test :refer [deftest is]]))

(def example [[0  0  1 16 15 14 13 12]
              [0  1  2 17 24 23 23 11]
              [0  2  2 18 25 25 23 10]
              [0  2  2 19 20 21 22  9]
              [0  1  3  4  5  6  7  8]])

(deftest parse-input
  (is (= (day12/parse-input (slurp "examples/day12.txt"))
         example)))

(deftest shortest-path-distance
  (is (= (day12/shortest-path-distance example)
         31)))
