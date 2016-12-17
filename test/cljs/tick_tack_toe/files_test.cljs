(ns tick-tack-toe.files-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [tick-tack-toe.game :as g]
            [tick-tack-toe.files :as files]))


(deftest game-history-to-string
  (testing "dump"
    (is (= (files/game-history-to-str [(g/move. 1 1 1) (g/move. 0 2 3)])
           "2\n1 1 1\n0 2 3"))))

(deftest str-to-game-history
  (testing "load"
    (is (= (files/str-to-game-history "2\n1 1 1\n0 2 3")
           [(g/move. 1 1 1) (g/move. 0 2 3)]))))
