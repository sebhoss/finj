(ns com.github.sebhoss.finj.compound-interest
  "Compound interest arises when interest is added to the principal, so that, from that moment on, the interest that
   has been added also earns interest. This addition of interest to the principal is called compounding.

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
     * http://en.wikipedia.org/wiki/Compound_interest"
  (:require [com.github.sebhoss.finj.math :refer :all]
            [com.github.sebhoss.finj.def :refer :all]))

(defnk final-value
  "Calculates the final value for a given investment with a given rate over a given period of time.

   Parameters:
     * present-value - Seed capital
     * rate          - Rate of interest
     * period        - Number of interest periods

   Examples:
     * (final-value :present-value 100 :rate 0.05 :period 5)
     * (final-value :present-value 500 :rate 0.1 :period 8)
     * (final-value :present-value 800 :rate 0.15 :period 12)"
  [:present-value :rate :period]
  {:pre [(number? present-value)
         (number? rate)
         (number? period)]}
  (* present-value (pow (inc rate) period)))

(defnk amount
  "Calculates the generated amount of interest for a given investment with a given rate over a given period of time.

   Parameters:
     * present-value - Seed capital
     * rate          - Rate of interest 
     * period        - Number of interest periods

   Examples:
     * (amount :present-value 100 :rate 0.05 :period 5)
     * (amount :present-value 500 :rate 0.1 :period 8)
     * (amount :present-value 800 :rate 0.15 :period 12)"
  [:present-value :rate :period]
  {:pre [(number? present-value)
         (number? rate)
         (number? period)]}
  (- (final-value
       :present-value present-value
       :rate rate
       :period period)
     present-value))

(defnk present-value
  "Calculates the present value for a given final value with a given rate over a given period of time.

   Parameters:
     * final-value - Final value of a investment
     * rate        - Rate of interest of final value
     * period      - Number of periods of final value

   Examples:
     * (present-value :final-value 250 :rate 0.05 :period 5)
     * (present-value :final-value 400 :rate 0.1 :period 8)
     * (present-value :final-value 750 :rate 0.15 :period 12)"
  [:final-value :rate :period]
  {:pre [(number? final-value)
         (number? rate)
         (number? period)]}
  (/ final-value
     (pow (inc rate) period)))

(defnk yield
  "Calculates the yield for a given seed capital to reach a given final value within a given period of time.

   Parameters:
     * final-value   - Final value to reach
     * present-value - Seed capital
     * period        - Number of periods

   Examples:
     * (yield :final-value 300 :present-value 150 :period 5)
     * (yield :final-value 500 :present-value 180 :period 8)
     * (yield :final-value 800 :present-value 230 :period 12)"
  [:final-value :present-value :period]
  {:pre [(number? final-value)
         (number? present-value)
         (number? period)]}
  (* 100 (dec (root period (/ final-value present-value)))))

(defnk period
  "Calculates the number of periods for a given seed capital to reach a final value with a given rate of interest.

   Parameters:
     * final-value   - Final value to reach
     * present-value - Seed capital
     * rate          - Rate of interest

   Examples:
     * (period :final-value 300 :present-value 150 :rate 0.05)
     * (period :final-value 500 :present-value 180 :rate 0.1)
     * (period :final-value 800 :present-value 230 :rate 0.15)"
  [:final-value :present-value :rate]
  {:pre [(number? final-value)
         (number? present-value)
         (number? rate)]}
  (/ (- (ln final-value) (ln present-value))
     (ln (inc rate))))

(defnk actual-value
  "Calculates the actual/360 value for a given seed capital with a given rate of interest over a given period of time.

   Parameters:
     * present-value - Seed capital
     * rate          - Rate of interest
     * start-part    - Length of 'broken' start period
     * period        - Number of periods
     * end-part      - Length of 'broken' end period

   Examples:
     * (actual :present-value 150 :rate 0.05 :start-part 0.3 :period 5 :end-part 0.5)
     * (actual :present-value 150 :rate 0.05 :start-part 0 :period 3 :end-part 0)
     * (actual :present-value 150 :rate 0.05 :start-part 1 :period 5 :end-part 1)"
  [:present-value :rate :start-part :period :end-part]
  (* present-value
     (inc (* rate start-part))
     (pow (inc rate) period)
     (inc (* rate end-part))))

(defnk final-during-value
  "Calculates the final value for a given investment with a given rate over a given period of time using during the
   periods return on investments.

   Parameters:
     * present-value  - Seed capital
     * rate           - Rate of interest
     * period         - Number of interest periods
     * in-year-period - Number of during the period periods

   Examples:
     * (final-during-value :present-value 100 :rate 0.05 :period 5 :in-year-period 12)
     * (final-during-value :present-value 500 :rate 0.1 :period 8 :in-year-period 12)
     * (final-during-value :present-value 800 :rate 0.15 :period 12 :in-year-period 4)"
  [:present-value :rate :period :in-year-period]
  {:pre [(number? present-value)
         (number? rate)
         (number? period)
         (number? in-year-period)]}
  (* present-value (pow (inc (/ rate in-year-period)) (* period in-year-period))))

(defnk relative-during-rate
  "Calculates the relative, during the period rate of interest.

   Parameters:
     * rate           - Rate of interest
     * in-year-period - Number of during the period periods

   Examples:
     * (relative-during-rate :rate 0.05 :in-year-period 12)
     * (relative-during-rate :rate 0.1 :in-year-period 4)
     * (relative-during-rate :rate 0.15 :in-year-period 6)"
  [:rate :in-year-period]
  {:pre [(number? rate)
         (number? in-year-period)]}
  (/ rate in-year-period))

(defnk conformal-during-rate
  "Calculates the conformal, during the period rate of interest.

   Parameters:
     * rate           - Rate of interest
     * in-year-period - Number of during the period periods

   Examples:
     * (conformal-during-rate :rate 0.05 :in-year-period 12)
     * (conformal-during-rate :rate 0.1 :in-year-period 4)
     * (conformal-during-rate :rate 0.15 :in-year-period 6)"
  [:rate :in-year-period]
  {:pre [(number? rate)
         (number? in-year-period)]}
  (dec (root in-year-period (inc rate))))

(defnk effective-during-rate
  "Calculates the effective, during the period rate of interest.

   Parameters:
     * relative-during-rate - Relative during rate of interest
     * in-year-period       - Number of during the period periods

   Examples:
     * (effective-during-rate :relative-during-rate 0.05 :in-year-period 12)
     * (effective-during-rate :relative-during-rate 0.1 :in-year-period 4)
     * (effective-during-rate :relative-during-rate 0.15 :in-year-period 6)"
  [:relative-during-rate :in-year-period]
  {:pre [(number? relative-during-rate)
         (number? in-year-period)]}
  (dec (pow (inc relative-during-rate) in-year-period)))

(defnk final-continuous-value
  "Calculates the final value using continuous interests.

   Parameters:
     * present-value - Seed capital
     * rate          - Rate of interest
     * period        - Number of periods

   Examples:
     * (final-continuous-value :present-value 100 :rate 0.05 :period 5)
     * (final-continuous-value :present-value 150 :rate 0.1 :period 8)
     * (final-continuous-value :present-value 180 :rate 0.15 :period 12)"
  [:present-value :rate :period]
  {:pre [(number? present-value)
         (number? rate)
         (number? period)]}
  (* present-value (exp (* rate period))))

(defnk intensity
  "Calculates the interest intensity.

   Parameters:
     * rate - Rate of interest

   Examples:
     * (intensity :rate 0.05)
     * (intensity :rate 0.1)
     * (intensity :rate 0.15)"
  [:rate]
  {:pre [(number? rate)]}
  (ln (inc rate)))

(defnk rate
  "Calculates the rate of interest.

   Parameters:
     * intensity - The interest intensity

   Examples:
     * (rate :intensity 0.23)
     * (rate :intensity 0.098)
     * (rate :intensity 0.74)"
  [:intensity]
  {:pre [(number? intensity)]}
  (dec (exp intensity)))
