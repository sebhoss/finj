(ns com.github.sebhoss.finj.deprecation
  "In accountancy, depreciation refers to two aspects of the same concept:
     - the decrease in value of assets (fair value depreciation), and
     - the allocation of the cost of assets to periods in which the assets are used (depreciation with the matching
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
            [com.github.sebhoss.finj.math :refer :all]))

(defnk straight-line-annual-expense
  "Calculates the annual deprecation expense of an asset using straight line deprecation.

   Parameters:
     * fixed-asset    - Cost of fixed asset
     * residual-value - Estimate of the value of the asset at the time it will be sold or disposed of
     * period         - Useful life of asset

   Examples:
     * (straight-line-annual-expense :fixed-asset 1000 :residual-value 100 :period 5)
     * (straight-line-annual-expense :fixed-asset 2000 :residual-value 200 :period 8)
     * (straight-line-annual-expense :fixed-asset 3000 :residual-value 300 :period 12)

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
     * (straight-line-expense :fixed-asset 2000 :residual-value 200 :period 8)
     * (straight-line-expense :fixed-asset 3000 :residual-value 300 :period 12)

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
     * (straight-line-accumulated :fixed-asset 2000 :residual-value 200 :period 8)
     * (straight-line-accumulated :fixed-asset 3000 :residual-value 300 :period 12)

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
     * (straight-line-book-value :fixed-asset 2000 :residual-value 200 :period 8)
     * (straight-line-book-value :fixed-asset 3000 :residual-value 300 :period 12)

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
     * (declining-balance-rate :fixed-asset 2000 :residual-value 200 :period 8)
     * (declining-balance-rate :fixed-asset 3000 :residual-value 300 :period 12)

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
     * (declining-balance-rate-book-value :fixed-asset 2000 :residual-value 200 :period 8)
     * (declining-balance-rate-book-value :fixed-asset 3000 :residual-value 300 :period 12)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Declining_Balance_Method"
  [:fixed-asset :residual-value :period]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (number? period)]}
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
     * (declining-balance-rate-expense :fixed-asset 2000 :residual-value 200 :period 8)
     * (declining-balance-rate-expense :fixed-asset 3000 :residual-value 300 :period 12)

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
     * (declining-balance-rate-accumulated :fixed-asset 2000 :residual-value 200 :period 8)
     * (declining-balance-rate-accumulated :fixed-asset 3000 :residual-value 300 :period 12)

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
     * (sum-of-years-digit-expense :fixed-asset 2000 :residual-value 200 :period 8)
     * (sum-of-years-digit-expense :fixed-asset 3000 :residual-value 300 :period 12)

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
     * (sum-of-years-digit-accumulated :fixed-asset 2000 :residual-value 200 :period 8)
     * (sum-of-years-digit-accumulated :fixed-asset 3000 :residual-value 300 :period 12)

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
     * (sum-of-years-digit-book-value :fixed-asset 2000 :residual-value 200 :period 8)
     * (sum-of-years-digit-book-value :fixed-asset 3000 :residual-value 300 :period 12)

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Straight-line_depreciation"
  [:fixed-asset :residual-value :period]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (number? period)]}
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
     * (units-of-production-expense :fixed-asset 2000 :residual-value 200 :production [200 210 220 230])
     * (units-of-production-expense :fixed-asset 3000 :residual-value 300 :production [300 310 320 330])

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
     * (units-of-production-accumulated :fixed-asset 2000 :residual-value 200 :production [200 210 220 230])
     * (units-of-production-accumulated :fixed-asset 3000 :residual-value 300 :production [300 310 320 330])

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
     * (units-of-production-book-value :fixed-asset 2000 :residual-value 200 :production [200 210 220 230])
     * (units-of-production-book-value :fixed-asset 3000 :residual-value 300 :production [300 310 320 330])

   References:
     * http://en.wikipedia.org/wiki/Depreciation#Units-of-production_depreciation_method"
  [:fixed-asset :residual-value :production]
  {:pre [(number? fixed-asset)
         (number? residual-value)
         (coll? production)]
   :post [(= fixed-asset (first %))
          (= residual-value (last %))]}
  (let [accumulated (units-of-production-accumulated
                   :fixed-asset fixed-asset
                   :residual-value residual-value
                   :production production)]
    (into (vector fixed-asset)
          (map #(- fixed-asset %) accumulated))))
