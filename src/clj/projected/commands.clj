(ns projected.commands
  (:require [projected.domain :as dom]
            [projected.support.command-bus :as cb]
            [projected.support.event-store :as store]))

(defn create-project [name]
  (cb/unit-of-work
   (dom/create-project name)))

(defn rename-project [id originating-version name]
  (cb/unit-of-work
   (let [project (store/fetch-aggregate id originating-version)]
     (dom/rename-project project name))))
