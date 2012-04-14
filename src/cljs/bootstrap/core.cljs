(ns bootstrap.core
  (:use [jayq.core :only ($)]
        [jayq.util :only (clj->js)]
        [crate.core :only (html)])
  (:require [jayq.core :as j]
            [goog.string :as gstring])
  (:require-macros [crate.macros :as crate]))

(defn tooltip
  ([$elem]
      (.tooltip $elem))
  ([$elem opts]
     (.tooltip $elem (clj->js opts))))

(defn popover
  ([$elem]
      (.popover $elem))
  ([$elem opts]
     (.popover $elem (clj->js opts))))
