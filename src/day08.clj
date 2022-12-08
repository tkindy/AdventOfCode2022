(ns day08)

(defn parse-input [input]
  (throw (RuntimeException. "Not yet implemented")))

(defn read-input []
  (parse-input (slurp "inputs/day08.txt")))

(defn num-visible [grid]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [grid (read-input)]
    (println "Part 1:" (num-visible grid))))
