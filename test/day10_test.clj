(ns day10-test
  (:require day10
            [clojure.test :refer [deftest is are]]
            [clojure.string :as str]))

(def small-example (day10/parse-input "noop\naddx 3\naddx -5\n"))
(def example (day10/parse-input (slurp "examples/day10.txt")))

(deftest parse-input
  (is (= small-example
         [{:opcode :noop}
          {:opcode :addx, :arg 3}
          {:opcode :addx, :arg -5}])))

(deftest evaluate
  (is (= (day10/evaluate small-example)
         [1 1 1 4 4 -1]))
  (is (= (take 20 (day10/evaluate example))
         [1 1 16 16 5 5 11 11 8 8 13 13
          12 12 4 4 17 17 21 21])))


(deftest interesting-signals
  (is (= (day10/interesting-signals example)
         [420 1140 1800 2940 2880 3960])))

(deftest interesting-signals-sum
  (is (= (day10/interesting-signals-sum example)
         13140)))

(deftest render
  (is (= (day10/render example)
         (str/trim (slurp "examples/day10-rendered.txt")))))
