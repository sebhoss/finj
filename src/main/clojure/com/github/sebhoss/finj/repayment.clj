(ns com.github.sebhoss.finj.repayment
  (:require [com.github.sebhoss.finj.math :refer :all]))

(defn rate-balance-due [& {:keys [loan period repayment-period]}]
  (* loan (- 1 (/ period repayment-period))))

(defn rate-interest-amount [& {:keys [loan period repayment-period rate]}]
  (* loan rate (- 1 (/ (dec period) repayment-period))))

(defn annuity [& {:keys [loan repayment-period accumulation-factor]}]
  (* loan (/ (* (pow accumulation-factor repayment-period) (dec accumulation-factor))
             (dec (pow accumulation-factor repayment-period)))))

(defn annuity-amount [& {:keys [loan annuity period repayment-period accumulation-factor]}]
  (* (- annuity (* loan (dec accumulation-factor)))
     (pow accumulation-factor (dec period))))

(defn annuity-balance-due [& {:keys [loan annuity period accumulation-factor]}]
  (- (* loan (pow accumulation-factor period))
     (* annuity (/ (dec (pow accumulation-factor period))
                   (dec accumulation-factor)))))

(defn annuity-interest-amount [& {:keys [annuity first-annuity-amount period accumulation-factor]}]
  (- annuity (* first-annuity-amount (pow accumulation-factor (dec period)))))

(defn period [& {:keys [loan annuity accumulation-factor]}]
  (* (/ 1 (ln accumulation-factor))
     (- (ln annuity)
        (ln (- annuity (* loan (dec accumulation-factor)))))))
