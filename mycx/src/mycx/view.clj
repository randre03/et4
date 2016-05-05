(ns mycx.view
  (:require [hiccup.page :refer [html5]]
            [hiccup.core :refer [html h]]))

(defn items-page [items]
  (html5 {:lang :en}
         [:head
          [:title "MyCX (is not YourCX)"]
          [:meta {:name :viewport
                  :content "width=device-width, initial-scale=1.0"}]
          [:link {:href "/bootstrap/css/bootstrap.min.css"
                  :rel :stylesheet}]]
         [:body
          [:div.container]
          [:script {:src "https://ajax.googleapis.com/ajax/libs/jquery/2.2.0/jquery.min.js"}]
          [:script {:scr "/bootstrap/js/bootstrap.min.js"}]]))
