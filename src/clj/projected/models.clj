(ns projected.models
  (:use korma.core
        korma.db))

(defdb dev (postgres {:db "projected"
                      :user "dev"
                      :password ""}))

(defentity aggregates
  (entity-fields :id :type :version))

(defentity event-log
  (table :eventlog)
  (entity-fields :data :version)
  (belongs-to aggregates {:fk :aggregate_id}))
