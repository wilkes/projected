(ns projected.support.command-bus
  (:use [projected.support.event-store :only [save-events]]
        [projected.support.event-bus :only [publish-event]]
        [projected.support.domain-helpers :only [apply-change]]))

(def ^:dynamic *domain-repository* nil)

(defn uuid [instance]
  (merge instance {:id (java.util.UUID/randomUUID)}))

(defn domain-repository-events []
  @*domain-repository*)

(defn add-event-to-domain-repository! [aggregate event]
  (swap! *domain-repository* update-in [aggregate]
         (fn [v] (into (vec v) [event]))))

(defn commit-repository! []
  (let [events (domain-repository-events)]
    (save-events events)
    #_(doseq [event events]
      (publish-event event))))

(defmacro with-domain-repository [& body]
  `(binding [*domain-repository* (atom {})]
     ~@body))

(defmacro unit-of-work [& body]
  `(binding [*domain-repository* (atom {})]
     (let [result# ~@body]
       (commit-repository!)
       result#)))

(defn execute-command [cmd & args]
  (unit-of-work
    (apply cmd args)))

(defn- make-event [event-type event-data]
  (merge (.newInstance event-type)
         (uuid {:type (.getName event-type)
                :data event-data})))

(defn apply-event!
  [aggregate event-type event-data]
  (let [event (make-event event-type event-data)
        aggregate (apply-change event aggregate)]
    (add-event-to-domain-repository! aggregate
                                     (assoc event :aggregate_id (:id aggregate)))
    aggregate))
