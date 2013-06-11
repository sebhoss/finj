(ns com.github.sebhoss.finj.investment
  (:require [com.github.sebhoss.finj.def :refer :all]
            [com.github.sebhoss.finj.math :refer :all]))

(defnk net-present-value
  "Calculates the net present value (NPV) of a time series of cash flows.

   Options:
     :rate       - The discount rate in percent.
     :cashflows  - A sequence of cashflows, ordered by their period. This includes the initial investment.

   Examples:
     - (net-present-value :rate 10 :cashflows [-1000 500 600 800]))

   References:
     - http://en.wikipedia.org/wiki/Net_present_value"
  [:rate :cashflows]
  (reduce + (map #(/ %1 (pow (inc rate) %2)) cashflows (range))))

(defnk adjusted-present-value
  "Calculates the adjusted present value (ADV) of a business.

   Options:
     :value-without-liabilities  - The value of the business without any liabilities
     :borrowed-capital           - The amount of borrowed money as a sequence.
     :rate                       - The business 'tax rate' for the borrowed money.
     :risk-free-rate             - The risk free rate.

   Examples:
     - (adjusted-present-value
          :value-without-liabilities 250
          :borrowed-capital [200 300 400 350]
          :rate 0.1
          :risk-free-rate 0.08)

   References:
     - http://en.wikipedia.org/wiki/Adjusted_present_value"
  [:value-without-liabilities :borrowed-capital :rate :risk-free-rate]
  (+ value-without-liabilities
     (reduce + (map #(/ (* %1 rate risk-free-rate) (pow (inc risk-free-rate) %2))
                 borrowed-capital (range)))))
