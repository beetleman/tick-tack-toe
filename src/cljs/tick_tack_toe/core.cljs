(ns tick-tack-toe.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [tick-tack-toe.events]
              [tick-tack-toe.subs]
              [tick-tack-toe.views.core :as views]
              [tick-tack-toe.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (reagent/render [views/main-page]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
