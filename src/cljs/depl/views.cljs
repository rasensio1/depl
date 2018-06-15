(ns depl.views
  (:require
   [re-frame.core :as rf]
   [depl.subs :as subs]
   [depl.events :as events]))

(defn counter []
  (let [count (rf/subscribe [::subs/counter])]
    [:div [:h2 "Counter"]
     [:h2 (str @count)]
     [:button {:on-click #(rf/dispatch [::events/inc-counter])}]]))

(defn home-panel []
  [:div.section
   [:h1 "My counter app"]
   [counter]])


;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (rf/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
