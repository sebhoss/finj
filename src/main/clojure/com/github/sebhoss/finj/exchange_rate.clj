(ns com.github.sebhoss.finj.exchange-rate
  (:require [com.github.sebhoss.finj.math :refer :all]))

(defmulti market-price
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

(defn real-rate [& {:keys [market-price nominal-rate agio period]}]
  (* (/ 100 market-price)
     (- nominal-rate (/ agio period))))
