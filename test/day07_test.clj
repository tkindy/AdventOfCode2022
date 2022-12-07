(ns day07-test
  (:require day07
            [clojure.string :as str]
            [clojure.test :refer [deftest is are]]
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
           :output []}]))
  (is (= (day07/split-executions ["$ cd /"
                                  "$ ls"
                                  "dir a"
                                  "123 b"
                                  "$ cd a"
                                  "$ ls"
                                  "456 c"])
         [{:command :cd
           :arg "/"
           :output []}
          {:command :ls
           :arg nil
           :output ["dir a" "123 b"]}
          {:command :cd
           :arg "a"
           :output []}
          {:command :ls
           :arg nil
           :output ["456 c"]}])))

(deftest split-path
  (is (= (day07/split-path "/a/b/c")
         ["a" "b" "c"])))

(def path-gen
  (gen/fmap (fn [v] (str "/" (str/join "/" v)))
            (gen/vector (gen/such-that not-empty gen/string-alphanumeric)
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

(deftest capture-files
  (is (= (day07/capture-files {:path "/a"
                               :root {:contents {}}}
                              {:output ["123 b"]})
         {:path "/a"
          :root {:contents
                 {"a" {:contents
                       {"b" {:size 123}}}}}}))
  (is (= (day07/capture-files {:path "/a"
                               :root {:contents
                                      {"b" {:size 123}}}}
                              {:output ["456 c"]})
         {:path "/a"
          :root {:contents
                 {"a" {:contents
                       {"c" {:size 456}}}
                  "b" {:size 123}}}}))
  (is (= (day07/capture-files {:path "/a/b"
                               :root {:contents
                                      {"a" {:contents
                                            {"c" {:size 123}}}}}}
                              {:output ["456 d"]})
         {:path "/a/b"
          :root {:contents
                 {"a" {:contents
                       {"b" {:contents
                             {"d" {:size 456}}}
                        "c" {:size 123}}}}}})))

(deftest process-execution
  (are [state execution expected]
       (= (day07/process-execution state execution) expected)

    {:path "/"
     :root {:contents {}}}
    {:command :cd
     :arg "/"
     :output []}
    {:path "/"
     :root {:contents {}}}

    {:path "/"
     :root {:contents {}}}
    {:command :ls
     :arg nil
     :output ["dir a" "123 b"]}
    {:path "/"
     :root {:contents
            {"b" {:size 123}}}}

    {:path "/"
     :root {:contents
            {"b" {:size 123}}}}
    {:command :cd
     :arg "a"
     :output []}
    {:path "/a"
     :root {:contents
            {"b" {:size 123}}}}

    {:path "/a"
     :root {:contents
            {"b" {:size 123}}}}
    {:command :ls
     :arg nil
     :output ["456 c"]}
    {:path "/a"
     :root {:contents
            {"b" {:size 123}
             "a" {:contents
                  {"c" {:size 456}}}}}}))

(deftest parse-input
  (are [x y] (= (day07/parse-input x) y)
    "$ cd /\n$ ls\n123 a"
    {:contents {"a" {:size 123}}}

    "$ cd /\n$ ls\ndir a\n123 b\n$ cd a\n$ ls\n456 c"
    {:contents
     {"a"
      {:contents
       {"c" {:size 456}}}
      "b" {:size 123}}}

    (slurp "examples/day07.txt")
    example))

(deftest small-dirs-size
  (is (= (day07/small-dirs-size example)
         95437)))
