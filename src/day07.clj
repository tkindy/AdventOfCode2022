(ns day07)

(defn parse-input [input]
  (throw (RuntimeException. "Not yet implemented")))

(defn read-input []
  (parse-input (slurp "inputs/day07.txt")))

(defn small-dirs-size [root]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [root (read-input)]
    (println "Part 1:" (small-dirs-size root))))
