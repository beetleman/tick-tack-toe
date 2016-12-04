(ns tick-tack-toe.game
  (:require [tick-tack-toe.consts :as c]))

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


(defn find-lines* [coll keyfn incfn]
  (loop [coll (sort-by keyfn coll)
         curr []
         subseq []]
    (cond
      (empty? coll)
      (conj subseq curr)

      (= (-> curr last incfn keyfn) (-> coll first keyfn))
      (recur (rest coll) (conj curr (first coll)) subseq)

      :default
      (recur (rest coll) [(first coll)] (conj subseq curr)))))


(defn find-h-lines [coll max-size]
  (find-lines* coll
               #(+ (:x %) (* (:y %) max-size))
               #(update % :x inc)))


(defn find-v-lines [coll max-size]
  (find-lines* coll
               #(+ (:y %) (* (:x %) max-size))
               #(update % :y inc)))


(defn find-x-r-lines [coll max-size]
  (find-lines* coll
               (fn [itm] (+ (:x itm) (:y itm) (/ (:x itm) max-size )))
               #(-> % (update :x inc) (update :y dec))))



(defn find-x-l-lines [coll max-size]
  (find-lines* coll
               (fn [itm] (- (:x itm) (:y itm) (/ (:x itm) max-size)))
               #(-> % (update :x dec) (update :y dec))))


(defn has-wining-line [player find-lines win-length max-size]
  (let [lines (find-lines player max-size)]
    ((complement empty?) (filter #(<= win-length (count %)) lines))))


(defn win? [player win-length max-size]
  (or (has-wining-line player find-v-lines win-length max-size)
      (has-wining-line player find-h-lines win-length max-size)
      (has-wining-line player find-x-r-lines win-length max-size)
      (has-wining-line player find-x-l-lines win-length max-size)))


(defn who-win [game-history win-length max-size]
  (let [grouped (group-by :who game-history)
        me (get grouped c/me)
        you (get grouped c/you)]
    (cond
      (or (win? me win-length max-size))
      c/me

      (or (win? you win-length max-size))
      c/you

      :default
      nil)))
