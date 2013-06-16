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
   by the end of the period, using the formula

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
       (dec)))
   