(ns tick-tack-toe.subs-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [tick-tack-toe.game :as g]
            [tick-tack-toe.subs :as subs]))

(deftest subs-test
  (testing "get-name"
    (is (= (subs/get-name {:name "yoo"}) "yoo")))

  (testing "get-winner"
    (is (= (subs/get-winner {:game-history []})
           nil))
    (is (= (subs/get-winner {:game-history [(g/move. 0 1 1)
                                            (g/move. 0 1 2)
                                            (g/move. 0 1 3)
                                            (g/move. 0 1 4)
                                            (g/move. 0 1 5)]})
           0)))

  (testing "get-board"
    (is (= (subs/get-board {:game-history [(g/move. 0 1 5) (g/move. 1 9 9)]})
           [[nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil 0 nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil 1]]))
    (is (= (subs/get-board {:game-history []})
           [[nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]
            [nil nil nil nil nil nil nil nil nil nil]]))))
