(ns com.github.sebhoss.finj.share-price
  "A share price is the price of a single share of a number of saleable stocks of a company, derivative or other
   financial asset.

   References:
     * http://en.wikipedia.org/wiki/Share_price"
  (:require [com.github.sebhoss.finj.math :refer :all]))

(defmulti market-price
  "In economics, market price is the economic price for which a good or service is offered in the marketplace. It is of
   interest mainly in the study of microeconomics. Market value and market price are equal only under conditions of
   market efficiency, equilibrium, and rational expectations.

   The following parameter combinations are supported:
     * real-capital & nominal-capital
     * nominal-rate & accumulation-factor & effective-accumulation-factor & period
     * real-benefit & nominal-benefit
     * period & nominal-rate & real-rate & real-benefit
     * period & effective-accumulation-factor & nominal-rate
     * period & effective-accumulation-factor & nominal-rate & agio
     * real-rate & nominal-rate

   References:
     * http://en.wikipedia.org/wiki/Market_price"
  (fn [& {:keys [real-capital nominal-capital nominal-rate accumulation-factor effective-accumulation-factor
                 period real-benefit nominal-benefit nominal-rate real-rate agio]
          :as m}]
    (set (keys m))))
(defmethod market-price #{:real-capital :nominal-capital}
  [& {:keys [real-capital nominal-capital]}]
  (* 100 (/ real-capital nominal-capital)))
(defmethod market-price #{:nominal-rate :accumulation-factor :effective-accumulation-factor :period}
  [& {:keys [nominal-rate accumulation-factor effective-accumulation-factor period]}]
  (* 100 (pow (/ accumulation-factor
                 effective-accumulation-factor)
              period)))
(defmethod market-price #{:real-benefit :nominal-benefit}
  [& {:keys [real-benefit nominal-benefit]}]
  (* 100 (/ real-benefit nominal-benefit)))
(defmethod market-price #{:period :nominal-rate :real-rate :real-benefit}
  [& {:keys [period nominal-rate real-rate real-benefit]}]
  (* (/ 100 period)
     (+ (* period (/ nominal-rate real-rate))
        (* real-benefit
           (- 1 (/ nominal-rate real-rate))))))
(defmethod market-price #{:period :effective-accumulation-factor :nominal-rate}
  [& {:keys [period effective-accumulation-factor nominal-rate]}]
  (* (/ 1 (pow effective-accumulation-factor period))
     (+ 100 (* nominal-rate
               (/ (dec (pow effective-accumulation-factor period))
                  (dec effective-accumulation-factor))))))
(defmethod market-price #{:period :effective-accumulation-factor :nominal-rate :agio}
  [& {:keys [period effective-accumulation-factor nominal-rate agio]}]
  (* (/ 1 (pow effective-accumulation-factor period))
     (+ (* 100 (inc agio))
        (* nominal-rate
          (/ (dec (pow effective-accumulation-factor period))
             (dec effective-accumulation-factor))))))
(defmethod market-price #{:nominal-rate :real-rate}
  [& {:keys [nominal-rate real-rate]}]
  (/ nominal-rate real-rate))
(defmethod market-price #{}
  [x]
  (throw (IllegalArgumentException. "You did not specify any arguments")))

(defn real-rate
  "Calculates the real interest rate.

   Parameters:
     * market-price - Target market price
     * nominal-rate - Nominal rate of interest
     * agio         - Agio or disagio
     * period       - Remaining run time

   Examples:
     * (real-rate :market-price 100 :nominal-rate 0.05 :agio 50 :period 5)
     * (real-rate :market-price 130 :nominal-rate 0.1 :agio 60 :period 8)
     * (real-rate :market-price 180 :nominal-rate 0.15 :agio 80 :period 12)

   References:
     * http://en.wikipedia.org/wiki/Real_interest_rate"
  [& {:keys [market-price nominal-rate agio period]}]
  (* (/ 100 market-price)
     (- nominal-rate (/ agio period))))
