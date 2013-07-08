;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See "http://www.wtfpl.net/":http://www.wtfpl.net/ for
;   more details.

(ns com.github.sebhoss.finj.deprecation
  "In accountancy, depreciation refers to two aspects of the same concept:
     * the decrease in value of assets (fair value depreciation), and
     * the allocation of the cost of assets to periods in which the assets are used (depreciation with the matching
       principle).
   The former affects the balance sheet of a business or entity, and the latter affects the net income that they report.
   Generally the cost is allocated, as depreciation expense, among the periods in which the asset is expected to be
   used. This expense is recognized by businesses for financial reporting and tax purposes. Methods of computing
   depreciation, and the periods over which assets are depreciated, may vary between asset types within the same
   business. These may be specified by law or accounting standards, which may vary by country. There are several
   standard methods of computing depreciation expense, including fixed percentage, straight line, and declining balance
   methods. Depreciation expense generally begins when the asset is placed in service. For example, a depreciation
   expense of 100 per year for 5 years may be recognized for an asset costing 500.

   References:
     * http://en.wikipedia.org/wiki/Depreciation"
  (:require [com.github.sebhoss.finj.def :refer :all]
            [com.github.sebhoss.finj.math :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]))

(defnk straight-line-annual-expense
  "Calculates the annual deprecation expense of an asset using straight line deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * period         - Useful life of asset

   Examples:
     * (straight-line-annual-expense :fixed-asset 1000 :residual-value 100 :period 5)
       => 180
     * (straight-line-annual-expense :fixed-asset 2000 :residual-value 200 :period 8)
       => 225
     * (straight-line-annual-expense :fixed-asset 3000 :residual-value 300 :period 12)
       => 225

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Straight-line_depreciation"
  [:fixed-asset :residual-value :period]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (number? period)]}
  (-> fixed-asset
      (- residual-value)
      (/ period)))

(defnk straight-line-expense
  "Calculates the deprecation expense sequence of an asset using straight line deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * period         - Useful life of asset

   Examples:
     * (straight-line-expense :fixed-asset 1000 :residual-value 100 :period 5)
       => (180 180 180 180 180)
     * (straight-line-expense :fixed-asset 2000 :residual-value 200 :period 8)
       => (225 225 225 225 225 225 225 225)
     * (straight-line-expense :fixed-asset 3000 :residual-value 300 :period 12)
       => (225 225 225 225 225 225 225 225 225 225 225 225)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Straight-line_depreciation"
  [:fixed-asset :residual-value :period]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (number? period)]}
  (let [annual-expense (straight-line-annual-expense
                         :fixed-asset fixed-asset
                         :residual-value residual-value
                         :period period)]
    (->> annual-expense
         (repeat)
         (take period))))

(defnk straight-line-accumulated
  "Calculates the accumulated deprecation expense sequence of an asset using straight line deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * period         - Useful life of asset

   Examples:
     * (straight-line-accumulated :fixed-asset 1000 :residual-value 100 :period 5)
       => (180 360 540 720 900)
     * (straight-line-accumulated :fixed-asset 2000 :residual-value 200 :period 8)
       => (225 450 675 900 1125 1350 1575 1800)
     * (straight-line-accumulated :fixed-asset 3000 :residual-value 300 :period 12)
       => (225 450 675 900 1125 1350 1575 1800 2025 2250 2475 2700)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Straight-line_depreciation"
  [:fixed-asset :residual-value :period]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (number? period)]}
  (let [annual-expense (straight-line-annual-expense
                         :fixed-asset fixed-asset
                         :residual-value residual-value
                         :period period)]
    (->> annual-expense
         (iterate (partial + annual-expense))
         (take period))))

(defnk straight-line-book-value
  "Calculates the yearly book value sequence of an asset using straight line deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * period         - Useful life of asset

   Examples:
     * (straight-line-book-value :fixed-asset 1000 :residual-value 100 :period 5)
       => (1000 820 640 460 280 100)
     * (straight-line-book-value :fixed-asset 2000 :residual-value 200 :period 8)
       => (2000 1775 1550 1325 1100 875 650 425 200)
     * (straight-line-book-value :fixed-asset 3000 :residual-value 300 :period 12)
       => (3000 2775 2550 2325 2100 1875 1650 1425 1200 975 750 525 300)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Straight-line_depreciation"
  [:fixed-asset :residual-value :period]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (number? period)]
   :post [(= fixed-asset (first %))
          (≈ residual-value (last %))]}
  (let [annual-expense (straight-line-annual-expense
                         :fixed-asset fixed-asset
                         :residual-value residual-value
                         :period period)]
    (->> fixed-asset
         (iterate #(- % annual-expense))
         (take (inc period)))))

(defnk declining-balance-rate
  "With the declining balance method, one can find the depreciation rate that would allow exactly for full depreciation
   by the end of the period.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * period         - Useful life of asset

   Examples:
     * (declining-balance-rate :fixed-asset 1000 :residual-value 100 :period 5)
       => 0.36904265551980675
     * (declining-balance-rate :fixed-asset 2000 :residual-value 200 :period 8)
       => 0.2501057906675441
     * (declining-balance-rate :fixed-asset 3000 :residual-value 300 :period 12)
       => 0.1745958147319816

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Declining_Balance_Method"
  [:fixed-asset :residual-value :period]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (number? period)]}
  (->> fixed-asset
       (/ residual-value)
       (root period)
       (dec)
       (abs)))

(defnk declining-balance-rate-book-value
  "Calculates the book value sequence of an asset using declining balance rate deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * period         - Useful life of asset

   Examples:
     * (declining-balance-rate-book-value :fixed-asset 1000 :residual-value 100 :period 5)
       => (1000 630.9573444801932 398.1071705534972 251.188643150958 158.48931924611134 100.0)
     * (declining-balance-rate-book-value :fixed-asset 2000 :residual-value 200 :period 8)
       => (2000 1499.7884186649117 1124.6826503806983 843.3930068571647 632.455532033676 474.2747411323312
           355.6558820077847 266.70428643266496 200.0000000000001)
     * (declining-balance-rate-book-value :fixed-asset 3000 :residual-value 300 :period 12)
       => (3000 2476.2125558040552 2043.8762071738838 1687.0239755710472 1392.4766500838336 1149.3560548671862
           948.6832980505137 783.0471647047609 646.330407009565 533.4838230116767 440.33978028662074 363.45829758857644
           300)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Declining_Balance_Method"
  [:fixed-asset :residual-value :period]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (number? period)]
   :post [(= fixed-asset (first %))
          (≈ residual-value (last %))]}
  (let [rate (declining-balance-rate
               :fixed-asset fixed-asset
               :residual-value residual-value
               :period period)]
  (map #(if (< % residual-value) residual-value %)
        (take (inc period) (iterate #(- % (* % rate)) fixed-asset)))))

(defnk declining-balance-rate-expense
  "Calculates the deprecation expense sequence of an asset using declining balance rate deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * period         - Useful life of asset

   Examples:
     * (declining-balance-rate-expense :fixed-asset 1000 :residual-value 100 :period 5)
       => (369.0426555198068 232.850173926696 146.9185274025392 92.69932390484666 58.48931924611134)
     * (declining-balance-rate-expense :fixed-asset 2000 :residual-value 200 :period 8)
       => (500.2115813350882 375.1057682842134 281.28964352353364 210.93747482348863 158.18079090134484
           118.6188591245465 88.95159557511978 66.70428643266483)
     * (declining-balance-rate-expense :fixed-asset 3000 :residual-value 300 :period 12)
       => (523.7874441959448 432.33634863017147 356.85223160283664 294.54732548721364 243.1205952166474
           200.67275681667252 165.63613334575277 136.71675769519592 112.84658399788827 93.14404272505597
           76.88148269804432 63.45829758857644)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Declining_Balance_Method"
  [:fixed-asset :residual-value :period]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (number? period)]}
  (let [rate (declining-balance-rate
               :fixed-asset fixed-asset
               :residual-value residual-value
               :period period)
        book-value (take period
                     (declining-balance-rate-book-value
                       :fixed-asset fixed-asset
                       :residual-value residual-value
                       :period period))]
    (map #(if (< (- % (* % rate)) residual-value) (- % residual-value) (* % rate)) book-value)))

(defnk declining-balance-rate-accumulated
  "Calculates the accumulated deprecation expense sequence of an asset using declining balance rate deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * period         - Useful life of asset

   Examples:
     * (declining-balance-rate-accumulated :fixed-asset 1000 :residual-value 100 :period 5)
       => (369.0426555198068 601.8928294465028 748.811356849042 841.5106807538887 900.0)
     * (declining-balance-rate-accumulated :fixed-asset 2000 :residual-value 200 :period 8)
       => (500.2115813350882 875.3173496193017 1156.6069931428353 1367.544467966324 1525.7252588676688
           1644.3441179922154 1733.2957135673353 1800.0)
     * (declining-balance-rate-accumulated :fixed-asset 3000 :residual-value 300 :period 12)
       => (523.7874441959448 956.1237928261162 1312.9760244289528 1607.5233499161664 1850.6439451328138
           2051.3167019494863 2216.952835295239 2353.669592990435 2466.5161769883234 2559.6602197133793
           2636.5417024114236 2700.0)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Declining_Balance_Method"
  [:fixed-asset :residual-value :period]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (number? period)]}
  (let [expense (declining-balance-rate-expense
               :fixed-asset fixed-asset
               :residual-value residual-value
               :period period)]
    (reductions + expense)))

(defnk sum-of-years-digit-expense
  "Calculates the deprecation expense sequence of an asset using sum of years digit deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * period         - Useful life of asset

   Examples:
     * (sum-of-years-digit-expense :fixed-asset 1000 :residual-value 100 :period 5)
       => (300N 240N 180N 120N 60N)
     * (sum-of-years-digit-expense :fixed-asset 2000 :residual-value 200 :period 8)
       => (400N 350N 300N 250N 200N 150N 100N 50N)
     * (sum-of-years-digit-expense :fixed-asset 3000 :residual-value 300 :period 12)
       => (5400/13 4950/13 4500/13 4050/13 3600/13 3150/13 2700/13 2250/13 1800/13 1350/13 900/13 450/13)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Sum-of-years-digits_method"
  [:fixed-asset :residual-value :period]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (number? period)]}
  (let [periods (range 1 (inc period))
        sum-of-digits (reduce + periods)
        total-depreciable-cost (- fixed-asset residual-value)]
    (map #(* total-depreciable-cost (/ % sum-of-digits)) (reverse periods))))

(defnk sum-of-years-digit-accumulated
  "Calculates the accumulated deprecation expense sequence of an asset using sum of years digit deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * period         - Useful life of asset

   Examples:
     * (sum-of-years-digit-accumulated :fixed-asset 1000 :residual-value 100 :period 5)
       => (300N 540N 720N 840N 900N)
     * (sum-of-years-digit-accumulated :fixed-asset 2000 :residual-value 200 :period 8)
       => (400N 750N 1050N 1300N 1500N 1650N 1750N 1800N)
     * (sum-of-years-digit-accumulated :fixed-asset 3000 :residual-value 300 :period 12)
       => (5400/13 10350/13 14850/13 18900/13 22500/13 25650/13 28350/13 30600/13 32400/13 33750/13 34650/13 2700N)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Sum-of-years-digits_method"
  [:fixed-asset :residual-value :period]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (number? period)]}
  (let [expenses (sum-of-years-digit-expense
                   :fixed-asset fixed-asset
                   :residual-value residual-value
                   :period period)]
    (reductions + expenses)))

(defnk sum-of-years-digit-book-value
  "Calculates the yearly book value sequence of an asset using sum of years digit deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * period         - Useful life of asset

   Examples:
     * (sum-of-years-digit-book-value :fixed-asset 1000 :residual-value 100 :period 5)
       => (1000 700N 460N 280N 160N 100N)
     * (sum-of-years-digit-book-value :fixed-asset 2000 :residual-value 200 :period 8)
       => (2000 1600N 1250N 950N 700N 500N 350N 250N 200N)
     * (sum-of-years-digit-book-value :fixed-asset 3000 :residual-value 300 :period 12)
       => (3000 33600/13 28650/13 24150/13 20100/13 16500/13 13350/13 10650/13 8400/13 6600/13 5250/13 4350/13 300N)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Straight-line_depreciation"
  [:fixed-asset :residual-value :period]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (number? period)]
   :post [(= fixed-asset (first %))
          (≈ residual-value (last %))]}
  (let [accumulated (sum-of-years-digit-accumulated
                   :fixed-asset fixed-asset
                   :residual-value residual-value
                   :period period)]
  (->> accumulated
       (map #(- fixed-asset %))
       (conj [fixed-asset])
       (flatten))))

(defnk units-of-production-expense
  "Calculates the deprecation expense sequence of an asset using units of production deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * production     - The actual production per period. The number of items in this vector define how many
                        periods there are.

   Examples:
     * (units-of-production-expense :fixed-asset 1000 :residual-value 100 :production [100 110 120 130])
       => (4500/23 4950/23 5400/23 5850/23)
     * (units-of-production-expense :fixed-asset 2000 :residual-value 200 :production [200 210 220 230])
       => (18000/43 18900/43 19800/43 20700/43)
     * (units-of-production-expense :fixed-asset 3000 :residual-value 300 :production [300 310 320 330])
       => (4500/7 4650/7 4800/7 4950/7)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Units-of-production_depreciation_method"
  [:fixed-asset :residual-value :production]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (coll? production)]}
  (let [deprecation-per-unit (/ (- fixed-asset residual-value) (reduce + production))]
    (map #(* % deprecation-per-unit) production)))

(defnk units-of-production-accumulated
  "Calculates the accumulated deprecation expense sequence of an asset using units of production deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * production     - The actual production per period. The number of items in this vector define how many
                        periods there are.

   Examples:
     * (units-of-production-accumulated :fixed-asset 1000 :residual-value 100 :production [100 110 120 130])
       => (4500/23 9450/23 14850/23 900N)
     * (units-of-production-accumulated :fixed-asset 2000 :residual-value 200 :production [200 210 220 230])
       => (18000/43 36900/43 56700/43 1800N)
     * (units-of-production-accumulated :fixed-asset 3000 :residual-value 300 :production [300 310 320 330])
       => (4500/7 9150/7 13950/7 2700N)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Units-of-production_depreciation_method"
  [:fixed-asset :residual-value :production]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (coll? production)]}
  (let [expenses (units-of-production-expense
                   :fixed-asset fixed-asset
                   :residual-value residual-value
                   :production production)]
    (reductions + expenses)))

(defnk units-of-production-book-value
  "Calculates the book value sequence of an asset using units of production deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * production     - The actual production per period. The number of items in this vector define how many
                        periods there are.

   Examples:
     * (units-of-production-book-value :fixed-asset 1000 :residual-value 100 :production [100 110 120 130])
       => (1000 18500/23 13550/23 8150/23 100N)
     * (units-of-production-book-value :fixed-asset 2000 :residual-value 200 :production [200 210 220 230])
       => (2000 68000/43 49100/43 29300/43 200N)
     * (units-of-production-book-value :fixed-asset 3000 :residual-value 300 :production [300 310 320 330])
       => (3000 16500/7 11850/7 7050/7 300N)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Units-of-production_depreciation_method"
  [:fixed-asset :residual-value :production]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (coll? production)]
   :post [(= fixed-asset (first %))
          (≈ residual-value (last %))]}
  (let [accumulated (units-of-production-accumulated
                   :fixed-asset fixed-asset
                   :residual-value residual-value
                   :production production)]
    (flatten (list fixed-asset (map #(- fixed-asset %) accumulated)))))
