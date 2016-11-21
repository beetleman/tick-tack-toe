(ns tick-tack-toe.db
  (:require [tick-tack-toe.consts :as c]
            [tick-tack-toe.game :as g]))


(def default-db
  {:name "Tick-tack-toe"
   :board (g/new-board c/max-size)})
