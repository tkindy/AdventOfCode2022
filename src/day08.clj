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

(defn visible-in-dir? [{:keys [height]} line]
  (or (empty? line)
      (< (apply max line) height)))

(defn extract-row [grid row start end]
  (subvec (nth grid row) start end))

(defn extract-column [grid col start end]
  (->> grid
       (mapv (fn [row] (nth row col)))
       (drop start)
       (take (- end start))))

(defn visible? [{:keys [x y] :as spot} grid]
  (or (visible-in-dir? spot (extract-row grid y 0 x))
      (visible-in-dir? spot (extract-row grid y (inc x) (count grid)))
      (visible-in-dir? spot (extract-column grid x 0 y))
      (visible-in-dir? spot (extract-column grid x (inc y) (count grid)))))

(defn find-visible [grid]
  (->> grid
       grid->seq
       (filter (fn [spot] (visible? spot grid)))))

(defn num-visible [grid]
  (count (find-visible grid)))

(defn num-trees [{:keys [height]} line]
  (if (empty? line)
    0
    (->> line
         (take-while #(< % height))
         count
         inc)))

(defn num-trees-up [{:keys [x y] :as spot} grid]
  (num-trees spot (reverse (extract-row grid y 0 x))))

(defn num-trees-down [{:keys [x y] :as spot} grid]
  (num-trees spot (extract-row grid y (inc x) (count grid))))

(defn num-trees-left [{:keys [x y] :as spot} grid]
  (num-trees spot (reverse (extract-column grid x 0 y))))

(defn num-trees-right [{:keys [x y] :as spot} grid]
  (num-trees spot (extract-column grid x (inc y) (count grid))))

(defn scenic [spot grid]
  (* (num-trees-up spot grid)
     (num-trees-down spot grid)
     (num-trees-left spot grid)
     (num-trees-right spot grid)))

(defn all-scenics [grid]
  (->> grid
       grid->seq
       (map (fn [spot] (scenic spot grid)))))

(defn max-scenic [grid]
  (->> grid
       all-scenics
       (apply max)))

(defn -main []
  (let [grid (read-input)]
    (println "Part 1:" (num-visible grid))
    (println "Part 2:" (max-scenic grid))))
