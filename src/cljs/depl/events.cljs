(ns depl.events
  (:require
   [re-frame.core :as rf]
   [depl.db :as db]
   ))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-db
 ::set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(rf/reg-event-db
 ::inc-counter
 (fn [db _]
   (update db :counter inc)))
