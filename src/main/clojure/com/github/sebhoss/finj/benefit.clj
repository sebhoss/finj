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

(defn present-due-value [& {:keys [payment accumulation-factor period]}]
  (* (/ payment
        (pow accumulation-factor (dec period)))
     (/ (dec (pow accumulation-factor period))
        (dec accumulation-factor))))

(defn present-immediate-value [& {:keys [payment accumulation-factor period]}]
  (* (/ payment
        (pow accumulation-factor period))
     (/ (dec (pow accumulation-factor period))
        (dec accumulation-factor))))

(defn perpetuity-due-value [& {:keys [payment accumulation-factor]}]
  (/ (* payment accumulation-factor)
     (dec accumulation-factor)))

(defn perpetuity-immediate-value [& {:keys [payment accumulation-factor]}]
  (/ payment (dec accumulation-factor)))

(defn period [& {:keys [payment accumulation-factor final-immediate-value present-immediate-value]
                 :or {final-immediate-value nil
                      present-immediate-value nil}}]
  {:pre [(or (not-nil? final-immediate-value)
             (not-nil? present-immediate-value))]}
  (if (nil? final-immediate-value)
    (* (/ 1 (ln accumulation-factor))
       (ln (/ payment
              (- payment (* present-immediate-value (dec accumulation-factor))))))
    (* (/ 1 (ln accumulation-factor))
       (ln (inc (* final-immediate-value (/ (dec accumulation-factor) payment)))))))
