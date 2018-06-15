(ns depl.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [depl.core-test]))

(doo-tests 'depl.core-test)
