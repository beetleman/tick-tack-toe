(ns tick-tack-toe.views.core
  (:require [re-frame.core :as re-frame]
            [tick-tack-toe.views.game :as game-view]))


(defn button [options icon text]
  [:button.pure-button options
   icon text])


(defn button-load []
  [button {}
   [:i.fa.fa-folder-open] " Load Game"])


(defn button-save []
  [button {}
   [:i.fa.fa-floppy-o] " Save Game"])


(defn button-new []
  [button {:on-click #(re-frame/dispatch [:new-game])}
   [:i.fa.fa-file-o] " New Game"])


(defn control []
  [:div.control
   [button-load]
   [button-save]
   [button-new]])


(defn main-page []
  (let [name (re-frame/subscribe [:name])
        who-win (re-frame/subscribe [:winner])
        game-board (re-frame/subscribe [:board])]
    (fn []
      [:div
       [:h1 @name]
       [control]
       [game-view/board @game-board @who-win]])))


