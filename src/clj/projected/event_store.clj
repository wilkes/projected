(ns projected.event-store
  (:use korma.core))

(defentity aggregates
  (entity-fields :id :type :version)
  (has-many event-log {:fk :aggregate_id}))

(defentity event-log
  (table :eventlog)
  (entity-fields :data :version)
  (belongs-to aggregates {:fk :aggregate_id}))

(defn fetch-aggregate [id]
  (select aggregates
    (with event-log)
    (where {:id id})))

(defn save-events [events]
  (insert event-log (values events)))
