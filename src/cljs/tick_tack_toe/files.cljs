(ns tick-tack-toe.files
  (:require [tick-tack-toe.consts :as c]
            [tick-tack-toe.game :as g]))


(defn str->number [s]
  (int (js/Number. s)))


(defn game-history-to-str [game-history]
  (let [length (count game-history)
        game-rows (map (fn [{:keys [who x y]}]
                         (clojure.string/join " " [who x y]))
                       game-history)]
    (clojure.string/join
     "\n"
     (concat
      [length]
      game-rows))))


(defn str-to-game-history [str]
  (let [raw-rowes (clojure.string/split-lines str)
        length (-> raw-rowes
                   first
                   str->number)]
    (mapv (fn [idx]
            (let [[who x y]  (map str->number
                                  (-> (nth raw-rowes idx)
                                      (clojure.string/split " ")))]
              (g/move. who x y)))
          (range 1 (inc length)))))
