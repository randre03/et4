(ns mycx.parser
  (:require [cheshire.core :as cheshire]
            [haversine.core :as haversine]
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

(defn- users-by-age
  "Reduces the data-by-age dataset to just id, fname, lname and age, ordered
  by age - lowest to highest."
  [min max]
  (let [dataset (data-by-age min max)
        sub-set (i/$ [:id :fname :lname :age] dataset)
        ordered-set (i/$order :age :asc sub-set)]
    ordered-set))

(defn- max-radius
  "Calculates the range of latitude and longitude for distance
  (assumed to be 5.0 miles [8.06 km]) around a given point.
  Returns a map of 4 values:
  :maxlatitude, :minlatitude, :maxlongitude, minlongitude"
  [lat lon]
  (haversine/neighborhood {:latitude lat :longitude lon :distance-from 8.06}))

(def user-location-data
  "Returns the entire dataset, limited to :id, :fname, :lname, :lat and :long."
  (let [raw-data (i/$ [:id :fname :lname :lat :long] ds1)
        dataset (i/$order :long :asc raw-data)]
    dataset))

(defn user-radius-longitude
  "Returns a matrix of all users within the radius based upon longitude."
  [arg-lat arg-lon]
  (let [location-map (max-radius arg-lat arg-lon)
        maxlon (:maxlongitude location-map)
        minlon (:minlongitude location-map)]
    (i/$where {:long {:$lte maxlon :$gte minlon}} user-location-data)))

(defn user-radius-latitude
  "Returns a matrix of all users within the radius based upon latitude."
  [arg-lat arg-lon]
  (let [location-map (max-radius arg-lat arg-lon)
        maxlat (:maxlatitude location-map)
        minlat (:minlatitude location-map)]
    (i/$where {:lat {:$lte maxlat :$gte minlat}} user-location-data)))
;;;;;;;;;;;;;;;;;;;;
;; Convert to JSON

(def headings ["id" "fname" "lname" "username" "lat" "long" "gender" "age" "comments" "likes" "dislikes" "retweets"])

  (defn single-user-json
    "Returns all available data about user formatted in json."
    [user]
    (cheshire/generate-string (zipmap headings (get-user user))))

  (defn json-age-data
    "Returns JSON-formatted group of user-data (:fname, :lname, :age, :id), by age in ascending order."
    [min max]
    (let [dataset (users-by-age min max)
          reformatted (seq dataset)
          revised-data (drop 1 reformatted)]
      revised-data))



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
