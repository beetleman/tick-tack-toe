(ns tick-tack-toe.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]
              [tick-tack-toe.consts :as c]
              [tick-tack-toe.game :as g]))


(defn get-name [db]
  (:name db))

(re-frame/reg-sub
 :name
 get-name)


(defn get-board [db]
  (g/board (:game-history db) c/max-size))

(re-frame/reg-sub
 :board
 get-board)


(defn get-winner [db]
  (g/who-win (:game-history db) c/win-length c/max-size))

(re-frame/reg-sub
 :winner
 get-winner)
