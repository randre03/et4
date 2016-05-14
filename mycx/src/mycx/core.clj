(ns mycx.core
  (:require [compojure
             [core :refer [ANY defroutes GET]]
             [route :as route]]
            [json-html.core :as jhtml]
            [mycx.handler :as handler]
            [mycx.parser :as parser]
            [ring.handler.dump :refer [handle-dump]]
            [ring.middleware
             [defaults :refer [api-defaults wrap-defaults]]
             [file-info :refer [wrap-file-info]]
             [json :refer [wrap-json-body wrap-json-response]]
             [params :refer [wrap-params]]
             [resource :refer [wrap-resource]]]))

(defn all-users-json [req]
  {:status 200
   :body (parser/everyone-by-age)
   :headers {"Content-Type" "text/html; charset=UTF-8"}})

(defn single-user-data [req]
  (let [user-id (get-in req [:route-params :id])]
    {:status 200
     :body (jhtml/json->html (parser/single-user-json user-id))
     :headers {"Content-Type" "text/html; charset=UTF-8"}}))

(defn age-data [req]
  (let [min (get-in req [:route-params :min_age])
        max (get-in req [:route-params :max-age])]
    {:status 200
     :body (parser/json-age-data min max)
     :headers {"Content-Type" "text/html; charset=UTF-8"}}))

(defn within-radius [req]
  (let [lat (get-in req [:route-params :lat])
        lon (get-in req [:route-params :lon])]
    {:status 200
     :body (jhtml/json->html (parser/user-radius lat lon))
     :headers {"Content-Type" "text/html; charset=UTF-8"}}))

(defroutes routes
  (GET "/" [] "Hey MyCX - What's Happppppng?")
  (GET "/users" [] all-users-json)
  (GET "/users/:id" [] single-user-data)
  (GET "/users/?min_age=min&max_age=max" [] age-data)
  (GET "/users/age?min_age=min&max_age=max" [] age-data)
  (GET "/users?loc=lat=lat&lon=lon" [] within-radius)

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
