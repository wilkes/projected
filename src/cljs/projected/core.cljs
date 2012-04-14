(ns projected.core
  (:require [clojure.browser.repl :as repl]
            [one.dispatch :as dispatch]))

(defn ^:export main []
  (dispatch/fire :init))

(defn ^:export repl []
  (repl/connect "http://localhost:9000/repl"))
