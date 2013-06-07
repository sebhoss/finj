(ns com.github.sebhoss.finj.math
  "Misc math functions")

(def ^:const e
  "e is the double value that is closer than any other to e, the base of the natural logarithms."
  (Math/E))

(def ^:const pi
  "pi is the double value that is closer than any other to pi, the ratio of the circumference of a circle to its
   diameter."
  (Math/PI))

(defn sgn
  "(sgn x) returns '+' for a positive, '-' for a negative number or an empty string for zero."
  [x]
  {:pre [(number? x)]}
  (cond
    (neg? x) "-"
    (pos? x) "+"
    :else ""))

(defn signum
  "(signum x) returns 0 if x is zero, 1 if x is greater than zero, -1 if x is less than zero"
  [x]
  {:pre [(number? x)]}
  (cond
    (neg? x) -1
    (pos? x) 1
    :else 0))

(defn sgn-eq?
  "(sgn-eq? x y) returns true if both x and y share the same sign."
  [x y]
  {:pre [(number? x)
         (number? y)]}
  (= (sgn x) (sgn y)))

(defn sgn-opposite?
  "(sgn-opposite? x y) returns true if x and y do not share the same sign."
  [x y]
  {:pre [(number? x)
         (number? y)]}
  (not (sgn-eq? x y)))

(defn mean
  "(mean x y) is the mean value of x and y"
  [x y]
  {:pre [(number? x)
         (number? y)]}
  (/ (+ x y) 2))

(defn abs
  "(abs n) is the absolute value of n"
  [n]
  {:pre [(number? n)]}
  (if (neg? n)
    (- n)
    n))

(defn approx?
  "(approx? x y epsilon) returns true if the difference between x and y is less than epsilon."
  [x y epsilon]
  {:pre [(number? x)
         (number? y)
         (number? epsilon)]}
  (< (abs (- x y)) epsilon))

(defn approx-zero?
  "(approx-zero? x epsilon) returns true if x is within epsilon of zero."
  [x epsilon]
  {:pre [(number? x)
         (number? epsilon)]}
  (approx? x 0 epsilon))

(defn gcd
  "(gcd x y) returns the greatest common divisor of x and y"
  [x y]
  {:pre [(integer? x)
         (integer? y)]}
  (loop [x (abs x)
         y (abs y)]
    (if (zero? y)
      x
      (recur y (mod x y)))))

(defn lcm
  "(lcm x y) returns the least common multiple of x and y"
  [x y]
  {:pre [(integer? x)
         (integer? y)]}
  (cond
    (zero? x) 0
    (zero? y) 0
    :else (abs (* y (/ x (gcd x y))))))

(defn floor
  "(floor n) is n rounded down"
  [n]
  {:pre [(number? n)]}
  (Math/floor n))

(defn ceil
  "(ceil n) is n rounded up"
  [n]
  {:pre [(number? n)]}
  (Math/ceil n))

(defn round
  "(round n) is n rounded to the nearest integer"
  [n]
  {:pre [(float? n)]}
  (Math/round n))

(defn sqrt
  "(sqrt n) is square root of n"
  [n]
  {:pre [(or (zero? n) (pos? n))]}
  (Math/sqrt n))

(defn log
  "(log n) is natural logarithms of n"
  [n]
  {:pre [(number? n)]}
  (Math/log n))

(defn log10
  "(log10 n) is the base 10 logarithm of n"
  [n]
  {:pre [(number? n)]}
  (Math/log10 n))

(defn log1p
  "(log1p n) is the natural logarithm of n + 1"
  [n]
  {:pre [(number? n)]}
  (Math/log1p n))

(defn exp
  "(exp n) is Euler's number e raised to the power"
  [power]
  {:pre [(number? power)]}
  (Math/exp power))

(defn pow
  "(pow base power) is base to the power."
  [base power]
  {:pre [(number? base)
         (number? power)]}
  (Math/pow base power))

(defn root
  "(root n x) is the n-th root of x"
  [n x]
  {:pre [(number? n)
         (number? x)]}
  (long (Math/pow x (/ 1.0 n))))

(defn acos
  "(acos n) is the arc cosine of n"
  [n]
  {:pre [(number? n)]}
  (Math/acos n))

(defn asin
  "(asin n) is the arc sine of n"
  [n]
  {:pre [(number? n)]}
  (Math/asin n))

(defn atan
  "(atan n) is the arc tangent of n"
  [n]
  {:pre [(number? n)]}
  (Math/atan n))

(defn cos
  "(cos n) is the trigonometric cosine of n"
  [n]
  {:pre [(number? n)]}
  (Math/cos n))

(defn sin
  "(sin n) is the trigonometric sine of n"
  [n]
  {:pre [(number? n)]}
  (Math/sin n))

(defn tan
  "(tan n) is the trigonometric tangent of n"
  [n]
  {:pre [(number? n)]}
  (Math/tan n))
