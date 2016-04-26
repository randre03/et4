(ns mycx.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :as route]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults site-defaults]]
            [mycx.parser :as parser]
            [ring.handler.dump :refer [handle-dump]]))

(defn user-data [req]
  (let [user-id (get-in req [:route-params :id])]
    {:status 200
     :body (parser/roundtrip user-id)
     :headers {}}))

(defn yo [req]
  (let [name (get-in req [:route-params :name])]
    {:status 200
     :body (str "Yo, "name"!")
     :headers {}}))

(defroutes app-routes
  (GET "/" [] "<h1>Welcome to MyCX!</h1>")
  (GET "/widgets" [] (response [{:name "Widget 1"} {:name "Widget 2"}]))
  (GET "/user/:id" [] user-data)

  (GET "/request" [] handle-dump)

  (route/resources "/")
  (route/not-found "Page Not Found"))

(def app
  (-> app-routes
      (wrap-json-body)
      (wrap-json-response)
      (wrap-defaults api-defaults)))
