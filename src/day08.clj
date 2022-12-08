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

(defn on-edge? [{:keys [x y]} grid]
  (or (= x 0)
      (= x (dec (count (first grid))))
      (= y 0)
      (= y (dec (count grid)))))

(defn update-tallest [tallest {:keys [height] :as spot} keyfn]
  (update tallest
          (keyfn spot)
          (fn [cur] (max height (or cur -1)))))

(defn tallest-row [tallest spot]
  (update-tallest tallest spot :y))

(defn tallest-column [tallest spot]
  (update-tallest tallest spot :x))

(defn check-spot [[visible tallest-rows tallest-columns]
                  {:keys [x y height] :as spot}
                  grid]
  [visible
   (tallest-row tallest-rows spot)
   (tallest-column tallest-columns spot)])

(defn find-visible [grid]
  (let [buckets (->> grid
                     grid->seq
                     (group-by (fn [spot] (on-edge? spot grid))))
        edges (buckets true)
        interior (buckets false)
        tallest-rows (reduce tallest-row [] edges)
        tallest-columns (reduce tallest-column [] edges)]
    (->> interior
         (reduce (fn [state spot]
                   (check-spot state spot grid))
                 [#{} tallest-rows tallest-columns])
         first
         (concat edges))))

(defn num-visible [grid]
  (count (find-visible grid)))

(defn -main []
  (let [grid (read-input)]
    (println "Part 1:" (num-visible grid))))
