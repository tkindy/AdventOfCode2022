(ns day12
  (:require [clojure.string :as str]))

(defn parse-grid [input]
  (->> input
       str/trim
       str/split-lines
       (mapv identity)))

(defn grid->coords [grid]
  (for [y (range (count grid))
        x (range (count (first grid)))]
    {:x x, :y y}))

(defn start [grid]
  (->> grid
       grid->coords
       (filter (fn [{:keys [x y]}]
                 (= (get-in grid [y x]) \S)))
       first))

(defn end [grid]
  (->> grid
       grid->coords
       (filter (fn [{:keys [x y]}]
                 (= (get-in grid [y x]) \E)))
       first))

(defn parse-height [c]
  (case c
    \S 0
    \E 25
    (- (int c) (int \a))))

(defn parse-line [line]
  (mapv parse-height line))

(defn heightmap [grid]
  (mapv parse-line grid))

(defn parse-input [input]
  (let [grid (parse-grid input)]
    {:start (start grid)
     :end (end grid)
     :heightmap (heightmap grid)}))

(defn read-input []
  (parse-input (slurp "inputs/day12.txt")))

(defn build-unvisited [heightmap]
  (set (grid->coords heightmap)))

(defn build-distances [{:keys [start heightmap]}]
  (assoc (->> heightmap
              grid->coords
              (map (fn [node] [node {:distance ##Inf}]))
              (into {}))
         start
         {:distance 0}))

(defn get-height [{:keys [x y]} heightmap]
  (get-in heightmap [y x]))

(defn legal-neighbor? [candidate height heightmap]
  (let [candidate-height (get-height candidate heightmap)]
    (and candidate-height
         (<= candidate-height (inc height)))))

(defn neighbors [{:keys [x y] :as node} heightmap]
  (let [height (get-height node heightmap)
        candidates [{:x x, :y (dec y)}
                    {:x x, :y (inc y)}
                    {:x (dec x), :y y}
                    {:x (inc x), :y y}]]
    (filter (fn [candidate]
              (legal-neighbor? candidate height heightmap))
            candidates)))

(defn unvisited-neighbors [current heightmap unvisited]
  (->> (neighbors current heightmap)
       (filter unvisited)))

(defn update-distances [current neighbors distances]
  (reduce (fn [distances neighbor]
            (let [current-distance (:distance (distances current))
                  neighbor-distance (inc current-distance)]
              (if (< neighbor-distance (:distance (distances neighbor)))
                (assoc distances neighbor {:distance neighbor-distance
                                           :from current})
                distances)))
          distances
          neighbors))

(defn build-path [distances start end]
  (loop [current end, path '()]
    (let [path (conj path current)]
      (if (= current start)
        path
        (recur (:from (distances current))
               path)))))

(defn next-node [unvisited distances]
  (->> unvisited
       (apply min-key (fn [node] (:distance (distances node))))))

(defn shortest-path [{:keys [start end heightmap] :as state}]
  (loop [unvisited (build-unvisited heightmap)
         distances (build-distances state)
         current   start]
    (let [neighbors (unvisited-neighbors current heightmap unvisited)
          distances (update-distances current neighbors distances)
          unvisited (disj unvisited current)]
      (if (= current end)
        (build-path distances start end)
        (recur unvisited distances (next-node unvisited distances))))))

(defn shortest-path-distance [state]
  (-> state
      shortest-path
      count
      dec))

(defn all-lowest [heightmap]
  (->> heightmap
       grid->coords
       (filter (fn [node]
                 (zero? (get-height node heightmap))))))

(defn any-start-shortest [{:keys [heightmap] :as state}]
  (->> (all-lowest heightmap)
       (map (partial assoc state :start))
       (map shortest-path-distance)
       (apply min)))

(defn -main []
  (let [state (read-input)]
    (println "Part 1:" (shortest-path-distance state))
    (println "Part 2:" (any-start-shortest state))))
