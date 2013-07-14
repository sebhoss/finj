;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.

(ns com.github.sebhoss.finj.ratio
  (:require [com.github.sebhoss.finj.def :refer :all]
            [com.github.sebhoss.finj.math :refer :all]))

(defnk debt-ratio 
  "Debt Ratio is a financial ratio that indicates the percentage of a company's assets that are provided via debt.

   Parameters:
     * total-debt   - Sum of current liabilities and long-term liabilities
     * total-assets - Sum of current assets, fixed assets, and other assets such as 'goodwill'

   Examples:
     * (debt-ratio :total-debt 1000 :total-assets 10000)
       => 1/10
     * (debt-ratio :total-debt 500 :total-assets 2000)
       => 1/4
     * (debt-ratio :total-debt 300 :total-assets 2500)
       => 3/25

   References:
     * http://en.wikipedia.org/wiki/Debt_ratio"
  [:total-debt :total-assets]
  {:pre [(number? total-debt)
         (number? total-assets)]}
  (/ total-debt total-assets))

(defnk debt-to-capital-ratio
  "Debt-to-capital ratio or D/C ratio is the ratio of its total debt to its total capital, its debt and equity combined.
   The ratio measures a company's capital structure, financial solvency, and degree of leverage, at a particular point in time.

   Parameters:
     * debt   - Sum of current liabilities and long-term liabilities
     * equity - Sum of current assets, after all liabilities are paid

   Examples:
     * (debt-to-capital-ratio :debt 1000 :equity 10000)
       => 1/11
     * (debt-to-capital-ratio :debt 500 :equity 2000)
       => 1/5
     * (debt-to-capital-ratio :debt 300 :equity 2500)
       => 3/28

   References:
     * http://en.wikipedia.org/wiki/Debt-to-capital_ratio"
  [:debt :equity]
  {:pre [(number? debt)
         (number? equity)]}
  (/ debt (+ debt equity)))

(defnk debt-to-equity-ratio
  "Debt-to-equity ratio (D/E) is a financial ratio indicating the relative proportion of shareholders' equity and debt used to
   finance a company's assets. The ratio is also known as Risk, Gearing or Leverage

   Parameters:
     * debt   - Sum of current liabilities and long-term liabilities
     * equity - Sum of current assets, after all liabilities are paid

   Examples:
     * (debt-to-equity-ratio :debt 1000 :equity 10000)
       => 1/10
     * (debt-to-equity-ratio :debt 500 :equity 2000)
       => 1/4
     * (debt-to-equity-ratio :debt 300 :equity 2500)
       => 3/25

   References:
     * http://en.wikipedia.org/wiki/Debt-to-equity_ratio"
  [:debt :equity]
  {:pre [(number? debt)
         (number? equity)]}
  (/ debt equity))

(defnk debtor-collection-period
  "The debtor collection period indicates the average time taken to collect trade debts.

   Parameters:
     * average-debtor - Debtors at the beginning of the year + debtors at the end of the year, divided by 2
     * credit-sales   - Sales made on credit
     * days           - Number of days (optional, defaults to 365)

   Examples:
     * (debtor-collection-period :average-debtor 1000 :credit-sales 10000)
       => 73/2
     * (debtor-collection-period :average-debtor 500 :credit-sales 2000 :days 360)
       => 90N
     * (debtor-collection-period :average-debtor 300 :credit-sales 2500)
       => 219/5

   References:
     * http://en.wikipedia.org/wiki/Debtor_collection_period"
  [:average-debtor :credit-sales :opt-def :days 365]
  {:pre [(number? average-debtor)
         (number? credit-sales)
         (number? days)]}
  (* days (/ average-debtor credit-sales)))

(defnk current-ratio
  "The current ratio is a financial ratio that measures whether or not a firm has enough resources to pay its debts over the next
   12 months. It compares a firm's current assets to its current liabilities.

   Parameters:
     * current-assets      - Debtors at the beginning of the year + debtors at the end of the year, divided by 2
     * current-liabilities - Sales made on credit

   Examples:
     * (current-ratio :current-assets 1000 :current-liabilities 10000)
       => 1/10
     * (current-ratio :current-assets 500 :current-liabilities 2000)
       => 1/4
     * (current-ratio :current-assets 300 :current-liabilities 2500)
       => 3/25

   References:
     * http://en.wikipedia.org/wiki/Current_ratio"
  [:current-assets :current-liabilities]
  {:pre [(number? current-assets)
         (number? current-liabilities)]}
  (/ current-assets current-liabilities))

(defnk capital-adequacy-ratio
  "Capital Adequacy Ratio (CAR), also called Capital to Risk (Weighted) Assets Ratio (CRAR), is a ratio of a bank's capital to its risk.

   Parameters:
     * tier-1-capital       - (paid up capital + statutory reserves + disclosed free reserves) -
                              (equity investments in subsidiary + intangible assets + current & b/f losses)
     * tier-2-capital       - Undisclosed Reserves + General Loss reserves + hybrid debt capital instruments and subordinated debts
     * risk-weighted-assets - Assets or off-balance sheet exposures, weighted according to risk

   Examples:
     * (capital-adequacy-ratio :tier-1-capital 1000 :tier-2-capital 1200 :risk-weighted-assets 5000)
       => 11/25
     * (capital-adequacy-ratio :tier-1-capital 500 :tier-2-capital 800 :risk-weighted-assets 3200)
       => 13/32
     * (capital-adequacy-ratio :tier-1-capital 300 :tier-2-capital 650 :risk-weighted-assets 1800)
       => 19/36

   References:
     * http://en.wikipedia.org/wiki/Capital_adequacy_ratio"
  [:tier-1-capital :tier-2-capital :risk-weighted-assets]
  {:pre [(number? tier-1-capital)
         (number? tier-2-capital)
         (number? risk-weighted-assets)]}
  (/ (+ tier-1-capital tier-2-capital)
     risk-weighted-assets))

(defnk capital-recovery-factor
  "The capital recovery factor is the ratio of a constant annuity to the present value of receiving that annuity for a given length of time.

   Parameters:
     * rate   - Rate of interest
     * period - Number of periods = number of annuities received

   Examples:
     * (capital-recovery-factor :rate 0.05 :period 5)
       => 0.23097479812826793
     * (capital-recovery-factor :rate 0.1 :period 12)
       => 0.14676331510028723
     * (capital-recovery-factor :rate 0.15 :period 18)
       => 0.16318628735215818

   References:
     * http://en.wikipedia.org/wiki/Capital_recovery_factor"
  [:rate :period]
  {:pre [(number? rate)
         (number? period)]}
  (/ (* rate (pow (inc rate) period))
     (dec (pow (inc rate) period))))

(defnk capitalization-rate
  "The Capitalization rate (or cap rate) is the ratio between the net operating income produced by an asset and its capital cost
   or alternatively its current market value.

   Parameters:
     * income - Annual net operating income
     * cost   - Capital cost (the original price paid to buy the asset)

   Examples:
     * (capitalization-rate :income 1000 :cost 10000)
       => 1/10
     * (capitalization-rate :income 1200 :cost 5000)
       => 6/25
     * (capitalization-rate :income 1800 :cost 3600)
       => 1/2

   References:
     * http://en.wikipedia.org/wiki/Capitalization_rate"
  [:income :cost]
  {:pre [(number? income)
         (number? cost)]}
  (/ income cost))

(defnk equity-ratio
  "The equity ratio is a financial ratio indicating the relative proportion of equity used to finance a company's assets.

   Parameters:
     * equity       - Total shareholder's equity
     * total-assets - Total assets

   Examples:
     * (equity-ratio :equity 1000 :total-assets 10000)
       => 1/10
     * (equity-ratio :equity 1200 :total-assets 5000)
       => 6/25
     * (equity-ratio :equity 1800 :total-assets 3600)
       => 1/2

   References:
     * http://en.wikipedia.org/wiki/Equity_ratio"
  [:equity :total-assets]
  {:pre [(number? equity)
         (number? total-assets)]}
  (/ equity total-assets))

(defnk return-on-assets
  "The return on assets (ROA) percentage shows how profitable a company's assets are in generating revenue.

   Parameters:
     * income - Net income of the company
     * assets - Total assets

   Examples:
     * (return-on-assets :income 1000 :assets 10000)
       => 1/10
     * (return-on-assets :income 1200 :assets 5000)
       => 6/25
     * (return-on-assets :income 1800 :assets 3600)
       => 1/2

   References:
     * http://en.wikipedia.org/wiki/Return_on_assets"
  [:income :assets]
  {:pre [(number? income)
         (number? assets)]}
  (/ income assets))

(defnk sustainable-growth-rate
  "The Sustainable Growth Rate (SGR) describes optimal growth from a financial perspective assuming a given 
   strategy with clear defined financial frame conditions/ limitations. Sustainable growth is defined as the annual
   percentage of increase in sales that is consistent with a defined financial policy (target debt to equity ratio,
   target dividend payout ratio, target profit margin, target ratio of total assets to net sales). This concept
   provides a comprehensive financial framework and formula for case/ company specific SGR calculations

   Parameters:
     * profit-margin         - Existing and target profit margin
     * dividend-payout-ratio - Target dividend payout ratio
     * debt-to-equity-ratio  - Target total debt to equity ratio
     * assets-to-sales-ratio - Ratio of total assets to sales

   Examples:
     * (sustainable-growth-rate :profit-margin 0.05 :dividend-payout-ratio 0.02 :debt-to-equity-ratio 0.25 :assets-to-sales-ratio 0.5)
       => 0.1396011396011396
     * (sustainable-growth-rate :profit-margin 0.15 :dividend-payout-ratio 0.04 :debt-to-equity-ratio 0.35 :assets-to-sales-ratio 0.2)
       => 34.71428571428558
     * (sustainable-growth-rate :profit-margin 0.1 :dividend-payout-ratio 0.08 :debt-to-equity-ratio 0.12 :assets-to-sales-ratio 0.4)
       => 0.3469827586206897

   References:
     * http://en.wikipedia.org/wiki/Sustainable_growth_rate"
  [:profit-margin :dividend-payout-ratio :debt-to-equity-ratio :assets-to-sales-ratio]
  {:pre [(number? profit-margin )
         (number? dividend-payout-ratio)
         (number? debt-to-equity-ratio)
         (number? assets-to-sales-ratio)]}
  (/ (* profit-margin (- 1 dividend-payout-ratio) (inc debt-to-equity-ratio))
     (- assets-to-sales-ratio (* profit-margin (- 1 dividend-payout-ratio) (inc debt-to-equity-ratio)))))

(defnk treynor-ratio
  "The Treynor ratio (sometimes called the reward-to-volatility ratio or Treynor measure[1]), named after Jack L. Treynor,
   is a measurement of the returns earned in excess of that which could have been earned on an investment that has no
   diversifiable risk (e.g., Treasury Bills or a completely diversified portfolio), per each unit of market risk assumed.

   Parameters:
     * portfolio-return - Existing and target profit margin
     * risk-free-rate   - Target dividend payout ratio
     * portfolio-beta   - Target total debt to equity ratio

   Examples:
     * (treynor-ratio :portfolio-return 0.08 :risk-free-rate 0.05 :portfolio-beta 1.0)
       => 0.03
     * (treynor-ratio :portfolio-return 0.04 :risk-free-rate 0.06 :portfolio-beta 0.8)
       => -0.024999999999999994
     * (treynor-ratio :portfolio-return 0.1 :risk-free-rate 0.05 :portfolio-beta 1.2)
       => 0.04166666666666667
     * (treynor-ratio :portfolio-return 1/10 :risk-free-rate 5/100 :portfolio-beta 12/10)
       => 1/24

   References:
     * http://en.wikipedia.org/wiki/Treynor_ratio"
  [:portfolio-return :risk-free-rate :portfolio-beta]
  {:pre [(number? portfolio-return)
         (number? risk-free-rate)
         (number? portfolio-beta)]}
  (/ (- portfolio-return risk-free-rate)
     portfolio-beta))
