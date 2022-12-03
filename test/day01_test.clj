(ns day01-test
  (:require day01
            [clojure.test :refer [deftest is]]))

(def example [[1000 2000 3000]
              [4000]
              [5000 6000]
              [7000 8000 9000]
              [10000]])

(deftest most-calories
  (is (= (day01/most-calories example)
         24000)))

(deftest top-3-calories
  (is (= (day01/top-3-calories example)
         45000)))
