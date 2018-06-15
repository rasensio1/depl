(ns depl.controllers.clicks
  (:require [depl.models.click :as m-clk]))

(defn create [username]
  (m-clk/create! username))
