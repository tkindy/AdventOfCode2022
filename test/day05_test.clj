(ns day05-test
  (:require day05
            [clojure.test :refer [deftest is]]))

(def example (day05/parse-input (slurp "examples/day05.txt")))

(deftest end-tops
  (is (= (day05/end-tops example)
         "CMZ")))
