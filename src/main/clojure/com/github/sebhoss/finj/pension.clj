(ns com.github.sebhoss.finj.pension
  "A pension is a contract for a fixed sum to be paid regularly to a person, typically following retirement from
   service.

   Definitions:
     * p                   - Rate per cent
     * accumulation-factor - Accumulation factor q = 1 + p/100
     * period              - Multiple of a pension period or point in time
     * payment             - Amount of a single pension payment

   References:
     * http://en.wikipedia.org/wiki/Pension"
  (:require [com.github.sebhoss.finj.math :refer :all]
            [com.github.sebhoss.finj.def :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]))

(defnk final-due-value
  "Calculates the final due value of a pension.

   Parameters:
     * payment             - Amount of pension payment per period
     * accumulation-factor - Accumulation factor per period
     * period              - Number of pension periods

   Examples:
     * (final-due-value :payment 100 :accumulation-factor 1.05 :period 5)
     * (final-due-value :payment 150 :accumulation-factor 1.1 :period 8)
     * (final-due-value :payment 180 :accumulation-factor 1.15 :period 12)"
  [:payment :accumulation-factor :period]
  {:pre [(number? payment)
         (number? accumulation-factor)
         (number? period)]}
  (* payment accumulation-factor
     (/ (dec (pow accumulation-factor period))
        (dec accumulation-factor))))

(defnk final-immediate-value
  "Calculates the final immediate value of a pension.

   Parameters:
     * payment             - Amount of pension payment per period
     * accumulation-factor - Accumulation factor per period
     * period              - Number of pension periods

   Examples:
     * (final-immediate-value :payment 100 :accumulation-factor 1.05 :period 5)
     * (final-immediate-value :payment 150 :accumulation-factor 1.1 :period 8)
     * (final-immediate-value :payment 180 :accumulation-factor 1.15 :period 12)"
  [:payment :accumulation-factor :period]
  {:pre [(number? payment)
         (number? accumulation-factor)
         (number? period)]}
  (* payment
     (/ (dec (pow accumulation-factor period))
        (dec accumulation-factor))))

(defnk present-due-value 
  "Calculates the present due value of a pension.

   Parameters:
     * payment             - Amount of pension payment per period
     * accumulation-factor - Accumulation factor per period
     * period              - Number of pension periods

   Examples:
     * (present-due-value :payment 100 :accumulation-factor 1.05 :period 5)
     * (present-due-value :payment 150 :accumulation-factor 1.1 :period 8)
     * (present-due-value :payment 180 :accumulation-factor 1.15 :period 12)"
  [:payment :accumulation-factor :period]
  {:pre [(number? payment)
         (number? accumulation-factor)
         (number? period)]}
  (* (/ payment
        (pow accumulation-factor (dec period)))
     (/ (dec (pow accumulation-factor period))
        (dec accumulation-factor))))

(defnk present-immediate-value
  "Calculates the present immediate value of a pension.

   Parameters:
     * payment             - Amount of pension payment per period
     * accumulation-factor - Accumulation factor per period
     * period              - Number of pension periods

   Examples:
     * (present-immediate-value :payment 100 :accumulation-factor 1.05 :period 5)
     * (present-immediate-value :payment 150 :accumulation-factor 1.1 :period 8)
     * (present-immediate-value :payment 180 :accumulation-factor 1.15 :period 12)"
  [:payment :accumulation-factor :period]
  {:pre [(number? payment)
         (number? accumulation-factor)
         (number? period)]}
  (* (/ payment
        (pow accumulation-factor period))
     (/ (dec (pow accumulation-factor period))
        (dec accumulation-factor))))

(defnk perpetuity-due-value
  "Calculates the perpetuity due value of a pension.

   Parameters:
     * payment             - Amount of pension payment per period
     * accumulation-factor - Accumulation factor per period
     * period              - Number of pension periods

   Examples:
     * (perpetuity-due-value :payment 100 :accumulation-factor 1.05)
     * (perpetuity-due-value :payment 150 :accumulation-factor 1.1)
     * (perpetuity-due-value :payment 180 :accumulation-factor 1.15)"
  [:payment :accumulation-factor]
  {:pre [(number? payment)
         (number? accumulation-factor)]}
  (/ (* payment accumulation-factor)
     (dec accumulation-factor)))

(defnk perpetuity-immediate-value
  "Calculates the perpetuity immediate value of a pension.

   Parameters:
     * payment             - Amount of pension payment per period
     * accumulation-factor - Accumulation factor per period
     * period              - Number of pension periods

   Examples:
     * (perpetuity-immediate-value :payment 100 :accumulation-factor 1.05)
     * (perpetuity-immediate-value :payment 150 :accumulation-factor 1.1)
     * (perpetuity-immediate-value :payment 180 :accumulation-factor 1.15)"
  [:payment :accumulation-factor]
  {:pre [(number? payment)
         (number? accumulation-factor)]}
  (/ payment (dec accumulation-factor)))

(defnk period
  "Calculates the number of pension periods needed for a given payment and accumulation factor per period to reach
   either the final or present immediate value.

   Parameters:
     * payment
     * accumulation-factor
     * final-immediate-value
     * present-immediate-value

   Examples:
     * (period :payment 100 :accumulation-factor 1.1 :present-immediate-value 500)
     * (period :payment 100 :accumulation-factor 1.1 :final-immediate-value 500)
     * (period :payment 230 :accumulation-factor 1.15 :present-immediate-value 1280)"
  [:payment :accumulation-factor
   :opt :final-immediate-value
        :present-immediate-value]
  {:pre [(number? payment)
         (number? accumulation-factor)
         (or (not-nil? final-immediate-value)
             (not-nil? present-immediate-value))]}
  (if (nil? final-immediate-value)
    (* (/ 1 (ln accumulation-factor))
       (ln (/ payment
              (- payment (* present-immediate-value (dec accumulation-factor))))))
    (* (/ 1 (ln accumulation-factor))
       (ln (inc (* final-immediate-value (/ (dec accumulation-factor) payment)))))))
