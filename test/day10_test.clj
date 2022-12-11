(ns day10-test
  (:require day10
            [clojure.test :refer [deftest is are]]))

(def example (day10/parse-input (slurp "examples/day10.txt")))

(deftest parse-input
  (is (= (day10/parse-input "noop\naddx 3\naddx -5\n")
         [{:instruction :noop}
          {:instruction :addx, :arg 3}
          {:instruction :addx, :arg -5}])))

(deftest interesting-signals-sum
  (is (= (day10/interesting-signals-sum example)
         13140)))
