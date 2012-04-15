(ns projected.event-bus
  (require [lamina.core :as lamina]))

(defprotocol EventBus
  (publish [this event])
  (subscribe [this event-name handler]))


;; In memory impl
(extend-type lamina.core.channel.Channel
  EventBus
  (publish [ch event]
    (lamina/enqueue ch event))
  (subscribe [ch event-name handler]
    (lamina/receive-all (lamina/filter* #(= (:type %) event-name)
                                        ch)
                        handler)))

(defonce ^:dynamic *event-bus* (atom nil))
(defonce ^:dynamic  *subscriptions* (atom #{}))

(defn in-process-bus []
  (swap! *event-bus* lamina/channel)
  (doseq [[event-name subscriber] @*subscriptions*]
    (subscribe @*event-bus* event-name subscriber)))


(defn add-subscriber [event-name subscriber]
  (swap! *subscriptions* conj [event-name subscriber]))

(defn publish-event [event]
  (publish @*event-bus* event))
