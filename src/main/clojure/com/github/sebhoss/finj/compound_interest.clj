(ns com.github.sebhoss.finj.compound-interest
  (:require [com.github.sebhoss.finj.math :refer :all]))

(defn future-value [& {:keys [present-value rate period]}]
  (* present-value (pow (inc rate) period)))

(defn amount [& {:keys [present-value rate period]}]
  (- (future-value :present-value present-value :rate rate :period period) present-value))

(defn present-value [& {:keys [future-value rate period]}]
  (/ future-value
     (pow (inc rate) period)))

(defn yield [& {:keys [future-value present-value period]}]
  (* 100 (dec (root period (/ future-value present-value)))))

(defn period [& {:keys [future-value present-value rate]}]
  (/ (- (log future-value) (log present-value))
     (log (inc rate))))

(defn mixed-value [& {:keys [present-value rate start-part period end-part]}]
  (* present-value (inc (* rate start-part))
                   (pow (inc rate) period)
                   (inc (* rate end-part))))

(defn in-year-value [& {:keys [present-value rate period in-year-period]}]
  (* present-value (pow (inc (/ rate in-year-period)) (* period in-year-period))))

(defn relative-in-year-rate [& {:keys [rate in-year-period]}]
  (/ rate in-year-period))

(defn conformal-in-year-rate [& {:keys [rate in-year-period]}]
  (dec (root in-year-period (inc rate))))

(defn effective-in-year-rate [& {:keys [relative-in-year-rate in-year-period]}]
  (dec (pow (inc relative-in-year-rate) in-year-period)))

(defn continuous-value [& {:keys [present-value rate period]}]
  (* present-value (pow e (* rate period))))

(defn intensity [& {:keys [rate]}]
  (log (inc rate)))

(defn rate [& {:keys [intensity]}]
  (dec (pow e intensity)))
