(ns day09
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (let [[dir amount] (str/split line #" ")
        dir (case dir
              "U" :up
              "D" :down
              "L" :left
              "R" :right)
        amount (parse-long amount)]
    [dir amount]))

(defn parse-input [input]
  (->> input
       str/trim
       str/split-lines
       (map parse-line)))

(defn read-input []
  (parse-input (slurp "inputs/day09.txt")))

(defn move-head [head dir]
  (case dir
    :up    (update head :y inc)
    :down  (update head :y dec)
    :left  (update head :x dec)
    :right (update head :x inc)))

(defn delta [{x1 :x, y1 :y} {x2 :x, y2 :y}]
  {:dx (- x1 x2), :dy (- y1 y2)})

(defn touching? [{:keys [dx dy]}]
  (let [adx (abs dx)
        ady (abs dy)]
    (or (<= (+ adx ady) 1)
        (= 1 adx ady))))

(defn do-move-tail [tail {:keys [dx dy]}]
  (cond
    (zero? dx) (update tail :y + (/ dy (abs dy)))
    (zero? dy) (update tail :x + (/ dx (abs dx)))
    :else (-> tail
              (update :x + (/ dx (abs dx)))
              (update :y + (/ dy (abs dy))))))

(defn move-tail [tail head]
  (let [delta (delta head tail)]
    (if (touching? delta)
      tail
      (do-move-tail tail delta))))

(defn move-one [[{:keys [head tail]} visited] dir]
  (let [head (move-head head dir)
        tail (move-tail tail head)]
    [{:head head, :tail tail}
     (conj visited tail)]))

(defn move [acc [dir amount]]
  (->> (range amount)
       (reduce (fn [acc _] (move-one acc dir)) acc)))

(defn visited [motions]
  (let [[_ result] (reduce move
                           [{:head {:x 0, :y 0}, :tail {:x 0, :y 0}}
                            #{{:x 0, :y 0}}]
                           motions)]
    result))

(defn visited-count [motions]
  (count (visited motions)))

(defn -main []
  (let [motions (read-input)]
    (println "Part 1:" (visited-count motions))))
