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

(defn do-move-follower [follower {:keys [dx dy]}]
  (cond
    (zero? dx) (update follower :y + (/ dy (abs dy)))
    (zero? dy) (update follower :x + (/ dx (abs dx)))
    :else (-> follower
              (update :x + (/ dx (abs dx)))
              (update :y + (/ dy (abs dy))))))

(defn move-follower [follower leader]
  (let [delta (delta leader follower)]
    (if (touching? delta)
      follower
      (do-move-follower follower delta))))

(defn move-followers [followers head]
  (->> followers
       reverse
       (reduce (fn [followers follower]
                 (conj followers
                       (move-follower follower (first followers))))
               (list head))
       drop-last))

(defn move-one [[{:keys [head followers]} visited] dir]
  (let [head (move-head head dir)
        followers (move-followers followers head)]
    [{:head head, :followers followers}
     (conj visited (first followers))]))

(defn move [acc [dir amount]]
  (->> (range amount)
       (reduce (fn [acc _] (move-one acc dir)) acc)))

(defn visited [rope motions]
  (let [[_ result] (reduce move
                           [rope #{{:x 0, :y 0}}]
                           motions)]
    result))

(defn build-rope [knots]
  {:head {:x 0, :y 0}
   :followers (map (constantly {:x 0, :y 0})
                   (range (dec knots)))})

(defn visited-count [motions]
  (count (visited (build-rope 2) motions)))

(defn long-visited-count [motions]
  (count (visited (build-rope 10) motions)))

(defn -main []
  (let [motions (read-input)]
    (println "Part 1:" (visited-count motions))
    (println "Part 2:" (long-visited-count motions))))
