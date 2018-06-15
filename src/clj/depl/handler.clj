(ns depl.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [depl.controllers.clicks :as c-clk]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (POST "/clicks" [username] (c-clk/create username))
  (resources "/"))

(def dev-handler (-> #'routes wrap-reload))

(def handler routes)
