(ns day05
  (:require [clojure.string :as str]))

(defn parse-spot [i spot]
  (let [letter (nth spot 1)]
    (if (= letter \space)
      {}
      {(inc i) (list letter)})))

(defn parse-level [line]
  (->> line
       (partition 4 4 [])
       (map-indexed parse-spot)
       (apply merge)))

(defn parse-levels [lines]
  (map parse-level lines))

;; if the last stacks are empty, the map won't have their keys.
;; ignoring that because my input doesn't have that problem
(defn parse-start [start]
  (->> (str/split-lines start)
       drop-last
       parse-levels
       (apply merge-with concat)))

(def step-regex #"move (\d+) from (\d+) to (\d+)")

(defn parse-step [step]
  (let [[_ count from to] (re-matches step-regex step)]
    {:count (parse-long count)
     :from (parse-long from)
     :to (parse-long to)}))

(re-matches step-regex "move 1 from 2 to 1")

(defn parse-procedure [procedure]
  (->> procedure
       str/trim
       str/split-lines
       (map parse-step)))

(defn parse-input [input]
  (let [[start procedure] (str/split input #"\n\n")]
    {:start (parse-start start)
     :procedure (parse-procedure procedure)}))

(defn read-input []
  (parse-input (slurp "inputs/day05.txt")))

(defn do-step [stacks {:keys [count from to]}]
  (let [to-move (->> (get stacks from)
                     (take count)
                     reverse)]
    (-> stacks
        (update from (fn [stack] (drop count stack)))
        (update to (fn [stack] (concat to-move stack))))))

(defn tops [stacks]
  (->> stacks
       (sort-by key)
       (map val)
       (map first)
       (apply str)))

(defn end-tops [{:keys [start procedure]}]
  (-> (reduce do-step start procedure)
      tops))

(defn -main []
  (let [input (read-input)]
    (println "Part 1:" (end-tops input))))
