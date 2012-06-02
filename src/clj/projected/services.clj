(ns projected.services
  (:use noir.fetch.remotes
        [projected.support.command-bus :only [execute-command]])
  (:require projected.commands :as cmd))

(defremote create-project [name]
  (execute-command cmd/create-project name))

(defremote rename-project [originating-version name]
  (execute-command cmd/create-project originating-version name))
