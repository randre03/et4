(ns mycx.handler
  (:require [compojure.core :refer [ANY GET POST PUT DELETE defroutes]]
            [compojure.route :as [route]]
            [mycx.view :refer [items-page]]
            [mycx.parser :as [parser]]))

(defn list-users [req]
  {:status 200
   :headers {"Content-Type" "text/html; charset=UTF-8" "Location" "/users"}
   :body (items-page (print parser/raw-data))})
