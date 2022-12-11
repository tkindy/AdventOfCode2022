(ns day11-test
  (:require day11
            [clojure.test :refer [deftest is are]]))

(def example
  [{:items [79 98]
    :operation (fn [old] (* old 19))
    :test {:divisor 23
           true 2
           false 3}}
   {:items [54 65 75 74]
    :operation (fn [old] (+ old 6))
    :test {:divisor 19
           true 2
           false 0}}
   {:items [79 60 97]
    :operation (fn [old] (* old old))
    :test {:divisor 13
           true 1
           false 3}}
   {:items [74]
    :operation (fn [old] (+ old 3))
    :test {:divisor 17
           true 0
           false 1}}])

(deftest parse-operation
  (is (= ((day11/parse-operation "Operation: new = old + 3\n") 4)
         7)))

(defn strip-operations [monkeys]
  (mapv (fn [monkey]
          (dissoc monkey :operation))
        monkeys))

(deftest parse-input
  (is (= (strip-operations (day11/parse-input (slurp "examples/day11.txt")))
         (strip-operations example))))

(deftest inspected
  (are [rounds expected] (= (day11/inspected example rounds identity)
                            expected)
    1 [2 4 3 6]
    20 [99 97 8 103]
    1000 [5204 4792 199 5192]
    2000 [10419 9577 392 10391]
    3000 [15638 14358 587 15593]
    4000 [20858 19138 780 20797]
    5000 [26075 23921 974 26000]
    6000 [31294 28702 1165 31204]))

(deftest small-monkey-business
  (is (= (day11/small-monkey-business example)
         10605)))

(deftest big-monkey-business
  (is (= (day11/big-monkey-business example)
         2713310158)))
