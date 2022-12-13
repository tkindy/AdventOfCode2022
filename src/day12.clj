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

(defn legal-neighbor-helper [candidate heightmap comparison]
  (let [candidate-height (get-height candidate heightmap)]
    (and candidate-height
         (comparison candidate-height))))

(defn legal-neighbor? [candidate height heightmap]
  (legal-neighbor-helper candidate heightmap #(<= % (inc height))))

(defn legal-backwards-neighbor? [candidate height heightmap]
  (legal-neighbor-helper candidate heightmap #(>= % (dec height))))

(defn neighbors [{:keys [x y] :as node} heightmap good-neighbor?]
  (let [height (get-height node heightmap)
        candidates [{:x x, :y (dec y)}
                    {:x x, :y (inc y)}
                    {:x (dec x), :y y}
                    {:x (inc x), :y y}]]
    (filter (fn [candidate]
              (good-neighbor? candidate height heightmap))
            candidates)))

(defn unvisited-neighbors [current heightmap unvisited good-neighbor?]
  (->> (neighbors current heightmap good-neighbor?)
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
       (map (juxt identity (comp :distance distances)))
       (apply min-key second)))

(defn shortest-distances [{:keys [start heightmap] :as state} good-neighbor?]
  (loop [unvisited (build-unvisited heightmap)
         distances (build-distances state)
         current   start]
    (let [neighbors (unvisited-neighbors current heightmap unvisited good-neighbor?)
          distances (update-distances current neighbors distances)
          unvisited (disj unvisited current)]
      (if (empty? unvisited)
        distances
        (let [[next-node distance] (next-node unvisited distances)]
          (if (= distance ##Inf)
            distances
            (recur unvisited distances next-node)))))))

(defn shortest-path-distance [{:keys [start end] :as state}]
  (let [distances (shortest-distances state legal-neighbor?)
        path (build-path distances start end)]
    (-> path
        count
        dec)))

(defn all-lowest [heightmap]
  (->> heightmap
       grid->coords
       (filter (fn [node]
                 (zero? (get-height node heightmap))))))

(defn any-start-shortest [{:keys [end heightmap]}]
  (let [distances (shortest-distances {:start end, :heightmap heightmap}
                                      legal-backwards-neighbor?)]
    (->> heightmap
         all-lowest
         (map (partial build-path distances end)))))

(defn any-start-shortest-distance [state]
  (->> state
       any-start-shortest
       (map count)
       (apply min)
       dec))

(defn -main []
  (let [state (read-input)]
    (println "Part 1:" (shortest-path-distance state))
    (println "Part 2:" (any-start-shortest-distance state))))
