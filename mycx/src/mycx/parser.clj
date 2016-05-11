(ns mycx.parser
  (:require [incanter.core :as i]
            [incanter.io :as incant-io]
            [cheshire.core :as cheshire]
            [cheshire.core :as cheshire]
            [clojure-csv.core :as csv-core]))

;;;;;;;;;;;;;;;;;;;;
;; Retrieve Data

(defonce ds1 (incant-io/read-dataset "/Users/randre03/Dropbox/Link to dev/media/et4/data/user.csv" :header true :delim \,))

;;;;;;;;;;;;;;;;;;;;
;; Parse Data

(defn get-user [id]
  (let [new-id (Integer. id)]
    (i/$ new-id :all ds1)))

(defn users-by-age [min max]
  (let [new_min (Integer. min)
        new_max (Integer. max)]
    (i/$where {:age {:$gte new_min :$lte new_max}} ds1)))

;; (defn lng-lat [lng lat]
;;   )


;;;;;;;;;;;;;;;;;;;;
;; Convert to JSON - User

(def headings ["id" "fname" "lname" "username" "lat" "long" "gender" "age" "comments" "likes" "dislikes" "retweets"])

(defn single-user-json [user]
  (cheshire/generate-string (zipmap headings (get-user user))))

;; ;; Convert to JSON - Users by Age

;; ;; Calculate number of rows in the Age Matrix
;; (defn- total-rows [dataset]
;;   (first (i/dim dataset)))

;; (defn json-by-row [dataset]
;;   (i/$ (total-rows dataset) :all dataset))



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Old Parsing Code

;; (def parsed-data (csv-core/parse-csv ds1))
;; (def parsed-data-vec (into [] parsed-data))

;; (defn get-user-record [user-id]
;;   (let [id (Integer. user-id)]
;;     (nth parsed-data-vec id)))

;; (defn csv-map [headings & user-record]
;;   (map #(zipmap (map keyword headings) %1) user-record))

;; ;; Generate JSON
;; (defn roundtrip [user]
;;   (let [user-rec-number (get-user-record user)]
;;     (cheshire/generate-string (csv-map headings user-rec-number) {:pretty true})))
