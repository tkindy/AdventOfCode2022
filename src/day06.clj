(ns day06
  (:require [clojure.string :as str]))

(defn parse-input [input]
  (str/trim input))

(defn read-input []
  (parse-input (slurp "inputs/day06.txt")))

(defn all-unique [coll]
  (= (count (set coll))
     (count coll)))

(defn all-unique-index [length datastream]
  (let [marker-start (->> (partition length 1 datastream)
                          (map-indexed vector)
                          (filter (comp all-unique second))
                          first
                          first)]
    (+ marker-start length)))

(defn packet-start-distance [datastream]
  (all-unique-index 4 datastream))

(defn message-start-distance [datastream]
  (all-unique-index 14 datastream))

(defn -main []
  (let [datastream (read-input)]
    (println "Part 1:" (packet-start-distance datastream))
    (println "Part 2:" (message-start-distance datastream))))
