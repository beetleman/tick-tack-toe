(ns tick-tack-toe.events-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [tick-tack-toe.game :as g]
            [tick-tack-toe.events :as events]))


(deftest events-tests
  (testing "init db"
    (is (= (events/initialize-db-handler nil nil)
           {:name "Tick-tack-toe"
            :game-history []})))

  (testing "check-field-handler"
    (is (= (events/check-field-handler {:game-history [(g/move. 1 1 1)]} [nil 2 3])
           {:game-history [(g/move. 1 1 1) (g/move. 0 2 3)]})))

  (testing "new-game-handler"
    (is (= (events/new-game-handler {:game-history [(g/move. 1 1 1)]} nil)
           {:game-history []})))
  )
