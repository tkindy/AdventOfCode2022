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

(def beats {:paper :rock
            :scissors :paper
            :rock :scissors})

(defn outcome-score [[them me]]
  (cond
    (= them me) 3
    (= them (beats me)) 6
    :else 0))

(defn round-score [round]
  (let [[_ me] round]
    (+ (shape-score me)
       (outcome-score round))))

(defn expected-score [guide]
  (->> guide
       (map round-score)
       (apply +)))

(def fixes {:rock :lose
            :paper :draw
            :scissors :win})

(defn fix-guide [guide]
  (map (fn [[them me]] [them (fixes me)])
       guide))

(def beaten-by (->> beats
                    (map (fn [[k v]] [v k]))
                    (into {})))

(defn my-shape [[them outcome]]
  (case outcome
    :win  (beaten-by them)
    :lose (beats them)
    :draw them))

(defn real-round-score [round]
  (let [me (my-shape round)
        [them _] round]
    (round-score [them me])))

(defn expected-score-real [guide]
  (->> guide
       (map real-round-score)
       (apply +)))

(defn -main []
  (let [guide (read-input)]
    (println "Part 1:" (expected-score guide))
    (println "Part 2:" (expected-score-real (fix-guide guide)))))
