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

(defn build-tallest [grid]
  (->> grid
       count
       range
       (mapv (constantly -1))))

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

(defn find-visible-dir [grid sort-fn coord-key]
  (->> grid
       grid->seq
       sort-fn
       (reduce (fn [acc spot]
                 (visible acc spot coord-key))
               [#{} (build-tallest grid)])
       first))

(defn find-visible-up [grid]
  (find-visible-dir grid
                    identity
                    :x))

(defn find-visible-down [grid]
  (find-visible-dir grid
                    (fn [grid-seq]
                      (->> grid-seq
                           (sort-by :y)
                           reverse))
                    :x))

(defn find-visible-left [grid]
  (find-visible-dir grid
                    identity
                    :y))

(defn find-visible-right [grid]
  (find-visible-dir grid
                    (fn [grid-seq]
                      (->> grid-seq
                           (sort-by :x)
                           reverse))
                    :y))

(defn find-visible [grid]
  (let [up    (find-visible-up grid)
        down  (find-visible-down grid)
        left  (find-visible-left grid)
        right (find-visible-right grid)]
    (set/union up
               down
               left
               right)))

(defn num-visible [grid]
  (count (find-visible grid)))

(defn max-scenic [grid]
  0)

(defn -main []
  (let [grid (read-input)]
    (println "Part 1:" (num-visible grid))
    (println "Part 2:" (max-scenic grid))))
