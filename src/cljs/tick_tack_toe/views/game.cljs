(ns tick-tack-toe.views.game
  (:require [re-frame.core :as re-frame]
            [tick-tack-toe.consts :as c]))


(defn cell [cell-definition row-nr cell-nr]
  (condp = cell-definition
         nil
         [:td {:on-click #(re-frame/dispatch [:check-field row-nr cell-nr])}
          [:div.field.empty]]

         c/me
         [:td
          [:div.field.me]]

         c/you
         [:td
          [:div.field.you]]))


(defn row [row-definition row-nr]
  [:tr
   (map-indexed
    (fn [idx cell-definition]
      ^{:key idx} [cell cell-definition row-nr idx])
    row-definition)])


(defn win [who]
  [:div.score.win
   [:div who " win!"]])

(defn lose [who]
  [:div.score.lose
   [:div who " lose!"]])


(defn board [board-definition winner]
  [:div.board
   (condp = winner
         c/you
         [:win "You"]

         c/me
         [:lose "You"]

         nil)
   [:table.game.pure-table.pure-table-bordered
    [:tbody
     (map-indexed
      (fn [idx row-definition]
        ^{:key idx} [row row-definition idx])
      board-definition)]]])
