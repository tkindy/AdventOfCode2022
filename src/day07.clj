(ns day07
  (:require [clojure.string :as str]))

(defn parse-command-line [line]
  (let [[_ command arg] (str/split line #" ")]
    [(keyword command) arg]))

(defn split-execution [lines]
  (let [[command arg] (parse-command-line (first lines))
        output (->> (rest lines)
                    (take-while #(not (str/starts-with? % "$"))))]
    [{:command command
      :arg arg
      :output output}
     (drop (inc (count output)) lines)]))

(defn split-executions [lines]
  (if (empty? lines)
    nil
    (let [[execution rest] (split-execution lines)]
      (cons execution (split-executions rest)))))

(defn split-path [path]
  (rest (str/split path #"/")))

(defn cd-up [path]
  (str "/" (->> path
                split-path
                drop-last
                (str/join "/"))))

(defn change-directory [{:keys [path] :as state} {:keys [arg]}]
  (let [new-path (case arg
                   "/" "/"
                   ".." (cd-up path)
                   (str path "/" arg))]
    (assoc state :path new-path)))

(defn parse-file [line]
  (let [[size name] (str/split line #" ")]
    [name {:size (parse-long size)}]))

(defn parse-files [output]
  (->> output
       (filter #(not (str/starts-with? % "dir")))
       (map parse-file)
       (into {})))

(defn path->keys [path]
  (mapcat (fn [key] [:contents key])
          (split-path path)))

(defn capture-files [{:keys [path root]} {:keys [output]}]
  (let [files (parse-files output)]
    (update-in root
               (concat (path->keys path) [:contents])
               merge files)))

(defn process-execution [state {:keys [command] :as execution}]
  (case command
    :cd (change-directory state execution)
    :ls (capture-files state execution)))

(defn parse-input [input]
  (->> input
       str/trim
       str/split-lines
       split-executions
       (reduce process-execution {:path "/"
                                  :root {:contents {}}})
       :root))

(defn read-input []
  (parse-input (slurp "inputs/day07.txt")))

(defn small-dirs-size [root]
  (throw (RuntimeException. "Not yet implemented")))

(defn -main []
  (let [root (read-input)]
    (println "Part 1:" (small-dirs-size root))))
