(ns day09
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (let [[dir count] (str/split line #" ")
        dir (case dir
              "U" :up
              "D" :down
              "L" :left
              "R" :right)
        count (parse-long count)]
    [dir count]))

(defn parse-input [input]
  (->> input
       str/trim
       str/split-lines
       (map parse-line)))

(defn read-input []
  (parse-input (slurp "inputs/day09.txt")))

(defn visited-count [motions]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [motions (read-input)]
    (println "Part 1:" (visited-count motions))))
