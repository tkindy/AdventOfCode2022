(ns day04
  (:require [clojure.string :as str]))

(defn parse-assignment [assignment]
  (let [[start end] (str/split assignment "-")]
    [(parse-long start) (inc (parse-long end))]))

(defn parse-pair [pair]
  (let [[p1 p2] (str/split pair ",")]
    [(parse-assignment p1) (parse-assignment p2)]))

(defn parse-input [input]
  (map parse-pair (str/split-lines (str/trim input))))

(defn read-input []
  (parse-input (slurp "inputs/day04.txt")))

(defn subset-count [pairs]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [pairs (read-input)]
    (println "Part 1:" (subset-count pairs))))
