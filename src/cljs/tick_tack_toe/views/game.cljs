(ns tick-tack-toe.views.game)


(defn cell [cell-definition]
  [:td
   (cond
     (= cell-definition nil)
     [:div.field.empty]

     (= cell-definition 1)
     [:div.field.me]

     (= cell-definition 0)
     [:div.field.you])])


(defn row [row-definition]
  [:tr
   (map-indexed
    (fn [idx cell-definition]
      ^{:key idx} [cell cell-definition])
    row-definition)])


(defn board [board-definition]
  [:table.game.pure-table.pure-table-bordered
   [:tbody
    (map-indexed
     (fn [idx row-definition]
       ^{:key idx} [row row-definition])
     board-definition)]])
