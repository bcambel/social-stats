(ns social-stats.core
  (:require
    [clojure.string :as s]
    [clj-http.client :as client]
    [cheshire.core :as json]
    [taoensso.timbre :as timbre]
    [clojure.java.io :as io])
  (:gen-class)
  (:import (com.fasterxml.jackson.core JsonParseException)))

(timbre/refer-timbre)

(def file-to-read "/Users/bahadircambel/Documents/Quantcast-Top-Million.txt")

(def twitter-url "http://urls.api.twitter.com/1/urls/count.json")
(def facebook-url "http://graph.facebook.com")

(defn tweets
  [url]
  (let [raw-resp (client/get
                   twitter-url
                   {:query-params { "url" url} } )]
    (if-let [json-result
          (try
            (json/parse-string (get raw-resp :body))
            (catch JsonParseException jpe (error jpe)))
          ]
      (get json-result "count"))))


(defn facebook-popularity
  [url]
  (if-let [raw-resp (try
                      (client/get
                           (format "%s/http://%s" facebook-url url))
                      (catch Exception e)) ]
    (if-let [json-result
             (try
               (json/parse-string (get raw-resp :body))
               (catch JsonParseException jpe (error jpe)))
             ]
      (or (get json-result "shares")
          (get json-result "likes")))))

(defn read-file
  [file]
  (with-open [rdr (io/reader file)]
    (doseq [line (line-seq rdr)]
      (let [parts (s/split line #"\t")
            url (last parts)]
        (if-not (= url "Hidden profile")
          (let [tweet-count (tweets url)
                fb-count (facebook-popularity url)
                ]
            (info (format "%s: [%s][%s]" url tweet-count fb-count))))))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!")
  ;(read-file (first args))
  (read-file file-to-read))
