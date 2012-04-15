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
        (uuid :id :primary-key)
        (varchar :type 100)
        (integer :version)))
  (create
   (tbl :eventlog
        (uuid :id :primary-key)
        (uuid :aggregate_id
              [:refer :aggregates :id :on-delete :set-null])
        (clob :data)
        (integer :version :auto-inc)

        (index [:aggregate_id]))))

(defn drop-event-store []
  (drop (table :eventlog))
  (drop (table :aggregates)))
