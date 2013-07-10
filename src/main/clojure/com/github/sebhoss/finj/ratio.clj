;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.

(ns com.github.sebhoss.finj.ratio
  (:require [com.github.sebhoss.finj.def :refer :all]
            [com.github.sebhoss.finj.math :refer :all]))

; http://en.wikipedia.org/wiki/Debt_ratio
(defnk debt-ratio [:total-debt :total-assets]
  (/ total-debt total-assets))

; http://en.wikipedia.org/wiki/Debt-to-capital_ratio
(defnk debt-to-capital-ratio [:debt :equity]
  (/ debt (+ debt equity)))

; http://en.wikipedia.org/wiki/Debt-to-equity_ratio
(defnk debt-to-equity-ratio [:debt :equity]
  (/ debt equity))

; http://en.wikipedia.org/wiki/Debtor_collection_period
(defnk debtor-collection-period [:average-debtor :credit-sales :opt-def :days 365]
  (* days (/ average-debtor credit-sales)))

; http://en.wikipedia.org/wiki/Current_ratio
(defnk current-ratio [:current-assets :current-liabilities]
  (/ current-assets current-liabilities))

; http://en.wikipedia.org/wiki/Capital_adequacy_ratio
(defnk capital-adequacy-ratio [:tier-1-capital :tier-2-capital :risk-weighted-assets]
  (/ (+ tier-1-capital tier-2-capital)
     risk-weighted-assets))

; http://en.wikipedia.org/wiki/Capital_recovery_factor
(defnk capital-recovery-factor [:rate :period]
  (/ (* rate (pow (inc rate) period))
     (dec (pow (inc rate) period))))

; http://en.wikipedia.org/wiki/Capitalization_rate
(defnk capitalization-rate [:income :cost]
  (/ income cost))

; http://en.wikipedia.org/wiki/Equity_ratio
(defnk equity-ratio [:equity :total-assets]
  (/ equity total-assets))
