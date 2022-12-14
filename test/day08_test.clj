(ns day08-test
  (:require day08
            [clojure.test :refer [deftest is are]]))

(def example [[3 0 3 7 3]
              [2 5 5 1 2]
              [6 5 3 3 2]
              [3 3 5 4 9]
              [3 5 3 9 0]])

(deftest parse-input
  (is (= (day08/parse-input (slurp "examples/day08.txt"))
         example)))

(deftest num-visible
  (is (= (day08/num-visible example)
         21)))

(deftest extract-row
  (is (= (day08/extract-row example 2 0 3)
         [6 5 3]))
  (is (= (day08/extract-row example 2 4 5)
         [2])))

(deftest extract-column
  (is (= (day08/extract-column example 3 0 2)
         [7 1]))
  (is (= (day08/extract-column example 3 3 5)
         [4 9])))

(deftest line-up
  (are [spot expected] (= (day08/line-up spot example)
                          expected)
    {:x 2, :y 1, :height 5} [3]))

(deftest num-trees-up
  (are [spot expected] (= (day08/num-trees-up spot example)
                          expected)
    {:x 2, :y 1, :height 5} 1))

(deftest line-down
  (are [spot expected] (= (day08/line-down spot example)
                          expected)
    {:x 2, :y 1, :height 5} [3 5 3]))

(deftest num-trees-down
  (are [spot expected] (= (day08/num-trees-down spot example)
                          expected)
    {:x 2, :y 1, :height 5} 2))

(deftest line-left
  (are [spot expected] (= (day08/line-left spot example)
                          expected)
    {:x 2, :y 1, :height 5} [5 2]))

(deftest num-trees-left
  (are [spot expected] (= (day08/num-trees-left spot example)
                          expected)
    {:x 2, :y 1, :height 5} 1))

(deftest line-right
  (are [spot expected] (= (day08/line-right spot example)
                          expected)
    {:x 2, :y 1, :height 5} [1 2]))

(deftest num-trees-right
  (are [spot expected] (= (day08/num-trees-right spot example)
                          expected)
    {:x 2, :y 1, :height 5} 2))

(deftest scenic
  (are [spot expected] (= (day08/scenic spot example)
                          expected)
    {:x 0, :y 0, :height 3} 0
    {:x 2, :y 1, :height 5} 4))

(deftest max-scenic
  (is (= (day08/max-scenic example)
         8)))
