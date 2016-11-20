(ns tick-tack-toe.views.core
  (:require [re-frame.core :as re-frame]
            [tick-tack-toe.views.game :as game-view]))


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


(def fake-game
  [[nil nil nil nil]
   [nil 1 1 nil]
   [nil 0 nil nil]
   [nil nil nil nil]])

(defn main-page []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [:div
       [:h1 @name]
       [control]
       [game-view/board fake-game]])))


