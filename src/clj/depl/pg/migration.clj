(ns depl.pg.migration
  (:require [clojure.java.jdbc :as sql]))

(def db-url
  (or (System/getenv "DATABASE_URL")
      "postgresql://localhost:5432/depl"))

(defn migrated? []
  (-> sql/query [(str "select count(*) from information_schema.tables "
                      "were table_name='clicks'")]
      :first :count pos?))

(defn migrate []
  (when (not (migrated?))
    (print "Creating database structure...") (flush)
    (sql/db-do-commands db-url
                        (sql/create-table-ddl
                         :clicks
                         [[:id :serial "PRIMARY KEY"]
                          [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]])))
  (println " done"))

