(ns mycx.core
  (:require [compojure.core :refer [ANY GET POST PUT DELETE defroutes]]
            [compojure.route :as route]
            [ring.util.response :refer [resource-response response]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults site-defaults]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.file-info :refer [wrap-file-info]]
            [ring.middleware.params :refer [wrap-params]]
            [mycx.parser :as parser]
            [mycx.handler :as handler]
            [ring.handler.dump :refer [handle-dump]]
            [json-html.core :as jhtml]))

(defn user-data [req]
  (let [user-id (get-in req [:route-params :id])]
    {:status 200
     :body (jhtml/json->html (parser/single-user-json user-id))
     :headers {"Content-Type" "text/html; charset=UTF-8"}}))

(defn age-data [req]
  (let [min (get-in req [:route-params :min_age])
        max (get-in req [:route-params :max-age])]
    {:status 200
     :body (jhtml/json->html (parser/users-by-age min max))
     :headers {"Content-Type" "text/html; charset=UTF-8"}}))

(defroutes routes
  (GET "/" [] "Hey MyCX - What's Happppppng?")
  (GET "/users/:id" [] user-data)
  (GET "/users?age=min_age=min&max_age=max" [] age-data)

  (ANY "/request" [] handle-dump)
  (route/not-found "404 Page Not Found"))

(def app
  (-> routes
      (wrap-file-info)
      (wrap-resource "static")
      (wrap-params)
      (wrap-json-body)
      (wrap-json-response)
      (wrap-defaults api-defaults)))
