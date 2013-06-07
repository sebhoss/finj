(ns com.github.sebhoss.finj.math
  "Misc math functions")

(defn sgn-eq? [x y]
  "Returns true if both numbers have the same sign, false otherwise."
  (or (and (neg? x) (neg? y))
      (and (pos? x) (pos? y))))

(defn sgn-opposite? [x y]
  "Returns true if both numbers have a different sign, false otherwise."
  (not (sgn-eq? x y)))

(defn root [n x]
  "Returns the n-th root of x"
  (long (Math/pow x (/ 1.0 n))))

(defn mean [x y]
  "Returns the mean value of two numbers"
  (/ (+ x y) 2))
