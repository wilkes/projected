(ns projected.domain
  (:use [projected.support.command-bus :only [apply-event!]]
        projected.support.domain-helpers))

(defrecord Project [])

(defevent ProjectCreated [event project]
  (merge (assoc project :id (:id event))
         (:data event)))

(defevent ProjectRenamed [event project]
  (assoc project :name (-> event :data :name)))

(defn create-project [name]
  (if name
    (apply-event! (->Project) ProjectCreated {:name name})
    (throw (RuntimeException. "spooooon!"))))

(defn rename-project [project name]
  (if name
    (apply-event! project ProjectRenamed {:name name})
    (throw (RuntimeException. "spooooon!"))))
