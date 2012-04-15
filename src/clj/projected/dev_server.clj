(ns projected.dev-server
  (:use [noir.core :only (defpage)]
        [korma.db :only (defdb postgres)]
        [projected.application-host :only (layout)])
  (:require [noir.response :as response]
            [noir.server :as server]))

(defdb dev (postgres {:db "projected"
                      :user "dev"
                      :password ""}))

(defpage "/" [] (response/redirect "/development"))
(defpage "/development" [] (layout :development))
(defpage "/production" [] (layout :production))

(defn run-server [& [p]]
  (let [port (or p 8080)]
    (server/start port {:mode :dev
                        :ns 'wma})))

(comment
  (do
    (use 'projected.dev-server)
    (run-server)))
