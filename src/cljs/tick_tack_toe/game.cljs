(ns tick-tack-toe.game)

(defrecord move [who x y])

(defn repeatv [times v]
  (-> (repeat times v) vec))


(defn new-board [size]
  (->>
   (repeatv size nil)
   (repeatv size)))

(defn board [game-history size]
  (reduce
   (fn [acc itm]
     (assoc-in acc [(:x itm) (:y itm)] (:who itm)))
   (new-board size)
   game-history))

(defn check-random [board who]
  ;; TODO implementation
  board)
