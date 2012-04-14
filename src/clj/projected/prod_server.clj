(ns projected.prod-server
  (:use [noir.core :only (defpage)]
        [projected.application-host :only (layout)])
  (:require [noir.server :as server]))

(defpage "/" [] (layout :production))

(defn -main [& [p]]
  (let [port (or p 9090)]
    (server/start port {:mode :prod
                        :ns 'projected})))
