(ns tick-tack-toe.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]
              [tick-tack-toe.consts :as c]
              [tick-tack-toe.game :as g]))

(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 :board
 (fn [db]
   (g/board (:game-history db) c/max-size)))
