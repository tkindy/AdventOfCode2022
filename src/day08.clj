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

(defn build-distance-index [grid]
  (->> (range (count grid))
       (mapv (constantly {}))))

(defn lookup-distance [{:keys [height]}
                       index
                       depth]
  (let [higher-depths (->> index
                           keys
                           (filter #(>= % height))
                           (select-keys index)
                           vals)
        blocked-at (if (empty? higher-depths)
                     0
                     (apply max higher-depths))]
    (- depth blocked-at)))

(defn find-distance [distances
                     index
                     {:keys [x y] :as spot}
                     depth]
  (let [distance (lookup-distance spot index depth)]
    (assoc-in distances [y x] distance)))

(defn update-distance-index [index
                             {:keys [height]}
                             depth]
  (assoc index height depth))

(defn calc-distance [[distances indexes] spot coord-key depth-fn]
  (let [depth (depth-fn spot)
        index (get indexes (spot coord-key))]
    [(find-distance distances index spot depth)
     (assoc indexes
            (spot coord-key)
            (update-distance-index index spot depth))]))

(defn all-scenics-builder [r coord-key depth-fn grid]
  (->> (r (fn [acc spot]
            (calc-distance acc spot coord-key depth-fn))
          [{} (build-distance-index grid)]
          grid)
       first))

(defn calc-scenics [distances]
  (->> distances
       (apply merge-with
              (fn [a b]
                (merge-with * a b)))
       vals
       (map vals)
       flatten))

(defn all-scenics [grid]
  (let [grid-size (count grid)
        up (all-scenics-builder up-reduce
                                :x
                                :y
                                grid)
        down (all-scenics-builder down-reduce
                                  :x
                                  #(inc (- (:y %) grid-size))
                                  grid)
        left (all-scenics-builder left-reduce
                                  :y
                                  :x
                                  grid)
        right (all-scenics-builder right-reduce
                                   :y
                                   #(inc (- (:x %) grid-size))
                                   grid)]
    (calc-scenics [up down left right])))

(defn max-scenic [grid]
  (->> grid
       all-scenics
       (apply max)))

(defn -main []
  (let [grid (read-input)]
    (println "Part 1:" (num-visible grid))
    (println "Part 2:" (max-scenic grid))))
