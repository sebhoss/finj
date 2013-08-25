;
; This program is free software. It comes without any warranty, to
; the extent permitted by applicable law. You can redistribute it
; and/or modify it under the terms of the Do What The Fuck You Want
; To Public License, Version 2, as published by Sam Hocevar. See
; http://www.wtfpl.net/ for more details.
;

(ns com.github.sebhoss.finj.annuity
  "In finance theory, an annuity is a terminating 'stream' of fixed payments, i.e., a collection of payments to be
   periodically received over a specified period of time.[1] The valuation of such a stream of payments entails concepts
   such as the time value of money, interest rate, and future value.

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)"
  (:require [com.github.sebhoss.def :refer :all]
            [com.github.sebhoss.math :refer :all]))

(defnk present-immediate-factor
  "Calculates the annuity immediate factor.

   Parameters:
     * rate    - Interest rate per period
     * period  - Number of terms

   Examples:
     * (present-immediate-factor :rate 0.05 :period 5)
     * (present-immediate-factor :rate 0.1 :period 8)
     * (present-immediate-factor :rate 0.15 :period 12)

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Annuity-immediate"
  [:rate :period]
  (/ (- 1 (pow (inc rate) (- period)))
        rate))

(defnk future-immediate-factor
  "Calculates the annuity due factor.

   Parameters:
     * rate    - Interest rate per period
     * period  - Number of terms

   Examples:
     * (future-immediate-factor :rate 0.05 :period 5)
     * (future-immediate-factor :rate 0.1 :period 8)
     * (future-immediate-factor :rate 0.15 :period 12)

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Annuity-immediate"
  [:rate :period]
  (/ (dec (pow (inc rate) period))
     rate))


(defnk present-immediate-value
  "An annuity is a series of payments made at fixed intervals of time. If the number of payments is known in advance,
   the annuity is an annuity-certain. If the payments are made at the end of the time periods, so that interest is
   accumulated before the payment, the annuity is called an annuity-immediate, or ordinary annuity. Mortgage payments
   are annuity-immediate, interest is earned before being paid.
   The present value of an annuity is the value of a stream of payments, discounted by the interest rate to account for
   the fact that payments are being made at various moments in the future.

   Parameters:
     * payment - Payment per period
     * rate    - Interest rate per period
     * period  - Number of terms

   Examples:
     * (present-immediate-value :payment 100 :rate 0.05 :period 5)
     * (present-immediate-value :payment 200 :rate 0.1 :period 8)
     * (present-immediate-value :payment 300 :rate 0.15 :period 12)

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Annuity-immediate"
  [:payment :rate :period]
  (* payment (present-immediate-factor :rate rate :period period)))

(defnk future-immediate-value
  "The future value of an annuity is the accumulated amount, including payments and interest, of a stream of payments
   made to an interest-bearing account. For an annuity-immediate, it is the value immediately after the n-th payment.

   Parameters:
     * payment - Payment per period
     * rate    - Interest rate per period
     * period  - Number of terms

   Examples:
     * (future-immediate-value :payment 100 :rate 0.05 :period 5)
     * (future-immediate-value :payment 200 :rate 0.1 :period 8)
     * (future-immediate-value :payment 300 :rate 0.15 :period 12)

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Annuity-immediate"
  [:payment :rate :period]
  (* payment (future-immediate-factor :rate rate :period period)))

(defnk present-due-value
  "An annuity-due is an annuity whose payments are made at the beginning of each period.
   Deposits in savings, rent or lease payments, and insurance premiums are examples of annuities due.

   Parameters:
     * payment - Payment per period
     * rate    - Interest rate per period
     * period  - Number of terms

   Examples:
     * (present-due-value :payment 100 :rate 0.05 :period 5)
     * (present-due-value :payment 200 :rate 0.1 :period 8)
     * (present-due-value :payment 300 :rate 0.15 :period 12)

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Annuity-due"
  [:payment :rate :period]
  (let [immediate-value (present-immediate-value :payment payment :rate rate :period period)]
    (* (inc rate) immediate-value)))

(defnk future-due-value
  "An annuity-due is an annuity whose payments are made at the beginning of each period.
   Deposits in savings, rent or lease payments, and insurance premiums are examples of annuities due.

   Parameters:
     * payment - Payment per period
     * rate    - Interest rate per period
     * period  - Number of terms

   Examples:
     * (future-due-value :payment 100 :rate 0.05 :period 5)
     * (future-due-value :payment 200 :rate 0.1 :period 8)
     * (future-due-value :payment 300 :rate 0.15 :period 12)

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Annuity-due"
  [:payment :rate :period]
  (let [immediate-value (future-immediate-value :payment payment :rate rate :period period)]
    (* (inc rate) immediate-value)))

(defnk perpetuity-immediate-value
  "A perpetuity is an annuity for which the payments continue forever.
   This function calculates the present value of an immediate perpetuity.

   Parameters:
     * payment - Payment per period
     * rate    - Interest rate per period

   Examples:
     * (perpetuity-immediate-value :payment 100 :rate 0.05)
     * (perpetuity-immediate-value :payment 200 :rate 0.1)
     * (perpetuity-immediate-value :payment 300 :rate 0.15)

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Perpetuity"
  [:payment :rate]
  (/ payment rate))

(defnk perpetuity-due-value
  "A perpetuity is an annuity for which the payments continue forever.
   This function calculates the present value of a due perpetuity.

   Parameters:
     * payment - Payment per period
     * rate    - Interest rate per period

   Examples:
     * (perpetuity-due-value :payment 100 :rate 0.05)
     * (perpetuity-due-value :payment 200 :rate 0.1)
     * (perpetuity-due-value :payment 300 :rate 0.15)

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Perpetuity"
  [:payment :rate]
  (let [effective-discount-rate (/ rate (inc rate))]
    (/ payment effective-discount-rate)))
