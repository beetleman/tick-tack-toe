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

      (= (-> curr last incfn) (-> coll first keyfn))
      (recur (rest coll) (conj curr (first coll)) subseq)

      :default
      (recur (rest coll) [(first coll)] (conj subseq curr)))))


(defn find-h-lines [coll]
  (find-lines* coll :x #(-> % :x inc)))


(defn find-v-lines [coll]
  (find-lines* coll :y #(-> % :y inc)))


(defn find-x-r-lines [coll]
  (find-lines* coll
               (fn [itm] [(:x itm) (:y itm)])
               (fn [itm] [(-> itm :x inc) (-> itm :y inc)])))


(defn has-wining-line [player find-lines win-length]
  (let [lines (find-lines player)]
    ((complement empty?) (filter #(<= win-length (count %)) lines))))


(defn win? [player win-length]
  (or (has-wining-line player find-v-lines win-length)
      (has-wining-line player find-h-lines win-length)
      (has-wining-line player find-x-r-lines win-length)))


(defn who-win [game-history win-length]
  (let [grouped (group-by :who game-history)
        me (get grouped c/me)
        you (get grouped c/you)]
    (cond
      (or (win? me win-length))
      c/me

      (or (win? you win-length))
      c/you

      :default
      nil)))
