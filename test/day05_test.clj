(ns day05-test
  (:require day05
            [clojure.test :refer [deftest is]]))

(def example {:start {1 '(\N \Z)
                      2 '(\D \C \M)
                      3 '(\P)}
              :procedure '({:count 1, :from 2, :to 1}
                           {:count 3, :from 1, :to 3}
                           {:count 2, :from 2, :to 1}
                           {:count 1, :from 1, :to 2})})

(deftest parse-spot
  (is (= (day05/parse-spot 0 "    ")
         {}))
  (is (= (day05/parse-spot 1 "[D]")
         {2 [\D]})))

(deftest parse-level
  (is (= (day05/parse-level "    [D]")
         {2 [\D]})))

(deftest parse-step
  (is (= (day05/parse-step "move 1 from 2 to 1")
         {:count 1, :from 2, :to 1})))

(deftest parse-input
  (is (= (day05/parse-input (slurp "examples/day05.txt"))
         example)))

(deftest end-tops
  (is (= (day05/end-tops example)
         "CMZ")))

(deftest upgraded-end-tops
  (is (= (day05/upgraded-end-tops example)
         "MCD")))
