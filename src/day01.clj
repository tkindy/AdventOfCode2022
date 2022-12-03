(ns day01)

(defn parse-input [input]
  [])

(defn read-input []
  (parse-input (slurp "inputs/day01.txt")))

(defn most-calories [elves]
  0)

(defn -main []
  (let [elves (read-input)]
    (println "Part 1:" (most-calories elves))))
