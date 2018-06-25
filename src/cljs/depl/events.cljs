(ns depl.events
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [re-frame.core :as rf]
            [depl.db :as db]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-db
 ::set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

;; increments personal counter and dispatches
;; event to increment total counter
(rf/reg-event-fx
 ::inc-counter
 (fn [{:keys [db]} [_ username]]
   (println "DB HERE " db)
   {:db (update db :counter inc)
    :dispatch [::inc-total username]}))

;; used as a callback to decrement personal counter
;; if update to total counter fails
(rf/reg-event-db
 ::dec-counter
 (fn [db _]
   (update db :counter dec)))

(rf/reg-event-fx
 ::inc-total
 (fn [db [_ username]]
   (go (let [response (<! (http/post "http://localhost:3000/clicks"
                                     {:with-credentials? false
                                      :query-params {:username username}}))]
         (if (= 200 (:status response))
           nil
           (rf/dispatch [::dec-counter]))))
   {}))

