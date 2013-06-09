(ns com.github.sebhoss.finj.predicate
  (:require [com.github.sebhoss.finj.math :refer :all]))

(def not-nil? (comp not nil?))

(defprotocol Fuzzy
  (fuzzy-eq? [x y epsilon] "(fuzzy-eq? x y epsilon) returns true if the difference between x and y is less than epsilon")
  (fuzzy-zero? [x epsilon] "(fuzzy-zero? x epsilon) returns true if x is within epsilon of zero"))
(extend-protocol Fuzzy
  Number
    (fuzzy-eq? [x y epsilon]
      (< (abs (- x y)) epsilon))
    (fuzzy-zero? [x epsilon]
      (fuzzy-eq? x 0 epsilon)))
