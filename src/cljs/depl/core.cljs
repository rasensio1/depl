(ns depl.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [depl.events :as events]
   [depl.routes :as routes]
   [depl.views :as views]
   [depl.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (re-frame/dispatch [::events/fetch-total])
  (dev-setup)
  (mount-root))
