(ns depl.handler
  (:require [compojure.core :refer [GET POST defroutes]]
            [depl.controllers.clicks :as c-clk]
            [compojure.route :refer [resources]]
            [ring.util.response :refer [resource-response]]
            [ring.middleware.reload :refer [wrap-reload]]
            [ring.middleware.params :as params]
            [ring.middleware.keyword-params :as kw-params]))

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (POST "/clicks" [_] c-clk/create)
  (resources "/"))

(def dev-handler (-> #'routes wrap-reload))

(def handler (-> routes
                 kw-params/wrap-keyword-params
                 params/wrap-params))
