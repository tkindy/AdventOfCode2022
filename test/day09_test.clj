(ns day09-test
  (:require day09
            [clojure.test :refer [deftest is are]]))

(def example '([:right 4]
               [:up    4]
               [:left  3]
               [:down  1]
               [:right 4]
               [:down  1]
               [:left  5]
               [:right 2]))

(deftest parse-input
  (is (= (day09/parse-input (slurp "examples/day09-1.txt"))
         example)))

(deftest move
  (are [acc motion expected] (= (day09/move acc motion)
                                expected)
    [{:head {:x 0, :y 0}, :tail {:x 0, :y 0}}
     #{{:x 0, :y 0}}]
    [:right 4]
    [{:head {:x 4, :y 0}, :tail {:x 3, :y 0}}
     #{{:x 0, :y 0}
       {:x 1, :y 0}
       {:x 2, :y 0}
       {:x 3, :y 0}}]))

(deftest visited-count
  (is (= (day09/visited-count example)
         13)))
