(ns projected.schema.helpers
  (:refer-clojure :exclude [bigint boolean char double float time])
  (:use lobos.schema))

(def-simple-typed-columns
  uuid)

(defn timestamps [table]
  (-> table
      (timestamp :updated_on :time-zone)
      (timestamp :created_on (default (now)) :time-zone)))

(defmacro tbl [name & elements]
  `(-> (table ~name)
       (timestamps)
       ~@(reverse elements)))
