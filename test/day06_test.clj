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

(deftest message-start-distance
  (are [x y] (= (day06/message-start-distance x) y)
    "mjqjpqmgbljsphdztnvjfqwrcgsmlb"    19
    "bvwbjplbgvbhsrlpgdmjqwftvncz"      23
    "nppdvjthqldpwncqszvftbrmjlhg"      23
    "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg" 29
    "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw"  26))
