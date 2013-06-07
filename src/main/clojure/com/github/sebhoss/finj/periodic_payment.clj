(ns com.github.sebhoss.finj.periodic-payment)

(defn due-payments [& {:keys [amount rate period]}]
  (* amount (+ period
               (* rate (/ (inc period) 2)))))

(defn immediate-payments [& {:keys [amount rate period]}]
  (* amount (+ period
               (* rate (/ (dec period) 2)))))