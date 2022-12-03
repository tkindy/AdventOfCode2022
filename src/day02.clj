(ns day02
  (:require [clojure.string :as str]))

(def them-map {"A" :rock
               "B" :paper
               "C" :scissors})
(def me-map {"X" :rock
             "Y" :paper
             "Z" :scissors})

(defn parse-input [input]
  (->> (str/split-lines input)
       (map (fn [line]
              (let [[them me] (str/split line #" ")]
                [(them-map them) (me-map me)])))))

(defn read-input []
  (parse-input (slurp "inputs/day02.txt")))

(def shape-score {:rock 1
                  :paper 2
                  :scissors 3})

(def beats {:rock :paper
            :paper :scissors
            :scissors :rock})

(defn outcome-score [[them me]]
  (cond
    (= them me) 3
    (= them (beats me)) 0
    :else 6))

(defn round-score [round]
  (let [[_ me] round]
    (+ (shape-score me)
       (outcome-score round))))

(defn expected-score [guide]
  (->> guide
       (map round-score)
       (apply +)))

(defn -main []
  (let [guide (read-input)]
    (println "Part 1:" (expected-score guide))))
