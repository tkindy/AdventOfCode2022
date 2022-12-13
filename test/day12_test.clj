(ns day12-test
  (:require day12
            [clojure.test :refer [deftest is]]))

(def example {:start {:x 0, :y 0}
              :end {:x 5, :y 2}
              :heightmap
              [[0  0  1 16 15 14 13 12]
               [0  1  2 17 24 23 23 11]
               [0  2  2 18 25 25 23 10]
               [0  2  2 19 20 21 22  9]
               [0  1  3  4  5  6  7  8]]})

(deftest parse-input
  (is (= (day12/parse-input (slurp "examples/day12.txt"))
         example)))

(deftest build-unvisited
  (is (= (day12/build-unvisited (:heightmap example))
         (set [{:x 0, :y 0} {:x 1, :y 0} {:x 2, :y 0} {:x 3, :y 0}
               {:x 4, :y 0} {:x 5, :y 0} {:x 6, :y 0} {:x 7, :y 0}
               {:x 0, :y 1} {:x 1, :y 1} {:x 2, :y 1} {:x 3, :y 1}
               {:x 4, :y 1} {:x 5, :y 1} {:x 6, :y 1} {:x 7, :y 1}
               {:x 0, :y 2} {:x 1, :y 2} {:x 2, :y 2} {:x 3, :y 2}
               {:x 4, :y 2} {:x 5, :y 2} {:x 6, :y 2} {:x 7, :y 2}
               {:x 0, :y 3} {:x 1, :y 3} {:x 2, :y 3} {:x 3, :y 3}
               {:x 4, :y 3} {:x 5, :y 3} {:x 6, :y 3} {:x 7, :y 3}
               {:x 0, :y 4} {:x 1, :y 4} {:x 2, :y 4} {:x 3, :y 4}
               {:x 4, :y 4} {:x 5, :y 4} {:x 6, :y 4} {:x 7, :y 4}]))))

(deftest shortest-path-distance
  (is (= (day12/shortest-path-distance example)
         31)))

(deftest any-start-shortest-distance
  (is (= (day12/any-start-shortest-distance example)
         29)))
