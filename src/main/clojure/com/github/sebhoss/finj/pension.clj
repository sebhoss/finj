;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.

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
            [com.github.sebhoss.def :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]))

(defnk final-due-value
  "Calculates the final due value of a pension.

   Parameters:
     * payment             - Amount of pension payment per period
     * accumulation-factor - Accumulation factor per period
     * period              - Number of pension periods

   Examples:
     * (final-due-value :payment 100 :accumulation-factor 1.05 :period 5)
       => 580.1912812500002
     * (final-due-value :payment 150 :accumulation-factor 1.1 :period 8)
       => 1886.921536500001
     * (final-due-value :payment 180 :accumulation-factor 1.15 :period 12)
       => 6003.345145553718"
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
       => 552.5631250000001
     * (final-immediate-value :payment 150 :accumulation-factor 1.1 :period 8)
       => 1715.3832150000007
     * (final-immediate-value :payment 180 :accumulation-factor 1.15 :period 12)
       => 5220.300126568451"
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
       => 454.5950504162361
     * (present-due-value :payment 150 :accumulation-factor 1.1 :period 8)
       => 880.2628226539398
     * (present-due-value :payment 180 :accumulation-factor 1.15 :period 12)
       => 1122.0681327424015"
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
       => 432.947667063082
     * (present-immediate-value :payment 150 :accumulation-factor 1.1 :period 8)
       => 800.2389296853996
     * (present-immediate-value :payment 180 :accumulation-factor 1.15 :period 12)
       => 975.7114197760012"
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
       => 2099.999999999998
     * (perpetuity-due-value :payment 150 :accumulation-factor 1.1)
       => 1649.9999999999986
     * (perpetuity-due-value :payment 180 :accumulation-factor 1.15)
       => 1380.0000000000007"
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
       => 1999.9999999999982
     * (perpetuity-immediate-value :payment 150 :accumulation-factor 1.1)
       => 1499.9999999999986
     * (perpetuity-immediate-value :payment 180 :accumulation-factor 1.15)
       => 1200.0000000000007"
  [:payment :accumulation-factor]
  {:pre [(number? payment)
         (number? accumulation-factor)]}
  (/ payment (dec accumulation-factor)))

(defnk period
  "Calculates the number of pension periods needed for a given payment and accumulation factor per period to reach
   either the final or present immediate value.

   Parameters:
     * payment                 - Amount of pension payment per period
     * accumulation-factor     - Accumulation factor per period
     * final-immediate-value   - Wanted final immediate value (optional)
     * present-immediate-value - Wanted present immediate value (optional)

   Examples:
     * (period :payment 100 :accumulation-factor 1.1 :present-immediate-value 500)
       => 7.272540897341723
     * (period :payment 100 :accumulation-factor 1.1 :final-immediate-value 500)
       => 4.254163709905892
     * (period :payment 230 :accumulation-factor 1.15 :present-immediate-value 1280)
       => 12.882571024691392"
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
