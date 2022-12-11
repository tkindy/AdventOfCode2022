(ns day11
  (:require [clojure.string :as str]))

(defn split-monkeys [input]
  (str/split input #"\n\n"))

(defn parse-items [input]
  (let [[_ numbers] (re-find #"Starting items: (.*?)\n" input)]
    (->> (str/split numbers #", ")
         (map parse-long))))

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

(defn handle-item [item monkey monkey-id monkeys item-fn lcm]
  (let [item (inspect-item item monkey)
        item (item-fn item)
        item (mod item lcm)
        destination (test-item item monkey)]
    (-> monkeys
        (update-in [destination :items] concat [item])
        (update-in [monkey-id :items] rest))))

(defn run-turn [monkey i monkeys item-fn lcm]
  (reduce (fn [monkeys item]
            (handle-item item monkey i monkeys item-fn lcm))
          monkeys
          (:items monkey)))

(defn run-round [monkeys item-fn lcm]
  (reduce (fn [[monkeys inspect-counts] i]
            (let [monkey (nth monkeys i)
                  monkeys (run-turn monkey i monkeys item-fn lcm)]
              [monkeys (conj inspect-counts (count (:items monkey)))]))
          [monkeys []]
          (range (count monkeys))))

(defn divisor-lcm [monkeys]
  (->> monkeys
       (map (comp :divisor :test))

       ; all primes, so the LCM is their product
       (apply *)))

(defn run [monkeys item-fn]
  (let [lcm (divisor-lcm monkeys)]
    (->> (range)
         (reductions (fn [[monkeys _] i]
                       (println "Round" i)
                       (run-round monkeys item-fn lcm))
                     [monkeys nil])
         (drop 1)
         (map second))))

(defn inspected [monkeys rounds item-fn]
  (->> (run monkeys item-fn)
       (take rounds)
       (reduce (partial mapv +)
               (mapv (constantly 0) monkeys))))

(defn monkey-business [monkeys rounds item-fn]
  (let [inspect-counts (inspected monkeys rounds item-fn)]
    (->> inspect-counts
         sort
         reverse
         (take 2)
         (apply *))))

(defn small-monkey-business [monkeys]
  (monkey-business monkeys
                   20
                   (fn [item] (unchecked-divide-int item 3))))

(defn big-monkey-business [monkeys]
  (monkey-business monkeys
                   10000
                   identity))

(defn -main []
  (let [monkeys (read-input)]
    (println "Part 1:" (small-monkey-business monkeys))
    (println "Part 2:" (big-monkey-business monkeys))))
