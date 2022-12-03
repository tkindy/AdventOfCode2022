(ns day02-test
  (:require day02
            [clojure.test :refer [deftest is]]))

(def example [[:rock     :paper]
              [:paper    :rock]
              [:scissors :scissors]])
(def fixed-example [[:rock     :draw]
                    [:paper    :lose]
                    [:scissors :win]])

(deftest parse-input
  (is (= (day02/parse-input (slurp "examples/day02.txt"))
         example)))

(deftest expected-score
  (is (= (day02/expected-score example)
         15)))

(deftest fix-guide
  (is (= (day02/fix-guide example)
         fixed-example)))

(deftest expected-score-real
  (is (= (day02/expected-score-real fixed-example)
         12)))
