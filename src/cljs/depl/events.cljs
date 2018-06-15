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

(rf/reg-event-db
 ::inc-counter
 (fn [db _]
   (update db :counter inc)))



;; I didn't do this part right...
(defn inc-backend-counter [cofx event]
  (go (let [response (<! (http/post "http://localhost:3000/clicks"
                                    {:with-credentials? false
                                     :query-params {:username "okyeah"}}))]
        (println (str "RESPONSE OF CALL " response)))))

(rf/reg-event-db
 ::inc-total
 inc-backend-counter)
