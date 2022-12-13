(ns day12
  (:require [clojure.string :as str]))

(defn parse-grid [input]
  (->> input
       str/trim
       str/split-lines
       (mapv identity)))

(defn grid->coords [grid]
  (for [y (range (count grid))
        x (range (count (first grid)))]
    {:x x, :y y}))

(defn start [grid]
  (->> grid
       grid->coords
       (filter (fn [{:keys [x y]}]
                 (= (get-in grid [y x]) \S)))
       first))

(defn end [grid]
  (->> grid
       grid->coords
       (filter (fn [{:keys [x y]}]
                 (= (get-in grid [y x]) \E)))
       first))

(defn parse-height [c]
  (case c
    \S 0
    \E 25
    (- (int c) (int \a))))

(defn parse-line [line]
  (mapv parse-height line))

(defn heightmap [grid]
  (mapv parse-line grid))

(defn parse-input [input]
  (let [grid (parse-grid input)]
    {:start (start grid)
     :end (end grid)
     :heightmap (heightmap grid)}))

(defn read-input []
  (parse-input (slurp "inputs/day12.txt")))

(defn shortest-path-distance [state]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [state (read-input)]
    (println "Part 1:" (shortest-path-distance state))))
