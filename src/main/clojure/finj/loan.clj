;
; This program is free software. It comes without any warranty, to
; the extent permitted by applicable law. You can redistribute it
; and/or modify it under the terms of the Do What The Fuck You Want
; To Public License, Version 2, as published by Sam Hocevar. See
; http://www.wtfpl.net/ for more details.
;

(ns finj.loan
  "A loan is a debt evidenced by a note which specifies, among other things, the principal amount, interest rate, and
   date of repayment. A loan entails the reallocation of the subject asset(s) for a period of time, between the lender
   and the borrower.

   Definitions:
     * p                   - Rate per cent
     * rate                - Rate of interest (= p/100)
     * accumulation-factor - Accumulator factor q = 1 + rate
     * period              - Multiple of a amortization period or point in time

   References:
     * http://en.wikipedia.org/wiki/Loan"
  (:require [com.github.sebhoss.math :refer :all]
            [com.github.sebhoss.def :refer :all]))

(defnk rate-balance-due
  "Calculates the remaining due value after a certain amount of periods.

   Parameters:
     * loan             - Amount of money borrowed
     * period           - Number of total repayment periods
     * repayment-period - Current repayment period

   Examples:
     * (rate-balance-due :loan 1000 :period 10 :repayment-period 3)
       => -7000/3
     * (rate-balance-due :loan 1250 :period 12 :repayment-period 5)
       => -1750N
     * (rate-balance-due :loan 1800 :period 15 :repayment-period 8)
       => -1575N"
  [:loan :period :repayment-period]
  {:pre [(number? loan)
         (number? period)
         (number? repayment-period)]}
  (* loan (- 1 (/ period repayment-period))))

(defnk rate-interest-amount
  "Calculates the generated interest amount after a certain amount of periods.

   Parameters:
     * loan             - Amount of money borrowed
     * period           - Number of total repayment periods
     * repayment-period - Current repayment period
     * rate             - Rate of interest

   Examples:
     * (rate-interest-amount :loan 1000 :period 10 :repayment-period 3 :rate 0.05)
       => -100.0
     * (rate-interest-amount :loan 1250 :period 12 :repayment-period 5 :rate 0.1)
       => -150.0
     * (rate-interest-amount :loan 1800 :period 15 :repayment-period 8 :rate 0.15)
       => -202.5"
  [:loan :period :repayment-period :rate]
  {:pre [(number? loan)
         (number? period)
         (number? repayment-period)
         (number? rate)]}
  (* loan rate (- 1 (/ (dec period) repayment-period))))

(defnk annuity
  "Calculates the annuity for a given loan over a given number of periods.

   Parameters:
     * loan                - Amount of money borrowed
     * period              - Number of total repayment periods
     * accumulation-factor - Accumulation factor per period

   Examples:
     * (annuity :loan 100 :period 5 :accumulation-factor 1.05)
       => 23.097479812826812
     * (annuity :loan 130 :period 8 :accumulation-factor 1.1)
       => 24.367722284725755
     * (annuity :loan 180 :period 12 :accumulation-factor 1.15)
       => 33.2065397035511

   References:
     * http://en.wikipedia.org/wiki/Annuity_(finance_theory)"
  [:loan :period :accumulation-factor]
  {:pre [(number? loan)
         (number? period)
         (number? accumulation-factor)]}
  (* loan (/ (* (pow accumulation-factor period) (dec accumulation-factor))
             (dec (pow accumulation-factor period)))))

(defnk annuity-amount
  "Calculates the annuity amount for a given loan over a given number of periods.

   Parameters:
     * loan                - Amount of money borrowed
     * annuity             - Collected annuity so far
     * period              - Number of total repayment periods
     * accumulation-factor - Accumulation factor per period

   Examples:
     * (annuity-amount :loan 100 :annuity 50 :period 5 :accumulation-factor 1.05)
       => 54.69778125
     * (annuity-amount :loan 130 :annuity 70 :period 8 :accumulation-factor 1.1)
       => 111.07687470000005
     * (annuity-amount :loan 180 :annuity 105 :period 12 :accumulation-factor 1.15)
       => 362.88652889299925"
  [:loan :annuity :period :accumulation-factor]
  {:pre [(number? loan)
         (number? annuity)
         (number? period)
         (number? accumulation-factor)]}
  (* (- annuity (* loan (dec accumulation-factor)))
     (pow accumulation-factor (dec period))))

(defnk annuity-balance-due
  "Calculates the annuity balance due for a given loan over a given number of periods.

   Parameters:
     * loan                - Amount of money borrowed
     * annuity             - Collected annuity so far
     * period              - Number of total repayment periods
     * accumulation-factor - Accumulation factor per period

   Examples:
     * (annuity-balance-due :loan 100 :annuity 50 :period 5 :accumulation-factor 1.05)
       => -148.65340625000005
     * (annuity-balance-due :loan 130 :annuity 70 :period 8 :accumulation-factor 1.1)
       => -521.8456217
     * (annuity-balance-due :loan 180 :annuity 105 :period 12 :accumulation-factor 1.15)
       => -2082.130054846329"
  [:loan :annuity :period :accumulation-factor]
  {:pre [(number? loan)
         (number? annuity)
         (number? period)
         (number? accumulation-factor)]}
  (- (* loan (pow accumulation-factor period))
     (* annuity (/ (dec (pow accumulation-factor period))
                   (dec accumulation-factor)))))

(defnk annuity-interest-amount
  "Calculates the annuity interest amount for a given loan over a given number of periods.

   Parameters:
     * annuity              - Total current annuity
     * first-annuity-amount - First repayment amount
     * period               - Current repayment period
     * accumulation-factor  - Accumulation factor per period

   Examples:
     * (annuity-interest-amount :annuity 500 :first-annuity-amount 70 :period 4 :accumulation-factor 1.05)
       => 418.96625
     * (annuity-interest-amount :annuity 600 :first-annuity-amount 80 :period 7 :accumulation-factor 1.1)
       => 458.2751199999999
     * (annuity-interest-amount :annuity 720 :first-annuity-amount 95 :period 9 :accumulation-factor 1.15)
       => 429.39282805878923"
  [:annuity :first-annuity-amount :period :accumulation-factor]
  {:pre [(number? annuity)
         (number? first-annuity-amount)
         (number? period)
         (number? accumulation-factor)]}
  (- annuity (* first-annuity-amount (pow accumulation-factor (dec period)))))

(defnk period
  "Calculates the number of periods needed the repay a loan.

   Parameters:
     * loan                - Amount of money borrowed
     * annuity             - Total current annuity
     * accumulation-factor - Accumulation factor per period

   Examples:
     * (period :loan 500 :annuity 120 :accumulation-factor 1.05)
       => 4.788154644658259
     * (period :loan 540 :annuity 160 :accumulation-factor 1.1)
       => 4.319944857590922
     * (period :loan 590 :annuity 180 :accumulation-factor 1.15)
       => 4.841216908623881"
  [:loan :annuity :accumulation-factor]
  {:pre [(number? loan)
         (number? annuity)
         (number? accumulation-factor)]}
  (* (/ 1 (ln accumulation-factor))
     (- (ln annuity)
        (ln (- annuity (* loan (dec accumulation-factor)))))))
