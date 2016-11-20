(ns tick-tack-toe.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [tick-tack-toe.core-test]))

(doo-tests 'tick-tack-toe.core-test)
