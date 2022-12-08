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

(defn visible-down? [{:keys [x height]} tallest]
  (> height (get tallest x)))

(defn update-tallest-down [tallest {:keys [x height]}]
  (update tallest x max height))

(defn visible-down [[visible tallest] spot]
  [(if (visible-down? spot tallest)
     (conj visible spot)
     visible)
   (update-tallest-down tallest spot)])

(defn build-tallest-down [edges grid-size]
  (->> edges
       (filter (fn [{:keys [y]}] (= y (dec grid-size))))
       (mapv :height)))

(defn find-visible-down [[interior edges] grid-size]
  (->> interior
       (sort-by :y)
       reverse
       (reduce visible-down [#{} (build-tallest-down edges grid-size)])
       first))

(defn split-edges [grid]
  (let [buckets (group-by (fn [spot] (on-edge? spot grid))
                          (grid->seq grid))]
    [(buckets false) (buckets true)]))

(defn find-visible [grid]
  (let [splits (split-edges grid)
        [_ edges] splits
        grid-size (count grid)
        up    (find-visible-up splits)
        down  (find-visible-down splits grid-size)
        left  (find-visible-left splits)
        right (find-visible-right splits)]
    (set/union up
               down
               left
               right
               (set edges))))

(defn num-visible [grid]
  (count (find-visible grid)))

(defn -main []
  (let [grid (read-input)]
    (println "Part 1:" (num-visible grid))))
