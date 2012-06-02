(ns projected.support.domain-helpers)

(defprotocol Eventish
  (apply-change [event aggregate]))

(defmacro defevent [name args & body]
  `(defrecord ~name []
       Eventish
       (apply-change ~args
         ~@body)))
