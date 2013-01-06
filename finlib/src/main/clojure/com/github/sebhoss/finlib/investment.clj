(ns com.github.sebhoss.finlib.investment)

(defn net-present-value
  "
   Calculates the net present value (NPV) of a time series of cash flows.

   Options:
     :investment - The initial investment needed to generate a cashflow.
     :rate       - The discount rate in percent.
     :cashflows  - A sequence of cashflows, ordered by their period.

   Examples:
     - (net-present-value :investment 1000 :rate 10 :cashflows (500 600 800))

   References:
     - http://en.wikipedia.org/wiki/Net_present_value
  "

  [{:keys [investment rate cashflows]}]
    [investment rate cashflows]
)

(defn adjusted-present-value
  "
   Calculates the adjusted present value (ADV) of a business.

   References:
     - http://en.wikipedia.org/wiki/Adjusted_present_value
  "

  [{:keys []}]
    []
)