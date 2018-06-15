(ns depl.controllers.clicks
  (:require [depl.models.click :as m-clk]))

(defn create [{{:keys [username]} :params}]
  (m-clk/create! username))
