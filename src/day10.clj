(ns day10
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (let [[opcode arg] (str/split line #" ")
        instruction {:opcode (keyword opcode)}]
    (if arg
      (assoc instruction :arg (parse-long arg))
      instruction)))

(defn parse-input [input]
  (->> input
       str/trim
       str/split-lines
       (map parse-line)))

(defn read-input []
  (parse-input (slurp "inputs/day10.txt")))

(defn interesting-signals-sum [program]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [program (read-input)]
    (println "Part 1:" (interesting-signals-sum program))))
