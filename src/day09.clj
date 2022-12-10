(ns day09)

(defn parse-input [input]
  (throw (RuntimeException. "Not yet implemented")))

(defn read-input []
  (parse-input (slurp "inputs/day09.txt")))

(defn visited-count [motions]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [motions (read-input)]
    (println "Part 1:" (visited-count motions))))
