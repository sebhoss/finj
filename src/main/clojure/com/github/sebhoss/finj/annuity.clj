(ns com.github.sebhoss.finj.annuity
  (:require [com.github.sebhoss.finj.math :refer :all]))

(defn final-due-value [& {:keys [payment accumulation-factor period]}]
  (* payment accumulation-factor
     (/ (dec (exp accumulation-factor period))
        (dec accumulation-factor))))

(defn final-immediate-value [& {:keys [payment accumulation-factor period]}]
  (* payment
     (/ (dec (exp accumulation-factor period))
        (dec accumulation-factor))))