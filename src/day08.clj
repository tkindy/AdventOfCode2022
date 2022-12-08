(ns day08
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (mapv (comp parse-long str) line))

(defn parse-input [input]
  (->> input
       str/trim
       str/split-lines
       (mapv parse-line)))

(defn read-input []
  (parse-input (slurp "inputs/day08.txt")))

(defn grid->seq [grid]
  (for [y (range (count grid))
        x (range (count (first grid)))]
    {:x x, :y y, :height (get-in grid [y x])}))

(defn on-edge? [[x y] grid]
  (or (= x 0)
      (= x (dec (count (first grid))))
      (= y 0)
      (= y (dec (count grid)))))

(defn check-spot [visible
                  {:keys [x y height] :as spot}
                  grid]
  (if (on-edge? [x y] grid)
    (conj visible [x y])
    visible))

(defn find-visible [grid]
  (->> grid
       grid->seq
       (reduce (fn [visible spot]
                 (check-spot visible spot grid))
               #{})))

(defn num-visible [grid]
  (count (find-visible grid)))

(defn -main []
  (let [grid (read-input)]
    (println "Part 1:" (num-visible grid))))
