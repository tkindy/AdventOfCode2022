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

(defn inspect-item [item {:keys [operation]}]
  (->> operation
       (map (fn [a] (if (= a 'old)
                      item
                      a)))
       eval))

(defn test-item [item {:keys [test]}]
  (let [{:keys [divisor]} test
        result (zero? (mod item divisor))]
    (get test result)))

(defn handle-item [item monkey monkey-id monkeys]
  (let [item (inspect-item item monkey)
        item (unchecked-divide-int item 3)
        destination (test-item item monkey)]
    (-> monkeys
        (update-in [destination :items] conj item)
        (update-in [monkey-id :items] (fn [items] (subvec items 1))))))

(defn run-turn [monkey i monkeys]
  (reduce (fn [monkeys item]
            (handle-item item monkey i monkeys))
          monkeys
          (:items monkey)))

(defn run-round [monkeys]
  (reduce (fn [[monkeys inspect-counts] i]
            (let [monkey (nth monkeys i)
                  monkeys (run-turn monkey i monkeys)]
              [monkeys (conj inspect-counts (count (:items monkey)))]))
          [monkeys []]
          (range (count monkeys))))

(defn run [monkeys]
  (->> (range)
       (reductions (fn [[monkeys _] _]
                     (run-round monkeys))
                   [monkeys nil])
       (drop 1)
       (map second)))

(defn inspected [monkeys]
  (->> monkeys
       run
       (take 20)
       (reduce (partial mapv +)
               (mapv (constantly 0) monkeys))))

(defn monkey-business-20 [monkeys]
  (let [inspect-counts (inspected monkeys)]
    (->> inspect-counts
         sort
         reverse
         (take 2)
         (apply *))))

(defn -main []
  (let [monkeys (read-input)]
    (println "Part 1:" (monkey-business-20 monkeys))))
