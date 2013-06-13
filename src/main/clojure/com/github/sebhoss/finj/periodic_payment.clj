(ns com.github.sebhoss.finj.periodic-payment
  "Periodic payment pay a certain amount of money per period.

   Definitions:
     * amount - Amount of an individual payment
     * rate   - Rate of interest
     * period - Payment periods"
  (:require [com.github.sebhoss.finj.def :refer :all]))

(defnk due-value
  "Calculates the due value of a sequence of periodic payments.

   Parameters:
     * amount - Payment per period
     * rate   - Rate of interest
     * period - Number of payment periods

   Examples:
     * (due-value :amount 100 :rate 0.05 :period 5)
     * (due-value :amount 150 :rate 0.1 :period 8)
     * (due-value :amount 180 :rate 0.15 :period 12)"
  [:amount :rate :period]
  {:pre [(number? amount)
         (number? rate)
         (number? period)]}
  (* amount (+ period
               (* rate (/ (inc period) 2)))))

(defnk immediate-value
  "Calculates the due value of a sequence of periodic payments.

   Parameters:
     * amount - Payment per period
     * rate   - Rate of interest
     * period - Number of payment periods

   Examples:
     * (immediate-value :amount 100 :rate 0.05 :period 5)
     * (immediate-value :amount 150 :rate 0.1 :period 8)
     * (immediate-value :amount 180 :rate 0.15 :period 12)"
  [:amount :rate :period]
  {:pre [(number? amount)
         (number? rate)
         (number? period)]}
  (* amount (+ period
               (* rate (/ (dec period) 2)))))