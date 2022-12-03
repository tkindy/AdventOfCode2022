(ns day03
  (:require [clojure.string :as str]
            [clojure.set :as set]))

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

(defn find-split-item [[left right]]
  (-> (set/intersection (set left)
                        (set right))
      first))

(defn calc-priorities [start end base]
  (->> (range (int start) (inc (int end)))
       (map (fn [i] [(char i) (+ (- i (int start)) base)]))
       (into {})))

(def lower-priorities (calc-priorities \a \z 1))
(def upper-priorities (calc-priorities \A \Z 27))

(def priorities (merge lower-priorities upper-priorities))

(defn split-item-priority-sum [rucksacks]
  (->> rucksacks
       (map find-split-item)
       (map priorities)
       (apply +)))

(defn all-items [[left right]]
  (set/union (set left)
             (set right)))

(defn find-badge [group]
  (->> group
       (map all-items)
       (apply set/intersection)
       first))

(defn badge-priority-sum [rucksacks]
  (->> rucksacks
       (partition 3)
       (map find-badge)
       (map priorities)
       (apply +)))

(defn -main []
  (let [rucksacks (read-input)]
    (println "Part 1:" (split-item-priority-sum rucksacks))
    (println "Part 2:" (badge-priority-sum rucksacks))))
