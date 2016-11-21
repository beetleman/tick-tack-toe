(ns tick-tack-toe.events
    (:require [re-frame.core :as re-frame]
              [tick-tack-toe.db :as db]
              [tick-tack-toe.game :as g]
              [tick-tack-toe.consts :as c]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame.core/reg-event-db
 :check-field
 (fn [db [_ x y]]
   (assoc-in db [:board x y] c/you)))


(re-frame.core/reg-event-db
 :new-game
 (fn [db [_ x y]]
   (assoc db :board (g/new-board c/max-size))))
