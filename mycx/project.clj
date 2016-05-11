(defproject mycx "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [compojure "1.4.0"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.2.0"]
                 [clojure-csv/clojure-csv "2.0.1"]
                 [haversine "0.1.1"]
                 [hiccup "1.0.5"]
                 [incanter "1.5.7"]
                 [json-html "0.3.9"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler mycx.core/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
