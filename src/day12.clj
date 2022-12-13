(ns day12)

(defn parse-input [input]
  (throw (RuntimeException. "Not yet implemented")))

(defn read-input []
  (parse-input (slurp "inputs/day12.txt")))

(defn shortest-path-distance [heightmap]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [heightmap (read-input)]
    (println "Part 1:" (shortest-path-distance heightmap))))
