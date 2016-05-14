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
  (let [mid-id (Integer. id)
        new-id (dec mid-id)]
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
  (assumed to be 5.0 miles [8.04672 km per Google]) around a given point.
  Returns a map with 4 kv pairs:
  :maxlatitude, :minlatitude, :maxlongitude, minlongitude"
  [lat lon]
  (haversine/neighborhood {:latitude lat :longitude lon :distance-from 8.04672}))

(def user-location-data
  "Returns the entire dataset, limited to :id, :fname, :lname, :lat and :long."
  (let [raw-data (i/$ [:id :fname :lname :lat :long] ds1)
        dataset (i/$order :long :asc raw-data)]
    dataset))

(defn- user-radius-longitude
  "Returns a matrix of all users within the radius based upon longitude."
  [arg-lat arg-lon]
  (let [location-map (max-radius arg-lat arg-lon)
        maxlon (:maxlongitude location-map)
        minlon (:minlongitude location-map)]
    (i/$where {:long {:$lte maxlon :$gte minlon}} user-location-data)))

(defn- user-radius-latitude
  "Returns a matrix of all users within the radius based upon latitude."
  [arg-lat arg-lon]
  (let [location-map (max-radius arg-lat arg-lon)
        maxlat (:maxlatitude location-map)
        minlat (:minlatitude location-map)]
    (i/$where {:lat {:$lte maxlat :$gte minlat}} user-location-data)))

(defn user-radius
  "Combines the matrices of people that live close-by according to Latitude and
  those that live close-by according to longitude into a seq."
  [arg-lat arg-lon]
  (let [lat-matrix (user-radius-latitude arg-lat arg-lon)
        lon-matrix (user-radius-longitude arg-lat arg-lon)
        latvect (i/to-vect lat-matrix)
        lonvect (i/to-vect lon-matrix)
        new-vect (conj latvect lonvect)
        flat-seq (flatten new-vect)]
    (-> flat-seq
        frequency-map
        neighbors)))

(defn- frequency-map
  "Takes the seq from user-radius, changes it to a map where values indicate the
  number of times that data-point shows up (either 1 time, on only one list or 2
  to show up on both lists)."
  [coll]
  (let [gp (group-by identity coll)]
    (zipmap (keys gp) (map #(count (second %)) gp))))

(defn- neighbors
  "Takes the data from frequency-map function and reduces it down to just those
  individuals who have shown up on both the latitude and longitude lists (hence v=2 below) and
  therefore arriving at the people that are within the indicated radius of the given
  coordinates."
  [coll]
  (select-keys coll (for [[k v] coll :when (= v 2)] k)))

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
