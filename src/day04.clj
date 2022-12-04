(ns day04)

(defn parse-input [input]
  (throw (RuntimeException. "Not yet implemented")))

(defn read-input []
  (parse-input (slurp "inputs/day04.txt")))

(defn subset-count [pairs]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [pairs (read-input)]
    (println "Part 1:" (subset-count pairs))))
