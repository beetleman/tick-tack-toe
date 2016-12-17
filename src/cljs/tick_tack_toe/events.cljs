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
  (let [db (update db :game-history conj (g/move. c/you x y))
        game-history (:game-history db)]
    (if (nil? (g/who-win game-history c/win-length c/max-size))
      (update db :game-history conj (g/generate-move game-history
                                                     :me
                                                     c/max-size
                                                     c/win-length))
      db)))

(re-frame.core/reg-event-db
 :check-field
 check-field-handler)


(defn new-game-handler [db _]
  (assoc db :game-history []))

(re-frame.core/reg-event-db
 :new-game
 new-game-handler)


(defn load-game-handler [db [_ game-history]]
  (let [db (assoc db :game-history game-history)]
    (if (= (-> game-history last :who) c/me)
      db
      (update db :game-history conj (g/generate-move game-history
                                                     :me
                                                     c/max-size
                                                     c/win-length)))))

(re-frame.core/reg-event-db
 :load-game
 load-game-handler)
