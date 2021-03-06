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

(def next-move-on-line-fns
  {:h #(update % :x inc)
   :v #(update % :y inc)
   :x-r #(-> % (update :x inc) (update :y dec))
   :x-l #(-> % (update :x dec) (update :y dec))})

(defn next-move-on-line [type m]
  ((next-move-on-line-fns type) m))

(def previous-move-on-line-fns
  {:h #(update % :x dec)
   :v #(update % :y dec)
   :x-r #(-> % (update :x dec) (update :y inc))
   :x-l #(-> % (update :x inc) (update :y inc))})

(defn previous-move-on-line [type m]
  ((previous-move-on-line-fns type) m))


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
               (next-move-on-line-fns :h)))


(defn find-v-lines [coll max-size]
  (find-lines* coll
               #(+ (:y %) (* (:x %) max-size))
               (next-move-on-line-fns :v)))


(defn find-x-r-lines [coll max-size]
  (find-lines* coll
               (fn [itm] (+ (:x itm) (:y itm) (/ (:x itm) max-size )))
               (next-move-on-line-fns :x-r)))



(defn find-x-l-lines [coll max-size]
  (find-lines* coll
               (fn [itm] (- (:x itm) (:y itm) (/ (:x itm) max-size)))
               (next-move-on-line-fns :x-l)))


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
   (< -1 x max-size)
   (< -1 y max-size)
   ((complement contains?)
    (set (map #(select-keys % [:x :y]) game-history))
    {:x x :y y})))



(defn find-all-lines-by-who [game-history max-size]
  (-> (group-by-who game-history)
      (update :you #(find-all-lines % max-size))
      (update :me #(find-all-lines % max-size))))

(defn find-good-move
  [game-history line win-length max-size next-fn previous-fn]
  (let [to-win (- win-length (count line) 1)
        valid? #(is-posible-move?
                   game-history
                   max-size
                   %)
        posible-moves- (->> (iterate previous-fn (first line))
                            (drop 1)
                            (take-while valid?))

        posible-moves+ (->> (iterate next-fn (last line))
                           (drop 1)
                           (take-while valid?))
        posible-length (+ (count posible-moves+) (count line) (count posible-moves-))
        create-move (fn [moves]
                      (let [move (first moves)]
                        {:to-win (if move
                                   to-win
                                   nil)
                         :posible-length posible-length
                         :move move}))]
    (if (< posible-length win-length)
      [(create-move [])]
      [(create-move posible-moves-)
       (create-move posible-moves+)])))


(defn find-all-posible-moves
  [game-history all-posible-lines win-length max-size]
  (->>
   (mapcat (fn [[k lines]]
             (let [next-fn (next-move-on-line-fns k)
                   previous-fn (previous-move-on-line-fns k)]
               (mapcat (fn [line]
                         (find-good-move game-history
                                         line
                                         win-length
                                         max-size
                                         next-fn
                                         previous-fn))
                       lines)))
           all-posible-lines)
   distinct
   (filter #(-> % :to-win nil? not))))

(defn find-all-posible-moves-by-who [game-history max-size win-length]
  (-> (find-all-lines-by-who game-history max-size)
      (update :you #(find-all-posible-moves game-history
                                            %
                                            win-length
                                            max-size))
      (update :me #(find-all-posible-moves game-history
                                           %
                                           win-length
                                           max-size))))

(defn update-by-conter-moves [all-posible-moves-by-who]
  (->
   all-posible-moves-by-who
   (update :me (fn [x]
                 (concat x (map #(->
                                  (assoc-in % [:move :who] c/me)
                                  #_(update :to-win dec))
                                (:you all-posible-moves-by-who)))))
   (update :you (fn [x]
                  (concat x (map #(->
                                   (assoc-in % [:move :who] c/you)
                                   #_(update :to-win dec))
                                 (:me all-posible-moves-by-who)))))))

(defn score [moves game-history max-size win-length]
  (map (fn [m] (assoc m :score (+ (- win-length (:to-win m))
                                  (/ (:posible-length m) max-size))))
       moves))

(defn generate-move [game-history who max-size win-length]
  (let [moves (-> (find-all-posible-moves-by-who game-history
                                                 max-size
                                                 win-length)
                  update-by-conter-moves
                  who)]
    (->>
     (score moves game-history max-size win-length)
     (sort-by :score)
     last
     :move)))
