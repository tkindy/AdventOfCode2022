(ns day02-test
  (:require day02
            [clojure.test :refer [deftest is]]))

(def example [[:rock     :paper]
              [:paper    :rock]
              [:scissors :scissors]])

(deftest expected-score
  (is (= (day02/expected-score example)
         15)))
