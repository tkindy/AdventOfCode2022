(ns day08
  (:require [clojure.string :as str]
            [clojure.set :as set]))

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

(defn visible-up? [{:keys [x height]} tallest]
  (> height (get tallest x)))

(defn update-tallest-up [tallest {:keys [x height]}]
  (update tallest x max height))

(defn visible-up [[visible tallest] spot]
  [(if (visible-up? spot tallest)
     (conj visible spot)
     visible)
   (update-tallest-up tallest spot)])

(defn build-tallest-up [edges]
  (->> edges
       (filter (comp zero? :y))
       (mapv :height)))

(defn find-visible-up [[interior edges]]
  (->> interior
       (reduce visible-up [#{} (build-tallest-up edges)])
       first))

(defn split-edges [grid]
  (let [buckets (group-by (fn [spot] (on-edge? spot grid))
                          (grid->seq grid))]
    [(buckets false) (buckets true)]))

(defn find-visible [grid]
  (let [splits (split-edges grid)
        [_ edges] splits
        up    (find-visible-up splits)]
    (set/union up
               (set edges))))

(defn num-visible [grid]
  (count (find-visible grid)))

(defn -main []
  (let [grid (read-input)]
    (println "Part 1:" (num-visible grid))))
