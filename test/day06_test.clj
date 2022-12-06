(ns day06-test
  (:require day06
            [clojure.test :refer [deftest are]]))

(deftest packet-start-distance
  (are [x y] (= (day06/packet-start-distance x) y)
    "mjqjpqmgbljsphdztnvjfqwrcgsmlb"    7
    "bvwbjplbgvbhsrlpgdmjqwftvncz"      5
    "nppdvjthqldpwncqszvftbrmjlhg"      6
    "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" 10
    "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"  11))
