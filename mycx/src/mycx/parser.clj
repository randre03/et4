(ns mycx.parser
  (:require [cheshire.core :as cheshire]
            [incanter
             [core :as i]
             [io :as incant-io]]))

;;;;;;;;;;;;;;;;;;;;
;; Retrieve Data

(defonce ds1 (incant-io/read-dataset "/Users/randre03/Dropbox/Link to dev/media/et4/data/user.csv" :header true :delim \,))

;;;;;;;;;;;;;;;;;;;;
;; Parse Data

(defn get-user
  "Returns all data associated with the user with id number id."
  [id]
  (let [new-id (Integer. id)]
    (i/$ new-id :all ds1)))

(defn everyone-by-age
  "Returns the entire dataset with all data-points sorted by id from min and max."
  []
  (drop 1 (i/$order :id :asc ds1)))

(defn- data-by-age
  "Returns a subset of the entire dataset with all data-points for those users
  between ages min and max (inclusive)."
  [min max]
  (let [new_min (Integer. min)
        new_max (Integer. max)]
    (i/$where {:age {:$gte new_min :$lte new_max}} ds1)))

(defn users-by-age
  "Reduces the data-by-age dataset to just id, fname, lname and age, ordered
  by age - lowest to highest."
  [min max]
  (let [dataset (data-by-age min max)
        sub-set (i/$ [:id :fname :lname :age] dataset)
        ordered-set (i/$order :age :asc sub-set)]
    ordered-set))

;; (defn lng-lat [lng lat]
;;   )


;;;;;;;;;;;;;;;;;;;;
;; Convert to JSON - User

(def headings ["id" "fname" "lname" "username" "lat" "long" "gender" "age" "comments" "likes" "dislikes" "retweets"])

(defn single-user-json [user]
  (cheshire/generate-string (zipmap headings (get-user user))))

;; Convert to JSON - Users by Age

(defn json-age-data [dataset]
  (if (seq dataset)
    (for [data dataset]
      #{"id:"     (i/$ :id data)
        "fname:"  (i/$ :fname data)
        "lname:"  (i/$ :lname data)
        "age:"     (i/$ :age data)})))



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

;; (defn- total-rows
;;   "Calculates the number of rows in the Age matrix."
;;   [dataset]
;;   (first (i/dim dataset)))

;; (defn- json-by-row [dataset]
;;   (i/$ (dec (total-rows dataset)) :all dataset))
