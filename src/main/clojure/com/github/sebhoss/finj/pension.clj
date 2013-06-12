(ns com.github.sebhoss.finj.pension
  (:require [com.github.sebhoss.finj.math :refer :all]
            [com.github.sebhoss.finj.def :refer :all]
            [com.github.sebhoss.finj.common :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]))

(defnk final-due-value [:payment :accumulation-factor :period]
  (* payment accumulation-factor
     (/ (dec (pow accumulation-factor period))
        (dec accumulation-factor))))

(defnk final-immediate-value [:payment :accumulation-factor :period]
  (* payment
     (/ (dec (pow accumulation-factor period))
        (dec accumulation-factor))))

(defnk present-due-value [:payment :accumulation-factor :period]
  (* (/ payment
        (pow accumulation-factor (dec period)))
     (/ (dec (pow accumulation-factor period))
        (dec accumulation-factor))))

(defnk present-immediate-value [:payment :accumulation-factor :period]
  (* (/ payment
        (pow accumulation-factor period))
     (/ (dec (pow accumulation-factor period))
        (dec accumulation-factor))))

(defnk perpetuity-due-value [:payment :accumulation-factor]
  (/ (* payment accumulation-factor)
     (dec accumulation-factor)))

(defnk perpetuity-immediate-value [:payment :accumulation-factor]
  (/ payment (dec accumulation-factor)))

(defnk period [:payment
               :accumulation-factor
               :opt :final-immediate-value
                    :present-immediate-value]
  {:pre [(or (not-nil? final-immediate-value)
             (not-nil? present-immediate-value))]}
  (if (nil? final-immediate-value)
    (* (/ 1 (ln accumulation-factor))
       (ln (/ payment
              (- payment (* present-immediate-value (dec accumulation-factor))))))
    (* (/ 1 (ln accumulation-factor))
       (ln (inc (* final-immediate-value (/ (dec accumulation-factor) payment)))))))
