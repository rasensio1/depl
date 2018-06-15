(ns depl.views
  (:require
   [re-frame.core :as rf]
   [depl.subs :as subs]
   [depl.events :as events]))

(defn total-count []
  (let [total-count (rf/subscribe [::subs/total-count])]
    [:div [:h3 "Total Count"]
     [:p @total-count]])
  )

(defn counter []
  (let [count (rf/subscribe [::subs/counter])]
    [:div [:h2 "Counter"]
     [:h2 (str @count)]
     [:button {:on-click (fn [e]
                           (rf/dispatch [::events/inc-counter])
                           (rf/dispatch [::events/inc-total "some-name"]))}]]))

(defn home-panel []
  [:div.section
   [:h1 "My counter app"]
   [counter]
   [total-count]])


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
