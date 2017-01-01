(ns tick-tack-toe.logger)

(def log (.-log js/console))
(defn log-it [n]
  (try
    (log n)
    (catch js/Object e
      e))
  n)
