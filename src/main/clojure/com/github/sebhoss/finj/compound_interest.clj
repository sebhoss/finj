(ns com.github.sebhoss.finj.compound-interest
  (:require [com.github.sebhoss.finj.math :refer :all]
            [clojure.math.numeric-tower :refer :all]))

(defn future-value [& {:keys [present-value rate period]}]
  (* present-value (expt (inc rate) period)))

(defn amount [& {:keys [present-value rate period]}]
  (- (future-value :present-value present-value :rate rate :period period) present-value))

(defn present-value [& {:keys [future-value rate period]}]
  (/ future-value
     (expt (inc rate) period)))

(defn yield [& {:keys [future-value present-value period]}]
  (* 100 (dec (root period (/ future-value present-value)))))

(defn period [& {:keys [future-value present-value rate]}]
  (/ (- (Math/log future-value) (Math/log present-value))
     (Math/log (inc rate))))

(defn mixed-value [& {:keys [present-value rate start-part period end-part]}]
  (* present-value (inc (* rate start-part))
                   (expt (inc rate) period)
                   (inc (* rate end-part))))

(defn in-year-value [& {:keys [present-value rate period in-year-period]}]
  (* present-value (expt (inc (/ rate in-year-period)) (* period in-year-period))))

(defn relative-in-year-rate [& {:keys [rate in-year-period]}]
  (/ rate in-year-period))

(defn conformal-in-year-rate [& {:keys [rate in-year-period]}]
  (dec (root in-year-period (inc rate))))

(defn effective-in-year-rate [& {:keys [relative-in-year-rate in-year-period]}]
  (dec (expt (inc relative-in-year-rate) in-year-period)))

(defn continuous-value [& {:keys [present-value rate period]}]
  (* present-value (expt (Math/E) (* rate period))))

(defn intensity [& {:keys [rate]}]
  (Math/log (inc rate)))

(defn rate [& {:keys [intensity]}]
  (dec (expt (Math/E) intensity)))