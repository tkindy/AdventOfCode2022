(ns day09-test
  (:require day09
            [clojure.test :refer [deftest is]]))

(def example '([:right 4]
               [:up    4]
               [:left  3]
               [:down  1]
               [:right 4]
               [:down  1]
               [:left  5]
               [:right 2]))

(deftest parse-input
  (is (= (day09/parse-input (slurp "examples/day09.txt"))
         example)))

(deftest visited-count
  (is (= (day09/visited-count example)
         13)))
