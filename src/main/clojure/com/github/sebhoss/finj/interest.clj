(ns com.github.sebhoss.finj.interest
  (:require [com.github.sebhoss.finj.def :refer :all]))

(defnk amount [:present-value :rate :period]
  (* present-value rate period))

(defnk future-value [:present-value :rate :period]
  (+ present-value
     (amount :present-value present-value
             :rate rate
             :period period)))

(defnk present-value [:future-value :rate :period]
  (/ future-value
     (inc (* rate period))))

(defnk rate [:future-value :present-value :period]
  (/ (- future-value present-value)
     (* present-value period)))

(defnk period [:future-value :present-value :rate]
  (/ (- future-value present-value)
     (* present-value rate)))

(defnk day [:future-value :present-value :rate :opt-def :days-per-year 360]
  (* days-per-year
     (/ (- future-value present-value)
        (* present-value rate))))
