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

(defn dir-reduce [f val sort-fn grid]
  (->> grid
       grid->seq
       sort-fn
       (reduce f val)))

(defn up-reduce [f val grid]
  (dir-reduce f val identity grid))

(def left-reduce up-reduce)

(defn down-reduce [f val grid]
  (dir-reduce f
              val
              (fn [grid-seq]
                (->> grid-seq
                     (sort-by :y)
                     reverse))
              grid))

(defn right-reduce [f val grid]
  (dir-reduce f
              val
              (fn [grid-seq]
                (->> grid-seq
                     (sort-by :x)
                     reverse))
              grid))

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

(defn find-visible [grid]
  (let [val [#{} (build-tallest grid)]
        builder (fn [r coord-key]
                  (->> (r (fn [acc spot]
                            (visible acc spot coord-key))
                          val
                          grid)
                       first))
        up    (builder up-reduce :x)
        down  (builder down-reduce :x)
        left  (builder left-reduce :y)
        right (builder right-reduce :y)]
    (set/union up
               down
               left
               right)))

(defn num-visible [grid]
  (count (find-visible grid)))

(defn all-scenics [grid]
  [0])

(defn max-scenic [grid]
  (->> grid
       all-scenics
       (apply max)))

(defn -main []
  (let [grid (read-input)]
    (println "Part 1:" (num-visible grid))
    (println "Part 2:" (max-scenic grid))))
