(ns day06
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (str/trim input))

(defn read-input []
  (parse-input (slurp "inputs/day06.txt")))

(def packet-length 4)

(defn all-unique [coll]
  (= (count (set coll))
     (count coll)))

(defn packet-start-distance [datastream]
  (let [marker-start (->> (partition packet-length 1 datastream)
                          (map-indexed vector)
                          (filter (comp all-unique second))
                          first
                          first)]
    (+ marker-start packet-length)))

(defn -main []
  (let [datastream (read-input)]
    (println "Part 1:" (packet-start-distance datastream))))
