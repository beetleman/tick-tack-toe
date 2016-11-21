(ns tick-tack-toe.game)


(defn repeatv [times v]
  (-> (repeat times v) vec))


(defn new-board [size]
  (->>
   (repeatv size nil)
   (repeatv size)))

(defn check-random [board who]
  ;; TODO implementation
  board)
