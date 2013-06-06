(ns com.github.sebhoss.finj.compound-interest)

(defn amount [& {:keys [present-value rate period]}]
  (- (future-value :present-value present-value :rate rate :period period) present-value)

(defn future-value [& {:keys [present-value rate period]}]
  (* present-value (exp (inc rate) period)))

(defn present-value [& {:keys [future-value rate period]}]
  (/ future-value
     (exp (inc rate) period)))

(defn yield [& {:keys [future-value present-value period]}]
  (* 100 (dec (root period (/ future-value present-value)))))

(defn period [& {:keys [future-value present-value rate]}]
  (/ (- (Math/log future-value) (Math/log present-value))
     (Math/log (inc rate))))

(defn mixed-value [& {:keys [present-value rate start-part period end-part]}]
  (* present-value (inc (* rate start-part))
                   (exp (inc rate) period)
                   (inc (* rate end-part))))

(defn in-year-value [& {:keys [present-value rate period in-year-period]}]
  (* present-value (exp (inc (/ rate in-year-period)) (* period in-year-period))))

(defn relative-in-year-rate [& {:keys [rate in-year-period]}]
  (/ rate in-year-period))

(defn conformal-in-year-rate [& {:keys [rate in-year-period]}]
  (dec (root in-year-period (inc rate))))

(defn effective-in-year-rate [& {:keys [relative-in-year-rate in-year-period]}]
  (dec (exp (inc relative-in-year-rate) in-year-period)))

(defn continuous-value [& {:keys [present-value rate period]}]
  (* present-value (exp (Math/E) (* rate period))))

(defn intensity [& {:keys [rate]}]
  (Math/log (inc rate)))

(defn rate [& {:keys [intensity]}]
  (dec (exp (Math/E) intensity)))
