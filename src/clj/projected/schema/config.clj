(ns projected.schema.config
  (:use lobos.connectivity))

(def db
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :user "wilkes"
   :password ""
   :subname "//localhost:5432/projected"})

(open-global db)
