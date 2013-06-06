(ns com.github.sebhoss.finj.interest)

(defn amount [& {:keys [present-value rate period]}]
  (* present-value rate period))

(defn future-value [& {:keys [present-value rate period]}]
  (+ present-value
     (amount :present-value present-value
             :rate rate
             :period period)))

(defn present-value [& {:keys [future-value rate period]}]
  (/ future-value
     (+ 1 (* rate period))))

(defn rate [& {:keys [future-value present-value period]}]
  (/ (- future-value present-value)
     (* present-value period)))

(defn period [& {:keys [future-value present-value rate]}]
  (/ (- future-value present-value)
     (* present-value rate)))

(defn day [& {:keys [future-value present-value rate days-per-year]
              :or {days-per-year 360}}]
  (* days-per-year
     (/ (- future-value present-value)
        (* present-value rate))))