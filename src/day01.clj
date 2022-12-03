(ns day01
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (->> (str/split input #"\n\n")
       (map str/split-lines)
       (map #(map parse-long %1))))

(defn read-input []
  (parse-input (slurp "inputs/day01.txt")))

(defn most-calories [elves]
  (->> elves
       (map #(apply + %1))
       (apply max)))

(defn -main []
  (let [elves (read-input)]
    (println "Part 1:" (most-calories elves))))
