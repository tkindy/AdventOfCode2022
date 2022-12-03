(ns day02
  (:require [clojure.string :as str]))

(def them-map {"A" :rock
               "B" :paper
               "C" :scissors})
(def me-map {"X" :rock
             "Y" :paper
             "Z" :scissors})

(defn parse-input [input]
  (->> (str/split-lines input)
       (map (fn [line]
              (let [[them me] (str/split line #" ")]
                [(them-map them) (me-map me)])))))

(defn read-input []
  (parse-input (slurp "inputs/day02.txt")))

(defn expected-score [guide]
  0)

(defn -main []
  (let [guide (read-input)]
    (println "Part 1:" (expected-score guide))))
