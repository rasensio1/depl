(ns depl.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(rf/reg-sub
 ::counter
 (fn [db _]
   (:counter db)))

(rf/reg-sub
 ::total-count
 (fn [db _]
   (:total-count db)))
