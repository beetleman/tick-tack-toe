(ns tick-tack-toe.events
    (:require [re-frame.core :as re-frame]
              [tick-tack-toe.db :as db]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))
