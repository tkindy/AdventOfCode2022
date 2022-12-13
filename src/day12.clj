(ns day12
  (:require [clojure.string :as str]))

(defn parse-height [c]
  (case c
    \S 0
    \E 25
    (- (int c) (int \a))))

(defn parse-line [line]
  (mapv parse-height line))

(defn parse-input [input]
  (->> input
       str/trim
       str/split-lines
       (mapv parse-line)))

(defn read-input []
  (parse-input (slurp "inputs/day12.txt")))

(defn shortest-path-distance [heightmap]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [heightmap (read-input)]
    (println "Part 1:" (shortest-path-distance heightmap))))
