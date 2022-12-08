(ns day08
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (mapv (comp parse-long str) line))

(defn parse-input [input]
  (->> input
       str/trim
       str/split-lines
       (mapv parse-line)))

(defn read-input []
  (parse-input (slurp "inputs/day08.txt")))

(defn grid->seq [grid]
  (for [y (range (count grid))
        x (range (count (first grid)))]
    [[x y] (get-in grid [y x])]))

(defn num-visible [grid]
  (->> grid
       grid->seq
       (filter (fn [tree] (visible? tree grid)))
       count))

(defn -main []
  (let [grid (read-input)]
    (println "Part 1:" (num-visible grid))))
