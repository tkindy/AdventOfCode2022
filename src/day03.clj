(ns day03
  (:require [clojure.string :as str]))

(defn parse-compartment [contents]
  (apply list contents))

(defn parse-rucksack [line]
  (let [len (count line)]
    [(parse-compartment (subs line 0 (/ len 2)))
     (parse-compartment (subs line (/ len 2)))]))

(defn parse-input [input]
  (->> (str/split-lines input)
       (map parse-rucksack)))

(defn read-input []
  (parse-input (slurp "inputs/day03.txt")))

(defn split-item-priority-sum [rucksacks]
  0)

(defn -main []
  (let [rucksacks (read-input)]
    (println "Part 1:" (split-item-priority-sum rucksacks))))
