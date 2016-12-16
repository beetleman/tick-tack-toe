(ns tick-tack-toe.game-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [tick-tack-toe.game :as game]))

(def horizontal-2
  [(game/move. 1 1 0)
   (game/move. 0 2 2)
   (game/move. 1 2 0)])

(def all-posible-lines-by-who-for-horizontal-2
  {:me {:h [[(game/move. 1 1 0)
             (game/move. 1 2 0)]]
        :v [[(game/move. 1 1 0)]
            [(game/move. 1 2 0)]]
        :x-l [[(game/move. 1 1 0)]
              [(game/move. 1 2 0)]]
        :x-r [[(game/move. 1 1 0)]
              [(game/move. 1 2 0)]]}
   :you {:h [[(game/move. 0 2 2)]]
         :v [[(game/move. 0 2 2)]]
         :x-l [[(game/move. 0 2 2)]]
         :x-r [[(game/move. 0 2 2)]]}})

(def horizontal-3
  [(game/move. 1 2 0)
   (game/move. 1 4 0)
   (game/move. 1 5 0)
   (game/move. 1 5 2)
   (game/move. 1 6 0)
   (game/move. 0 7 0)])

(def vertical-3
  [(game/move. 1 1 8)
   (game/move. 1 1 10)
   (game/move. 1 1 11)
   (game/move. 1 3 11)
   (game/move. 1 1 12)
   (game/move. 0 1 13)])

(def cross-3-l
  [(game/move. 1 4 0)
   (game/move. 1 7 3)
   (game/move. 1 8 4)
   (game/move. 1 6 000000000)
   (game/move. 1 9 5)
   (game/move. 0 10 6)])

(def cross-3-r
  [(game/move. 0 10 3)
   (game/move. 1 9 4)
   (game/move. 1 9 5)
   (game/move. 1 8 5)
   (game/move. 1 7 6)
   (game/move. 1 5 8)])


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
            [nil 0]]))))

(deftest find-lines
  (testing "find-h-lines"
    (is (= (game/find-h-lines [{:x 1 :y 1} {:x 2 :y 1} {:x 4 :y 1} {:x 5 :y 1} {:x 6 :y 1}] 10)
           [[{:x 1, :y 1} {:x 2, :y 1}] [{:x 4, :y 1} {:x 5, :y 1} {:x 6, :y 1}]]))))


(deftest who-win
  (testing "win horizontal line"
    (is (= (game/who-win horizontal-3 4 15) nil))
    (is (= (game/who-win horizontal-3 3 15) 1)))

  (testing "win vertical line"
    (is (= (game/who-win vertical-3 4 15) nil))
    (is (= (game/who-win vertical-3 3 15) 1)))

  (testing "win cross right line"
    (is (= (game/who-win cross-3-r 4 15) nil))
    (is (= (game/who-win cross-3-r 3 15) 1)))

  (testing "win cros left line"
    (is (= (game/who-win cross-3-l 4 15) nil))
    (is (= (game/who-win cross-3-l 3 15) 1))))



(deftest find-all-lines-by-who
  (testing "working"
    (is (= (game/find-all-lines-by-who horizontal-2 15)
           all-posible-lines-by-who-for-horizontal-2))))

(deftest is-posible-move?
  (testing "negative numbers"
    (is (= (game/is-posible-move?
            horizontal-2 5
            (game/move. 1 1 -1))
           false)
        (= (game/is-posible-move?
            horizontal-2 5
            (game/move. 1 -1 1))
           false)))

  (testing "out of range"
    (is
     (= (game/is-posible-move?
         horizontal-2 5
         (game/move. 1 1 5))
        false)
     (= (game/is-posible-move?
         horizontal-2 5
         (game/move. 1 5 1))
        false)))

  (testing "field alredy used"
    (is
     (= (game/is-posible-move?
         horizontal-2 5
         (game/move. 1 1 0))
        false)
     (= (game/is-posible-move?
         horizontal-2 5
         (game/move. 1 2 0))
        false)))

  (testing "corect field"
    (is
     (= (game/is-posible-move?
         horizontal-2 5
         (game/move. 1 4 1))
        true)
     (= (game/is-posible-move?
         horizontal-2 5
         (game/move. 1 2 4))
        true))))


(deftest find-all-posible-moves-by-who
  (testing "retuns proper data"
    (is (map? (game/find-all-posible-moves-by-who horizontal-2 16 3)))
    #_(is (= (game/find-all-posible-moves-by-who horizontal-2 16 3)
           {:me [{:move (game/move.  1 1 1) :to-win 2}
                 {:move (game/move. 1 2 1) :to-win 2}
                 {:move (game/move. 1 3 1) :to-win 2}]

            :you [{:move (game/move. 0 2 2) :to-win 2}]}))))


#_(deftest update-by-conter-moves
  (let [all-posible-moves-by-who (game/find-all-posible-moves-by-who
                                  horizontal-2 6 3)]
    (is (= (game/update-by-conter-moves all-posible-moves-by-who 3)
           {:me [{:move (game/move. 1 1 1) :to-win 2}
                 {:move (game/move. 1 2 1) :to-win 2}
                 {:move (game/move. 1 3 1) :to-win 2}
                 {:move (game/move. 1 2 2) :to-win 2}]

            :you [{:move (game/move. 0 2 2) :to-win 2}
                  {:move (game/move. 0 1 1) :to-win 2}
                  {:move (game/move. 0 2 1) :to-win 2}
                  {:move (game/move. 0 3 1) :to-win 2}]}))))
