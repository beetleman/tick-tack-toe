(ns tick-tack-toe.views
    (:require [re-frame.core :as re-frame]))



(defn button [icon text]
  [:button.pure-button
   icon text])

(defn button-load []
  [button [:i.fa.fa-folder-open] " Load Game"])

(defn button-save []
  [button [:i.fa.fa-floppy-o] " Save Game"])


(defn button-new []
  [button [:i.fa.fa-file-o] " New Game"])

(defn control []
  [:div.control
   [button-load]
   [button-save]
   [button-new]])


(defn main-panel []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div
       [:h1 @name]
       [control]])))

