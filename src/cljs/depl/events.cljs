(ns depl.events
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [re-frame.core :as rf]
            [depl.db :as db]
            [cljs-http.client :as http]
            [cljs.core.async :refer [<!]]
            ))

(defn total-count-query! [& {:keys [on-success]}]
  (go (let [response (<! (http/get "/clicks/count"
                                   {:with-credentials? false}))]
        (if (= 200 (:status response))
          (on-success response)
          (println "total count query failed: " response)))))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   (rf/dispatch [::fetch-total])
   db/default-db))

(rf/reg-event-db
 ::set-active-panel
 (fn [db [_ active-panel]]
   (assoc db :active-panel active-panel)))

(rf/reg-event-db
 ::set-total
 (fn [db [_ total-count]]
   (assoc db :total-count total-count)))

;; increments personal counter and dispatches
;; event to increment total counter
(rf/reg-event-fx
 ::inc-counter
 (fn [{:keys [db]} [_ username]]
   (println "DB HERE " db)
   {:db (update db :counter inc)
    :dispatch [::inc-total username]
    }))

;; used as a callback to decrement personal counter
;; if update to total counter fails
(rf/reg-event-db
 ::dec-counter
 (fn [db _]
   (update db :counter dec)))

(rf/reg-event-fx
 ::inc-total
 (fn [db [_ username]]
   (go (let [response (<! (http/post "/clicks"
                                     {:with-credentials? false
                                      :query-params {:username username}}))]
         (if (= 200 (:status response))
           (rf/dispatch [::fetch-total])
           (rf/dispatch [::dec-counter]))))
   {}))

(rf/reg-event-fx
 ::fetch-total
 (fn [db _]
   (total-count-query!
    :on-success (fn [res] (rf/dispatch [::set-total (get-in res [:body :count])])))
   {}))
