;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See "http://www.wtfpl.net/":http://www.wtfpl.net/ for
;   more details.

(ns com.github.sebhoss.finj.investment
  "In finance, investment is putting money into something with the expectation of gain, usually over a longer term.
   This may or may not be backed by research and analysis. Most or all forms of investment involve some form of risk,
   such as investment in equities, property, and even fixed interest securities which are subject, inter alia, to
   inflation risk. or you can express it in short form The act or action of using (money) to make more money out of
   something that will increase in value.

   References:
     * http://en.wikipedia.org/wiki/Investment"
  (:require [com.github.sebhoss.finj.def :refer :all]
            [com.github.sebhoss.finj.math :refer :all]
            [com.github.sebhoss.finj.annuity :refer :all]))

(defnk net-present-value
  "Calculates the net present value (NPV) of a time series of cash flows.

   Parameters:
     * rate      - The discount rate.
     * cashflows - A sequence of cashflows, ordered by their period. This includes the initial investment.

   Examples:
     * (net-present-value :rate 0.05 :cashflows [-1000 500 600 800])
       => 711.4782420904868
     * (net-present-value :rate 0.1 :cashflows [-1250 560 630 840])
       => 410.85649887302736
     * (net-present-value :rate 0.15 :cashflows [-2000 800 750 1200])
       => 51.7794033040193

   References:
     * http://en.wikipedia.org/wiki/Net_present_value"
  [:rate :cashflows]
  (reduce + (map #(/ %1 (pow (inc rate) %2)) cashflows (range))))

(defnk adjusted-present-value
  "Calculates the adjusted present value (ADV) of a business.

   Parameters:
     * value-without-liabilities - The value of the business without any liabilities
     * borrowed-capital          - The amount of borrowed money as a sequence.
     * rate                      - The business 'tax rate' for the borrowed money.
     * risk-free-rate            - The risk free rate.

   Examples:
     * (adjusted-present-value
          :value-without-liabilities 250
          :borrowed-capital [200 300 400 350]
          :rate 0.1
          :risk-free-rate 0.08)
       => 258.7884367220444
     * (adjusted-present-value
          :value-without-liabilities 300
          :borrowed-capital [200 150 120 80]
          :rate 0.05
          :risk-free-rate 0.08)
       => 302.02110450642687
     * (adjusted-present-value
          :value-without-liabilities 500
          :borrowed-capital [100 200 300 350]
          :rate 0.15
          :risk-free-rate 0.08)
       => 509.84273738759333

   References:
     - http://en.wikipedia.org/wiki/Adjusted_present_value"
  [:value-without-liabilities :borrowed-capital :rate :risk-free-rate]
  (+ value-without-liabilities
     (reduce + (map #(/ (* %1 rate risk-free-rate) (pow (inc risk-free-rate) %2))
                 borrowed-capital (range)))))

(defnk equivalent-annual-cost
  "In finance the equivalent annual cost (EAC) is the cost per year of owning and operating an asset over its entire
   lifespan.

   Parameters:
     * investment  - Total invesment cost
     * period      - Expected lifetime of the investment
     * maintenance - Annual maintenance costs
     * rate        - Cost of invested capital

   Examples:
     * (equivalent-annual-cost :investment 50000 :period 3 :maintenance 13000 :rate 0.05)
       => 31360.42823156223
     * (equivalent-annual-cost :investment 150000 :period 8 :maintenance 7500 :rate 0.05)
       => 30708.27204415216
     * (equivalent-annual-cost :investment 75000 :period 5 :maintenance 11000 :rate 0.15)
       => 33373.66643461463

   References:
     * http://en.wikipedia.org/wiki/Equivalent_annual_cost"
  [:investment :period :maintenance :rate]
  {:pre [(number? investment)
         (number? period)
         (number? maintenance)
         (number? rate)]}
  (let [present-immediate-factor (present-immediate-factor :rate rate :period period)]
    (+ maintenance (/ investment present-immediate-factor))))
