(ns depl.models.click
  (:require [clojure.java.jdbc :as sql]
            [depl.pg.utils :as ut]))

(defn click-count []
  (sql/query ut/db-url ["select count(*) from clicks"]))

(defn create! [user]
  (sql/insert! ut/db-url :clicks [:username] [user]))

