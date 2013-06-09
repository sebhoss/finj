(ns com.github.sebhoss.finj.compound-interest
  (:require [com.github.sebhoss.finj.math :refer :all]
            [com.github.sebhoss.finj.zombie :refer :all]))

(defnk future-value [:present-value :rate :period]
  (* present-value (pow (inc rate) period)))

(defnk amount [:present-value :rate :period]
  (- (future-value
       :present-value present-value
       :rate rate
       :period period)
     present-value))

(defnk present-value [:future-value :rate :period]
  (/ future-value
     (pow (inc rate) period)))

(defnk yield [:future-value :present-value :period]
  (* 100 (dec (root period (/ future-value present-value)))))

(defnk period [:future-value :present-value :rate]
  (/ (- (ln future-value) (ln present-value))
     (ln (inc rate))))

(defnk mixed-value [:present-value :rate :start-part :period :end-part]
  (* present-value (inc (* rate start-part))
                   (pow (inc rate) period)
                   (inc (* rate end-part))))

(defnk in-year-value [:present-value :rate :period :in-year-period]
  (* present-value (pow (inc (/ rate in-year-period)) (* period in-year-period))))

(defnk relative-in-year-rate [:rate :in-year-period]
  (/ rate in-year-period))

(defnk conformal-in-year-rate [:rate :in-year-period]
  (dec (root in-year-period (inc rate))))

(defnk effective-in-year-rate [:relative-in-year-rate :in-year-period]
  (dec (pow (inc relative-in-year-rate) in-year-period)))

(defnk continuous-value [:present-value :rate :period]
  (* present-value (exp (* rate period))))

(defnk intensity [:rate]
  (ln (inc rate)))

(defnk rate [:intensity]
  (dec (exp intensity)))
