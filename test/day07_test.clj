(ns day07-test
  (:require day07
            [clojure.string :as str]
            [clojure.test :refer [deftest is]]
            [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test.check.clojure-test :refer [defspec]]))

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

(deftest split-execution
  (is (= (day07/split-execution ["$ cd /"])
         [{:command :cd
           :arg "/"
           :output []}
          []])))

(deftest split-executions
  (is (= (day07/split-executions ["$ cd /"])
         [{:command :cd
           :arg "/"
           :output []}])))

(def path-gen
  (gen/fmap (fn [v] (str "/" (str/join "/" v)))
            (gen/vector (gen/such-that not-empty gen/string-ascii)
                        0
                        10)))

(declare cd-up-retains-prefix)
(defspec cd-up-retains-prefix
  1000
  (prop/for-all [path path-gen]
                (let [up (day07/cd-up path)]
                  (str/starts-with? path up))))

(declare cd-up-makes-shorter)
(defspec cd-up-makes-shorter
  1000
  (prop/for-all [path path-gen]
                (let [up (day07/cd-up path)]
                  (or (= path "/")
                      (< (count up)
                         (count path))))))

(deftest change-directory
  (is (= (day07/change-directory {:path "/a/b/c"
                                  :root {:contents {}}}
                                 {:command :cd
                                  :arg ".."
                                  :output []})
         {:path "/a/b"
          :root {:contents {}}})))

(deftest parse-input
  (is (= (day07/parse-input (slurp "examples/day07.txt"))
         example)))

(deftest small-dirs-size
  (is (= (day07/small-dirs-size example)
         95437)))
