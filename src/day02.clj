(ns day02)

(defn parse-input [input]
  [])

(defn read-input []
  (parse-input (slurp "inputs/day02.txt")))

(defn expected-score [guide]
  0)

(defn -main []
  (let [guide (read-input)]
    (println "Part 1:" (expected-score guide))))
