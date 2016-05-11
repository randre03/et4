(ns mycx.handler
  (:require [mycx
             [parser :as parser]
             [view :refer [items-page]]]))

(defn list-users [req]
  ({:status 200
    :headers {"Content-Type" "text/html; charset=UTF-8"}
    :body (items-page (print parser/ds1))}))
