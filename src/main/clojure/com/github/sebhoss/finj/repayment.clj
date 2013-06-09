(ns com.github.sebhoss.finj.repayment
  (:require [com.github.sebhoss.finj.math :refer :all]
            [com.github.sebhoss.finj.def :refer :all]))

(defnk rate-balance-due [:loan :period :repayment-period]
  (* loan (- 1 (/ period repayment-period))))

(defnk rate-interest-amount [:loan :period :repayment-period :rate]
  (* loan rate (- 1 (/ (dec period) repayment-period))))

(defnk annuity [:loan :repayment-period :accumulation-factor]
  (* loan (/ (* (pow accumulation-factor repayment-period) (dec accumulation-factor))
             (dec (pow accumulation-factor repayment-period)))))

(defnk annuity-amount [:loan :annuity :period :repayment-period :accumulation-factor]
  (* (- annuity (* loan (dec accumulation-factor)))
     (pow accumulation-factor (dec period))))

(defnk annuity-balance-due [:loan :annuity :period :accumulation-factor]
  (- (* loan (pow accumulation-factor period))
     (* annuity (/ (dec (pow accumulation-factor period))
                   (dec accumulation-factor)))))

(defnk annuity-interest-amount [:annuity :first-annuity-amount :period :accumulation-factor]
  (- annuity (* first-annuity-amount (pow accumulation-factor (dec period)))))

(defnk period [:loan :annuity :accumulation-factor]
  (* (/ 1 (ln accumulation-factor))
     (- (ln annuity)
        (ln (- annuity (* loan (dec accumulation-factor)))))))
