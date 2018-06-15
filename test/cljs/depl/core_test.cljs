(ns depl.core-test
  (:require [cljs.test :refer-macros [deftest testing is]]
            [depl.core :as core]))

(deftest fake-test
  (testing "fake description"
    (is (= 1 2))))
