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

(def lower-priorities (->> (range (int \a) (inc (int \z)))
                           (map (fn [i] [(char i) (+ (- i (int \a)) 1)]))
                           (into {})))
(def upper-priorities (->> (range (int \A) (inc (int \Z)))
                           (map (fn [i] [(char i) (+ (- i (int \A)) 27)]))
                           (into {})))

(def priorities (merge lower-priorities upper-priorities))

(defn split-item-priority-sum [rucksacks]
  (->> rucksacks
       (map find-split-item)
       (map priorities)
       (apply +)))

(defn -main []
  (let [rucksacks (read-input)]
    (println "Part 1:" (split-item-priority-sum rucksacks))))
