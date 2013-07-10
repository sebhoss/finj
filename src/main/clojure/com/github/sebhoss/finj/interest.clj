;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.

(ns com.github.sebhoss.finj.interest
  "Interest is a fee paid by a borrower of assets to the owner as a form of compensation for the use of the assets.
   It is most commonly the price paid for the use of borrowed money, or money earned by deposited funds.

   Definitions:
     * p             - Rate per cent
     * rate          - Rate of interest (= p/100)
     * period        - Multiple of a interest period or point in time
     * days-per-year - Number of days per year
     * amount        - Generated amount of interest
     * present-value - Present value of a investment or seed capital
     * final-value   - Final value of a investment or capital at a certain point in time
     * days          - Number of interest days

   References:
     * http://en.wikipedia.org/wiki/Interest"
  (:require [com.github.sebhoss.finj.def :refer :all]))

(defnk amount
  "Calculates the generated amount of interest for a given investment with a given rate over a given period of time.

   Parameters:
     * present-value - Seed capital
     * rate          - Rate of interest 
     * period        - Number of interest periods

   Examples:
     * (amount :present-value 100 :rate 0.05 :period 5)
       => 25.0
     * (amount :present-value 500 :rate 0.1 :period 8)
       => 400.0
     * (amount :present-value 800 :rate 0.15 :period 12)
       => 1440.0"
  [:present-value :rate :period]
  {:pre [(number? present-value)
         (number? rate)
         (number? period)]}
  (* present-value rate period))

(defnk final-value
  "Calculates the final value for a given investment with a given rate over a given period of time.

   Parameters:
     * present-value - Seed capital
     * rate          - Rate of interest
     * period        - Number of interest periods

   Examples:
     * (final-value :present-value 100 :rate 0.05 :period 5)
       => 125.0
     * (final-value :present-value 500 :rate 0.1 :period 8)
       => 900.0
     * (final-value :present-value 800 :rate 0.15 :period 12)
       => 2240.0"
  [:present-value :rate :period]
  {:pre [(number? present-value)
         (number? rate)
         (number? period)]}
  (+ present-value
     (amount :present-value present-value
             :rate rate
             :period period)))

(defnk present-value
  "Calculates the present value for a given final value with a given rate over a given period of time.

   Parameters:
     * final-value - Final value of a investment
     * rate        - Rate of interest of final value
     * period      - Number of periods of final value

   Examples:
     * (present-value :final-value 250 :rate 0.05 :period 5)
       => 200.0
     * (present-value :final-value 400 :rate 0.1 :period 8)
       => 222.22222222222223
     * (present-value :final-value 750 :rate 0.15 :period 12)
       => 267.8571428571429"
  [:final-value :rate :period]
  {:pre [(number? final-value)
         (number? rate)
         (number? period)]}
  (/ final-value
     (inc (* rate period))))

(defnk rate
  "Calculates the required rate of interest for a given seed capital to reach a given final value within a given
   period of time.

   Parameters:
     * final-value   - Final value to reach
     * present-value - Seed capital
     * period        - Number of periods

   Examples:
     * (rate :final-value 300 :present-value 150 :period 5)
       => 1/5
     * (rate :final-value 500 :present-value 180 :period 8)
       => 2/9
     * (rate :final-value 800 :present-value 230 :period 12)
       => 19/92"
  [:final-value :present-value :period]
  {:pre [(number? final-value)
         (number? present-value)
         (number? period)]}
  (/ (- final-value present-value)
     (* present-value period)))

(defnk period
  "Calculates the number of periods for a given seed capital to reach a final value with a given rate of interest.

   Parameters:
     * final-value   - Final value to reach
     * present-value - Seed capital
     * rate          - Rate of interest

   Examples:
     * (period :final-value 300 :present-value 150 :rate 0.05)
       => 20.0
     * (period :final-value 500 :present-value 180 :rate 0.1)
       => 17.77777777777778
     * (period :final-value 800 :present-value 230 :rate 0.15)
       => 16.52173913043478"
  [:final-value :present-value :rate]
  {:pre [(number? final-value)
         (number? present-value)
         (number? rate)]}
  (/ (- final-value present-value)
     (* present-value rate)))

(defnk days
  "Calculates the number interest days for a given seed capital to reach a final value with a given rate.

   Parameters:
     * final-value   - Final value to reach
     * present-value - Seed capital
     * rate          - Rate of interest
     * days-per-year - Number of days per year

   Examples:
     * (days :final-value 300 :present-value 150 :rate 0.05)
       => 7300.0
     * (days :final-value 500 :present-value 180 :rate 0.1)
       => 6488.88888888889
     * (days :final-value 800 :present-value 230 :rate 0.15 :days-per-year 360)
       => 5947.826086956521"
  [:final-value :present-value :rate :opt-def :days-per-year 365]
  {:pre [(number? final-value)
         (number? present-value)
         (number? rate)
         (number? days-per-year)]}
  (* days-per-year
     (/ (- final-value present-value)
        (* present-value rate))))
