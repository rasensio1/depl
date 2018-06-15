(ns depl.pgres.utils)

(def db-url
  (or (System/getenv "DATABASE_URL")
      "postgresql://localhost:5432/depl"))
