(ns day01-test
  (:require day01
            [clojure.test :refer [deftest is]]))

(deftest most-calories
  (is (= (day01/most-calories [[1000 2000 3000]
                               [4000]
                               [5000 6000]
                               [7000 8000 9000]
                               [10000]])
         24000)))
