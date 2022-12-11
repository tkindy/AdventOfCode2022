(ns day11
  (:require [clojure.string :as str]))

(defn split-monkeys [input]
  (str/split input #"\n\n"))

(defn parse-items [input]
  (let [[_ numbers] (re-find #"Starting items: (.*?)\n" input)]
    (->> (str/split numbers #", ")
         (mapv parse-long))))

(defn parse-op [op]
  (symbol op))

(defn parse-arg [arg]
  (if (= arg "old")
    'old
    (parse-long arg)))

(defn parse-operation [input]
  (let [[_ expression] (re-find #"Operation: new = (.*?)\n" input)
        [left op right] (str/split expression #" ")]
    (list (parse-op op)
          (parse-arg left)
          (parse-arg right))))

(defn parse-divisor [input]
  (let [[_ divisor] (re-find #"Test: divisible by (\d+)" input)]
    (parse-long divisor)))

(defn parse-true [input]
  (let [[_ num] (re-find #"If true: throw to monkey (\d+)" input)]
    (parse-long num)))

(defn parse-false [input]
  (let [[_ num] (re-find #"If false: throw to monkey (\d+)" input)]
    (parse-long num)))

(defn parse-monkey [input]
  {:items (parse-items input)
   :operation (parse-operation input)
   :test {:divisor (parse-divisor input)
          true (parse-true input)
          false (parse-false input)}})

(defn parse-input [input]
  (->> input
       str/trim
       split-monkeys
       (mapv parse-monkey)))

(defn read-input []
  (parse-input (slurp "inputs/day11.txt")))

(defn monkey-business-20 [monkeys]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [monkeys (read-input)]
    (println "Part 1:" (monkey-business-20 monkeys))))
