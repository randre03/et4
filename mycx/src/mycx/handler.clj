(ns mycx.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :as route]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "Welcome to MyCX!")
  (GET "/widgets" [] (response [{:name "Widget 1"} {:name "Widget 2"}]))
  (route/resources "/")
  (route/not-found "Page Not Found"))

(def app
  (-> app-routes
      (wrap-json-body)
      (wrap-json-response)
      (wrap-defaults api-defaults)))
