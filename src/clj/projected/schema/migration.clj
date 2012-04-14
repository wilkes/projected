(ns projected.schema.migration
  (:refer-clojure :exclude [alter drop
                            bigint boolean char double float time])
  (:use (lobos [migration :only [defmigration]]
               core
               schema)
        projected.schema.config
        projected.schema.helpers))

(defn make-event-store []
  (create
   (tbl :aggregates
        (varchar :type 100)
        (integer :version)))
  (create
   (tbl :eventlog
        (refer-to :aggregates)
        (clob :data)
        (integer :version))))

(defn drop-event-store []
  (drop (table :eventlog))
  (drop (table :aggregates)))
