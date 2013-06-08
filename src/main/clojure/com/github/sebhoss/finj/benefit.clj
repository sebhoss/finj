(ns com.github.sebhoss.finj.benefit
  (:require [com.github.sebhoss.finj.math :refer :all]))

(defn accumulation-factor [& {:keys [rate]}]
  (inc rate))

(defn final-due-value [& {:keys [payment accumulation-factor period]}]
  (* payment accumulation-factor
     (/ (dec (pow accumulation-factor period))
        (dec accumulation-factor))))

(defn final-immediate-value [& {:keys [payment accumulation-factor period]}]
  (* payment
     (/ (dec (pow accumulation-factor period))
        (dec accumulation-factor))))