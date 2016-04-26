(ns mycx.parser
  (:require [cheshire.core :as cheshire]
            [clojure-csv.core :as csv-core]))

(def raw-data (slurp "/Users/randre03/Dropbox/Link to dev/media/et4/data/user.csv"))
(def parsed-data (csv-core/parse-csv raw-data))
(def parsed-data-vec (into [] parsed-data))

(def headings ["id" "fname" "lname" "username" "lat" "long" "gender" "age" "comments" "likes" "dislikes" "retweets"])

(defn get-user-record [user-id]
  (let [id (Integer. user-id)]
    (nth parsed-data-vec id)))

(defn csv-map [headings & user-record]
  (map #(zipmap (map keyword headings) %1) user-record))

;; Generate JSON
(defn roundtrip [user]
  (let [user-rec-number (get-user-record user)]
    (cheshire/generate-string (csv-map headings user-rec-number) {:pretty true})))

(defn formatter [obj] ;;FIXME NOT WORKING - Should remove the vector that surrounds the JSON
  (let [{:as all} obj]
    (print all)))
