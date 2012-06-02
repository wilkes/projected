(ns projected.support.event-store
  (:use korma.core
        projected.support.domain-helpers
        [korma.db :only (transaction)]))

(declare event-log)

(defentity aggregates
  (entity-fields :id :type :version)
  (has-many event-log {:fk :aggregate_id}))

(defentity event-log
  (table :eventlog)
  (entity-fields :data :version)
  (belongs-to aggregates {:fk :aggregate_id}))

(defn class-name->instance [cname]
  (.newInstance (Class/forName cname)))

(defn ->aggregate [db-result]
  (let [obj (class-name->instance (:type db-result))
        events (:event-log db-result)
        event-instance (fn [event]
                         (-> (class-name->instance (:type event))
                             (merge event)
                             (assoc :data (read-string (:data event)))))]
    (reduce (fn [obj event]
              (assoc (apply-change (event-instance event) obj)
                :version (:version event)))
            obj events)))

(defn fetch-aggregate [id originating-version]
  (let [db-result (first (select aggregates
                                 (with event-log)
                                 (where {:id id})))]
    (when-not db-result
      (throw (RuntimeException. "Unable to locate object.")))
    (when-not (= (:version db-result) originating-version)
      (throw (RuntimeException. "Concurrency Exception")))
    (->aggregate db-result)))

(defn save-events [repo]
  (transaction
    (doseq [[aggregate events] repo]
      (let [version (-> (select aggregates
                          (fields [:version])
                          (where {:id (:id aggregate)}))
                        first
                        :version)]
        (when-not version
          (insert aggregates (values {:id (:id aggregate)
                                      :version 0
                                      :type (.. aggregate getClass getName)})))
        (when-not (= version (:version aggregate))
          (throw (RuntimeException. "Concurrency Exception")))
        (let [readable-events (map (fn [event]
                                     (prn event)
                                     (update-in event [:data] pr-str))
                                   events)
              last-inserted (insert event-log (values (reverse readable-events)))]
          (update aggregates
            (where {:id (:id aggregate)})
            (set-fields {:version (:version last-inserted)})))))))
