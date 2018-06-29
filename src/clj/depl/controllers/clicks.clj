(ns depl.controllers.clicks
  (:require [depl.models.click :as m-clk]))

(defn create [{{:keys [username]} :params}]
  (println "INCOMING CLICK!!! with name: " username)
  (m-clk/create! username)
  (-> (m-clk/click-count) first :count str))

(defn get-count [_]
  {:status 200
   :body (m-clk/click-count)})
