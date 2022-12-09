(ns day08-test
  (:require day08
            [clojure.test :refer [deftest is]]))

(def example [[3 0 3 7 3]
              [2 5 5 1 2]
              [6 5 3 3 2]
              [3 3 5 4 9]
              [3 5 3 9 0]])

(deftest parse-input
  (is (= (day08/parse-input (slurp "examples/day08.txt"))
         example)))

(deftest build-tallest
  (is (= (day08/build-tallest example)
         [-1 -1 -1 -1 -1])))

(deftest visible?
  (is (day08/visible? {:x 3, :y 4, :height 9}
                      [6 5 5 7 9]
                      :x)))

(deftest update-tallest
  (is (= (day08/update-tallest [3 0 3 -1 -1]
                               {:x 3, :y 0, :height 7}
                               :x)
         [3 0 3 7 -1]))
  (is (= (day08/update-tallest [6 5 5 7 9]
                               {:x 3, :y 4, :height 9}
                               :x)
         [6 5 5 9 9])))

(deftest visible
  (is (= (day08/visible [#{} [-1 -1 -1 -1 -1]]
                        {:x 0, :y 0, :height 3}
                        :x)
         [#{{:x 0, :y 0, :height 3}}
          [3 -1 -1 -1 -1]])))

(deftest up-reduce
  (is (= (first (day08/up-reduce (fn [acc spot]
                                   (day08/visible acc spot :x))
                                 [#{} (day08/build-tallest example)]
                                 example))
         #{{:x 0, :y 0, :height 3}
           {:x 1, :y 0, :height 0}
           {:x 2, :y 0, :height 3}
           {:x 3, :y 0, :height 7}
           {:x 4, :y 0, :height 3}
           {:x 1, :y 1, :height 5}
           {:x 2, :y 1, :height 5}
           {:x 0, :y 2, :height 6}
           {:x 4, :y 3, :height 9}
           {:x 3, :y 4, :height 9}})))

(deftest num-visible
  (is (= (day08/num-visible example)
         21)))

(deftest max-scenic
  (is (= (day08/max-scenic example)
         8)))
