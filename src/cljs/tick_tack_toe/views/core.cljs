(ns tick-tack-toe.views.core
  (:require [re-frame.core :as re-frame]
            [tick-tack-toe.files :as f]
            [tick-tack-toe.views.game :as game-view]))


(defn put-upload [e]
  (let [reader (js/FileReader.)
        file (aget e "target" "files" 0)]
    (.readAsText reader file)
    (set! (.-onload reader)
          (fn [x]
            (let [game-history (-> x
                                   .-target
                                   .-result
                                   js->clj
                                   f/str-to-game-history)]
              (re-frame/dispatch [:load-game game-history]))))))


(defn button-load []
  [:button.pure-button.load
   [:i.fa.fa-folder-open]
   " Load Game"
   [:input {:type "file"
            :accept ".txt"
            :on-change put-upload}]])


(defn button-save [game-history]
  [:a.pure-button.save {:href (str "data:text/plain;charset=utf-8,"
                              (js/encodeURIComponent
                               (f/game-history-to-str game-history)))
                   :download "game.txt"}
   [:i.fa.fa-floppy-o] " Save Game"])


(defn button-new []
  [:button.pure-button.new {:on-click #(re-frame/dispatch [:new-game])}
   [:i.fa.fa-file-o] " New Game"])


(defn control [game-history]
  [:div.control
   [button-load]
   [button-save game-history]
   [button-new]])


(defn main-page []
  (let [name (re-frame/subscribe [:name])
        game-history (re-frame/subscribe [:game-history])
        who-win (re-frame/subscribe [:winner])
        game-board (re-frame/subscribe [:board])]
    (fn []
      [:div
       [:h1 @name]
       [control @game-history]
       [game-view/board @game-board @who-win]])))
