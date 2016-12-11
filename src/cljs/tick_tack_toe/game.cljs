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
  (let [sorted (sort-by keyfn coll)
        first-el (first sorted)]
    (loop [coll (rest sorted)
           curr (if first-el [first-el] [])
           subseq []]
      (cond
        (empty? coll)
        (if (empty? curr)
          subseq
          (conj subseq curr))

        (= (-> curr last incfn keyfn) (-> coll first keyfn))
        (recur (rest coll) (conj curr (first coll)) subseq)

        :default
        (recur (rest coll) [(first coll)] (conj subseq curr))))))


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


(defn find-all-lines [coll max-size]
  {:h (find-h-lines coll max-size)
   :v (find-v-lines coll max-size)
   :x-r (find-x-r-lines coll max-size)
   :x-l (find-x-l-lines coll max-size)})


(defn win? [player win-length max-size]
  (or (has-wining-line player find-v-lines win-length max-size)
      (has-wining-line player find-h-lines win-length max-size)
      (has-wining-line player find-x-r-lines win-length max-size)
      (has-wining-line player find-x-l-lines win-length max-size)))


(defn group-by-who [game-history]
  (let [grouped (group-by :who game-history)
        me (get grouped c/me)
        you (get grouped c/you)]
    {:me me :you you}))


(defn who-win [game-history win-length max-size]
  (let [{:keys [me you]} (group-by-who game-history)]
    (cond
      (or (win? me win-length max-size))
      c/me

      (or (win? you win-length max-size))
      c/you

      :default
      nil)))

(defn is-posible-move? [game-history max-size {:keys [x y]}]
  (and
   (< 0 x max-size)
   (< 0 y max-size)
   ((complement contains?)
    (set (map #(select-keys % [:x :y]) game-history))
    [x y])))


(defn find-all-lines-by-who [game-history max-size]
  (reduce
   (fn [acc [k v]] (assoc acc k (find-all-lines v max-size)))
   {}
   (group-by-who game-history)))
