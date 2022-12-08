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

(defn build-tallest [edges first-line?]
  (->> edges
       (filter first-line?)
       (mapv :height)))

(defn visible? [{:keys [height] :as spot}
                tallest
                key]
  (> height (get tallest (spot key))))

(defn update-tallest [tallest
                      {:keys [height] :as spot}
                      coord-key]
  (update tallest (spot coord-key) max height))

(defn visible [[visible tallest] spot coord-key]
  [(if (visible? spot tallest coord-key)
     (conj visible spot)
     visible)
   (update-tallest tallest spot coord-key)])

(defn find-visible-dir [[interior edges] sort-fn first-line? coord-key]
  (->> interior
       sort-fn
       (reduce (fn [state spot]
                 (visible state spot coord-key))
               [#{} (build-tallest edges first-line?)])
       first))

(defn find-visible-up [state]
  (find-visible-dir state
                    identity
                    (comp zero? :y)
                    :x))

(defn find-visible-down [state grid-size]
  (find-visible-dir state
                    (fn [interior]
                      (->> interior
                           (sort-by :y)
                           reverse))
                    (fn [{:keys [y]}] (= y (dec grid-size)))
                    :x))

(defn find-visible-left [state]
  (find-visible-dir state
                    identity
                    (comp zero? :x)
                    :y))

(defn find-visible-right [state grid-size]
  (find-visible-dir state
                    (fn [interior]
                      (->> interior
                           (sort-by :x)
                           reverse))
                    (fn [{:keys [x]}] (= x (dec grid-size)))
                    :y))

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
        right (find-visible-right splits grid-size)]
    (set/union up
               down
               left
               right
               (set edges))))

(defn num-visible [grid]
  (count (find-visible grid)))

(defn max-scenic [grid]
  0)

(defn -main []
  (let [grid (read-input)]
    (println "Part 1:" (num-visible grid))
    (println "Part 2:" (max-scenic grid))))
