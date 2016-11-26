(ns tick-tack-toe.events
    (:require [re-frame.core :as re-frame]
              [tick-tack-toe.db :as db]
              [tick-tack-toe.game :as g]
              [tick-tack-toe.consts :as c]))


(defn initialize-db-handler [_ _]
  db/default-db)

(re-frame/reg-event-db
 :initialize-db
 initialize-db-handler)


(defn check-field-handler [db [_ x y]]
  (update db :game-history conj (g/move. c/you x y)))

(re-frame.core/reg-event-db
 :check-field
 check-field-handler)


(defn new-game-handler [db _]
  (assoc db :game-history []))

(re-frame.core/reg-event-db
 :new-game
 new-game-handler)
