(ns projected.event-store
  (:use korma.core
        [korma.db :only (transaction)]))

(declare event-log)
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

(defn save-events [{:keys [id version] :as aggregate} events]
  (transaction
   (let [version (-> (select aggregates
                             (fields [:version])
                             (where {:id (:id aggregate)}))
                     first
                     :version)]
     (when-not version
       (insert aggregates (values (assoc aggregate :version 0))))
     (when-not (= version (:version aggregate))
        (throw (RuntimeException. "Concurrency Exception")))
     (let [last-inserted (insert event-log (values (reverse events)))]
       (update aggregates
               (where {:id id})
               (set-fields {:version (:version last-inserted)}))))))
