(ns day07-test
  (:require day07
            [clojure.test :refer [deftest is]]))

(def example-parsed (day07/parse-input (slurp "examples/day07.txt")))
(def example {:contents
              {"a" {:contents
                    {"e" {:contents
                          {"i" {:size 584}}}
                     "f" {:size 29116}
                     "g" {:size 2557}
                     "h.lst" {:size 62596}}}
               "b.txt" {:size 14848514}
               "c.dat" {:size 8504156}
               "d" {:contents
                    {"j" {:size 4060174}
                     "d.log" {:size 8033020}
                     "d.ext" {:size 5626152}
                     "k" {:size 7214296}}}}})

(deftest parse-input
  (is (= example-parsed example)))

(deftest small-dirs-size
  (is (= (day07/small-dirs-size example)
         95437)))
