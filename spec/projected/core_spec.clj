(ns projected.core-spec
  (:use speclj.core
        projected.core))

(describe "Truth"
  (it "is true"
    (should true))

  (it "is not false"
    (should-not false)))
