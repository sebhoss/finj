(ns com.github.sebhoss.finj.math
  "Misc math functions")

(defn abs [x]
  "Returns the absolute value of a given number."
  (if (< x 0) (- x) x))

(defn distance [x, y]
  "Returns the distance between the given numbers."
  (abs (- y x)))

(defn sgn-eq [x, y]
  "Returns true if both numbers have the same sign, false otherwise."
  (or (and (<= x 0) (<= y 0)) (and (>= x 0) (>= y 0))))

(defn exp [x, n]
  "Returns x^n"
  (reduce * (repeat n x)))

(defn mean [sequence]
  "Returns the mean value of a sequence."
  (/ (reduce + sequence) (count sequence)))

(defn cross-lt-gt [first, second, compareTo]
  "Returns true if first is either lower than compareTo and second is higher than compareTo or vice versa. Otherwise
   returns false."
  (or
    (and (< first compareTo) (> second compareTo))
    (and (> first compareTo) (< second compareTo))))