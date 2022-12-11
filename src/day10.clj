(ns day10)

(defn parse-input [input]
  (throw (RuntimeException. "Not yet implemented")))

(defn read-input []
  (parse-input (slurp "inputs/day10.txt")))

(defn interesting-signals-sum [program]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [program (read-input)]
    (println "Part 1:" (interesting-signals-sum program))))
