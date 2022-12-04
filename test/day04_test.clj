(ns day04-test
  (:require day04
            [clojure.test :refer [deftest is]]))

(def example (day04/parse-input (slurp "examples/day04.txt")))

(deftest subset-count
  (is (= (day04/subset-count example)
         2)))
