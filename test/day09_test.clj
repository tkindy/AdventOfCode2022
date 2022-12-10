(ns day09-test
  (:require day09
            [clojure.test :refer [deftest is]]))

(def example (day09/parse-input (slurp "examples/day09.txt")))

(deftest visited-count
  (is (= (day09/visited-count example)
         13)))
