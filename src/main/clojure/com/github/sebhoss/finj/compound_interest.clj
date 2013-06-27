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
       => 127.62815625000003
     * (final-value :present-value 500 :rate 0.1 :period 8)
       => 1071.7944050000008
     * (final-value :present-value 800 :rate 0.15 :period 12)
       => 4280.200084378965"
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
       => 27.628156250000032
     * (amount :present-value 500 :rate 0.1 :period 8)
       => 571.7944050000008
     * (amount :present-value 800 :rate 0.15 :period 12)
       => 3480.2000843789647"
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
       => 195.8815416171147
     * (present-value :final-value 400 :rate 0.1 :period 8)
       => 186.60295208389323
     * (present-value :final-value 750 :rate 0.15 :period 12)
       => 140.18036263999954"
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
       => 14.869835499703509
     * (yield :final-value 500 :present-value 180 :period 8)
       => 13.621936646749933
     * (yield :final-value 800 :present-value 230 :period 12)
       => 10.946476081096757"
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
       => 14.206699082890463
     * (period :final-value 500 :present-value 180 :rate 0.1)
       => 10.719224847014937
     * (period :final-value 800 :present-value 230 :rate 0.15)
       => 8.918968909280778"
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
     * (actual-value :present-value 150 :rate 0.05 :start-part 0.3 :period 5 :end-part 0.5)
       => 199.17171458789062
     * (actual-value :present-value 150 :rate 0.05 :start-part 0 :period 3 :end-part 0)
       => 173.64375
     * (actual-value :present-value 150 :rate 0.05 :start-part 1 :period 5 :end-part 1)
       => 211.06506339843756"
  [:present-value :rate :start-part :period :end-part]
  (* present-value
     (inc (* rate start-part))
     (pow (inc rate) period)
     (inc (* rate end-part))))

(defnk final-annual-value
  "Calculates the final value for a given investment with a given rate over a given period of time using during the
   periods return on investments.

   Parameters:
     * present-value  - Seed capital
     * rate           - Rate of interest
     * period         - Number of interest periods
     * in-year-period - Number of compounding periods per year

   Examples:
     * (final-annual-value :present-value 100 :rate 0.05 :period 5 :in-year-period 12)
       => 128.33586785035118
     * (final-annual-value :present-value 500 :rate 0.1 :period 8 :in-year-period 12)
       => 1109.0878155189757
     * (final-annual-value :present-value 800 :rate 0.15 :period 12 :in-year-period 4)
       => 4682.9448738035235"
  [:present-value :rate :period :in-year-period]
  {:pre [(number? present-value)
         (number? rate)
         (number? period)
         (number? in-year-period)]}
  (* present-value (pow (inc (/ rate in-year-period)) (* period in-year-period))))

(defnk relative-annual-rate
  "Calculates the relative, annual rate of interest.

   Parameters:
     * rate           - Rate of interest
     * in-year-period - Number of compounding periods per year

   Examples:
     * (relative-annual-rate :rate 0.05 :in-year-period 12)
       => 0.004166666666666667
     * (relative-annual-rate :rate 0.1 :in-year-period 4)
       => 0.025
     * (relative-annual-rate :rate 0.15 :in-year-period 6)
       => 0.024999999999999998"
  [:rate :in-year-period]
  {:pre [(number? rate)
         (number? in-year-period)]}
  (/ rate in-year-period))

(defnk conformal-annual-rate
  "Calculates the conformal, annual rate of interest.

   Parameters:
     * rate           - Rate of interest
     * in-year-period - Number of compounding periods per year

   Examples:
     * (conformal-annual-rate :rate 0.05 :in-year-period 12)
       => 0.0040741237836483535
     * (conformal-annual-rate :rate 0.1 :in-year-period 4)
       => 0.02411368908444511
     * (conformal-annual-rate :rate 0.15 :in-year-period 6)
       => 0.023567073118145654"
  [:rate :in-year-period]
  {:pre [(number? rate)
         (number? in-year-period)]}
  (dec (root in-year-period (inc rate))))

(defnk effective-annual-rate
  "Calculates the effective, annual rate of interest.

   Parameters:
     * relative-annual-rate - Relative annual rate of interest
     * in-year-period       - Number of compounding periods per year

   Examples:
     * (effective-annual-rate :relative-annual-rate 0.05 :in-year-period 12)
       => 0.7958563260221301
     * (effective-annual-rate :relative-annual-rate 0.1 :in-year-period 4)
       => 0.4641000000000006
     * (effective-annual-rate :relative-annual-rate 0.15 :in-year-period 6)
       => 1.313060765624999

   References:
     * https://en.wikipedia.org/wiki/Effective_annual_rate"
  [:relative-annual-rate :in-year-period]
  {:pre [(number? relative-annual-rate)
         (number? in-year-period)]}
  (dec (pow (inc relative-annual-rate) in-year-period)))

(defnk final-continuous-value
  "Calculates the final value using continuous interests.

   Parameters:
     * present-value - Seed capital
     * rate          - Rate of interest
     * period        - Number of periods

   Examples:
     * (final-continuous-value :present-value 100 :rate 0.05 :period 5)
       => 128.40254166877415
     * (final-continuous-value :present-value 150 :rate 0.1 :period 8)
       => 333.8311392738702
     * (final-continuous-value :present-value 180 :rate 0.15 :period 12)
       => 1088.93654359433"
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
       => 0.04879016416943205
     * (intensity :rate 0.1)
       => 0.09531017980432493
     * (intensity :rate 0.15)
       => 0.13976194237515863"
  [:rate]
  {:pre [(number? rate)]}
  (ln (inc rate)))

(defnk rate
  "Calculates the rate of interest.

   Parameters:
     * intensity - The interest intensity

   Examples:
     * (rate :intensity 0.23)
       => 0.25860000992947785
     * (rate :intensity 0.098)
       => 0.10296278510850776
     * (rate :intensity 0.74)
       => 1.0959355144943643"
  [:intensity]
  {:pre [(number? intensity)]}
  (dec (exp intensity)))
