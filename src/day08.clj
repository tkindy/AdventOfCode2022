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

(defn num-visible [grid]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [grid (read-input)]
    (println "Part 1:" (num-visible grid))))
