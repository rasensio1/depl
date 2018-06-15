(ns depl.pg.migration
  (:require [depl.pg.utils :as utils]
            [clojure.java.jdbc :as sql]))

(defn migrated? []
  (-> (sql/query utils/db-url [(str "select count(*) from information_schema.tables "
                                    "where table_name='clicks'")])
      first :count pos?))

(defn migrate []
  (when (not (migrated?))
    (print "Creating database structure...") (flush)
    (sql/db-do-commands utils/db-url
                        (sql/create-table-ddl
                         :clicks
                         [[:id :serial "PRIMARY KEY"]
                          [:username :varchar "NOT NULL"]
                          [:created_at :timestamp "NOT NULL" "DEFAULT CURRENT_TIMESTAMP"]])))
  (println " done"))

