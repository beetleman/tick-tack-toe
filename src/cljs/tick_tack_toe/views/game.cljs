(ns tick-tack-toe.views.game
  (:require [re-frame.core :as re-frame]
            [tick-tack-toe.consts :as c]))


(defn cell [cell-definition row-nr cell-nr]
  (cond
    (= cell-definition nil)
    [:td {:on-click #(re-frame/dispatch [:check-field row-nr cell-nr])}
     [:div.field.empty]]

    (= cell-definition c/me)
    [:td
     [:div.field.me]]

    (= cell-definition c/you)
    [:td
     [:div.field.you]]))


(defn row [row-definition row-nr]
  [:tr
   (map-indexed
    (fn [idx cell-definition]
      ^{:key idx} [cell cell-definition row-nr idx])
    row-definition)])


(defn board [board-definition]
  [:table.game.pure-table.pure-table-bordered
   [:tbody
    (map-indexed
     (fn [idx row-definition]
       ^{:key idx} [row row-definition idx])
     board-definition)]])
