(ns com.github.sebhoss.finj.annuity
  "In finance theory, an annuity is a terminating 'stream' of fixed payments, i.e., a collection of payments to be
   periodically received over a specified period of time.[1] The valuation of such a stream of payments entails concepts
   such as the time value of money, interest rate, and future value.

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)"
  (:require [com.github.sebhoss.finj.def :refer :all]
            [com.github.sebhoss.finj.math :refer :all]))

(defnk present-immediate-value
  "An annuity is a series of payments made at fixed intervals of time. If the number of payments is known in advance,
   the annuity is an annuity-certain. If the payments are made at the end of the time periods, so that interest is
   accumulated before the payment, the annuity is called an annuity-immediate, or ordinary annuity. Mortgage payments
   are annuity-immediate, interest is earned before being paid.
   The present value of an annuity is the value of a stream of payments, discounted by the interest rate to account for
   the fact that payments are being made at various moments in the future.

   Parameters:
     * rate   - Interest rate per period
     * period - Number of terms

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Annuity-immediate"
  [:rate :period]
  (/ (- 1 (pow (inc rate) (- period)))
     rate))

(defnk future-immediate-value
  "The future value of an annuity is the accumulated amount, including payments and interest, of a stream of payments
   made to an interest-bearing account. For an annuity-immediate, it is the value immediately after the n-th payment.

   Parameters:
     * rate   - Interest rate per period
     * period - Number of terms

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Annuity-immediate"
  [:rate :period]
  (/ (dec (pow (inc rate) period))
     rate))

(defnk present-due-value
  "An annuity-due is an annuity whose payments are made at the beginning of each period.
   Deposits in savings, rent or lease payments, and insurance premiums are examples of annuities due.

   Parameters:
     * rate   - Interest rate per period
     * period - Number of terms

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Annuity-due"
  [:rate :period]
  (let [immediate-value (present-immediate-value :rate rate :period period)]
    (* (inc rate) immediate-value)))

(defnk future-due-value
  "An annuity-due is an annuity whose payments are made at the beginning of each period.
   Deposits in savings, rent or lease payments, and insurance premiums are examples of annuities due.

   Parameters:
     * rate   - Interest rate per period
     * period - Number of terms

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Annuity-due"
  [:rate :period]
  (let [immediate-value (future-immediate-value :rate rate :period period)]
    (* (inc rate) immediate-value)))

(defnk perpetuity-immediate-value
  "A perpetuity is an annuity for which the payments continue forever.
   This function calculates the present value of an immediate perpetuity.

   Parameters:
     * rate   - Interest rate per period

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Perpetuity"
  [:rate]
  (/ 1 rate))

(defnk perpetuity-due-value
  "A perpetuity is an annuity for which the payments continue forever.
   This function calculates the present value of a due perpetuity.

   Parameters:
     * rate   - Interest rate per period

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)#Perpetuity"
  [:rate]
  (let [effective-discount-rate (/ rate (inc rate))]
    (/ 1 effective-discount-rate)))
