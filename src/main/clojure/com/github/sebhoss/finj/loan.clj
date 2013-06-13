(ns com.github.sebhoss.finj.loan
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
  (:require [com.github.sebhoss.finj.math :refer :all]
            [com.github.sebhoss.finj.def :refer :all]))

(defnk rate-balance-due
  "Calculates the remaining due value after a certain amount of periods.

   Parameters:
     * loan             - Amount of money borrowed
     * period           - Number of total repayment periods
     * repayment-period - Current repayment period

   Examples:
     * (rate-balance-due :loan 1000 :period 10 :repayment-period 3)
     * (rate-balance-due :loan 1250 :period 12 :repayment-period 5)
     * (rate-balance-due :loan 1800 :period 15 :repayment-period 8)"
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
     * (rate-interest-amount :loan 1250 :period 12 :repayment-period 5 :rate 0.1)
     * (rate-interest-amount :loan 1800 :period 15 :repayment-period 8 :rate 0.15)"
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
     * (annuity :loan 130 :period 8 :accumulation-factor 1.1)
     * (annuity :loan 180 :period 12 :accumulation-factor 1.15)

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
     * (annuity-amount :loan 130 :annuity 70 :period 8 :accumulation-factor 1.1)
     * (annuity-amount :loan 180 :annuity 105 :period 12 :accumulation-factor 1.15)"
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
     * (annuity-balance-due :loan 130 :annuity 70 :period 8 :accumulation-factor 1.1)
     * (annuity-balance-due :loan 180 :annuity 105 :period 12 :accumulation-factor 1.15)"
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
     * (annuity-interest-amount :annuity 600 :first-annuity-amount 80 :period 7 :accumulation-factor 1.1)
     * (annuity-interest-amount :annuity 720 :first-annuity-amount 95 :period 9 :accumulation-factor 1.15)"
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
     * (period :loan 540 :annuity 160 :accumulation-factor 1.1)
     * (period :loan 590 :annuity 180 :accumulation-factor 1.15)"
  [:loan :annuity :accumulation-factor]
  {:pre [(number? loan)
         (number? annuity)
         (number? accumulation-factor)]}
  (* (/ 1 (ln accumulation-factor))
     (- (ln annuity)
        (ln (- annuity (* loan (dec accumulation-factor)))))))
