(ns tick-tack-toe.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [tick-tack-toe.core-test]
            [tick-tack-toe.events-test]
            [tick-tack-toe.subs-test]
            [tick-tack-toe.game-test]))

(doo-tests 'tick-tack-toe.core-test
           'tick-tack-toe.game-test
           'tick-tack-toe.subs-test
           'tick-tack-toe.events-test)
