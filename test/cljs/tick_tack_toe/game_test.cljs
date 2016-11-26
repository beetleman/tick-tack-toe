(ns tick-tack-toe.game-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [tick-tack-toe.game :as game]))

(deftest new-board-test
  (testing "3x3"
    (is (= (game/new-board 3)
           [[nil nil nil]
            [nil nil nil]
            [nil nil nil]])))
  (testing "2x2"
    (= (game/new-board 2)
       [[nil nil]
        [nil nil]])))

(deftest board-test
  (testing "empty board"
    (is (= (game/board [] 2) [[nil nil]
                              [nil nil]])))

  (testing "record"
    (is (= 1 (:x (game/move. 2 1 3)))))

  (testing "board with history"
    (is (= (game/board
            [(game/move. 1 0 0)
             (game/move. 0 1 1)]
            2)
           [[1 nil]
            [nil 0]])))
  )
