(ns day10
  (:require [clojure.string :as str]))

(defn parse-line [line]
  (let [[opcode arg] (str/split line #" ")
        instruction {:opcode (keyword opcode)}]
    (if arg
      (assoc instruction :arg (parse-long arg))
      instruction)))

(defn parse-input [input]
  (->> input
       str/trim
       str/split-lines
       (map parse-line)))

(defn read-input []
  (parse-input (slurp "inputs/day10.txt")))

(defn evaluate-one [x {:keys [opcode arg]}]
  (case opcode
    :noop [x]
    :addx [x (+ x arg)]))

(defn evaluate-step [[x xs] instruction]
  (let [new-xs (evaluate-one x instruction)]
    [(last new-xs) (concat xs new-xs)]))

(defn evaluate [program]
  (second (reduce evaluate-step
                  [1 '(1)]
                  program)))

(defn signals [program]
  (->> program
       evaluate
       (map-indexed (fn [i x] (* (inc i) x)))))

(defn interesting-signals [program]
  (->> program
       signals
       (drop 19)
       (take-nth 40)))

(defn interesting-signals-sum [program]
  (apply + (interesting-signals program)))

(defn render-one [image [i x]]
  (let [crt-pos (mod i 40)
        newline (if (and (> i 0)
                         (zero? crt-pos))
                  "\n"
                  "")
        drawn (if (<= (dec x) crt-pos (inc x))
                "#"
                ".")]
    (str image newline drawn)))

(defn render [program]
  (->> program
       evaluate
       (take 240)
       (map-indexed vector)
       (reduce render-one "")))

(defn -main []
  (let [program (read-input)]
    (println "Part 1:" (interesting-signals-sum program))
    (println "Part 2:")
    (println (render program))))
