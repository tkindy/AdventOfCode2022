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

(defn top-3-calories [elves]
  (->> elves
       (map #(apply + %1))
       (sort (comparator >))
       (take 3)
       (apply +)))

(defn -main []
  (let [elves (read-input)]
    (println "Part 1:" (most-calories elves))
    (println "Part 2:" (top-3-calories elves))))
