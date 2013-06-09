(ns com.github.sebhoss.finj.math
  "Misc math functions"
  (:import (com.google.common.math IntMath LongMath DoubleMath)))

(def ^:const e
  "e is the double value that is closer than any other to e, the base of the natural logarithms."
  (Math/E))

(def ^:const pi
  "pi is the double value that is closer than any other to pi, the ratio of the circumference of a circle to its
   diameter."
  (Math/PI))

(defprotocol MEAN
  (mean [x y] "(mean x y) is the mean value of x and y"))
(extend-protocol MEAN
  Integer
    (mean [x y] (IntMath/mean x y))
  Long
    (mean [x y] (LongMath/mean x y))
  Number
    (mean [x y] (/ (+ x y) 2)))

(defprotocol ABS
  (abs [x] "(abs x) is the absolute value of x"))
(extend-protocol ABS
  Number
    (abs [x] (if (neg? x) (- x) x)))

(defprotocol GCD
  (gcd [x y] "(gcd x y) returns the greatest common divisor of x and y"))
(extend-protocol GCD
  Integer
    (gcd [x y] (IntMath/gcd x y))
  Long
    (gcd [x y] (LongMath/gcd x y))
  BigInteger
    (gcd [x y] (.gcd x y)))

(defprotocol LCM
  (lcm [x y] "(lcm x y) returns the least common multiple of x and y"))
(extend-protocol LCM
  Number
    (lcm [x y] (cond
                 (zero? x) 0
                 (zero? y) 0
                 :else (abs (* y (/ x (gcd x y)))))))

(defprotocol Rounding
  (floor [x] "(floor x) is n rounded down")
  (ceil [x] "(ceil x) is n rounded up")
  (round [x] "(round x) is n rounded to the nearest integer"))
(extend-protocol Rounding
  Number
    (floor [x] (Math/floor x))
    (ceil [x] (Math/ceil x))
    (round [x] (Math/round x)))

(defprotocol Sign
  (sgn [x] "(sgn x) returns '+' for positive, '-' for negative numbers and an empty string for zero")
  (signum [x] "(signum x) returns 1 for positive, -1 for negative numbers and 0 for zero")
  (sgn-eq? [x y] "(sgn-eq? x y) returns true if both x and y share the same sign")
  (sgn-different? [x y] "(sgn-different? x y) returns true if x and y do not share the same sign"))
(extend-protocol Sign
  Number
    (sgn [x]
      (cond
        (neg? x) "-"
        (pos? x) "+"
        :else ""))
    (signum [x]
      (cond
        (neg? x) -1
        (pos? x) 1
        :else 0))
    (sgn-eq? [x y]
      (= (sgn x) (sgn y)))
    (sgn-different? [x y]
      (not (sgn-eq? x y))))

(defprotocol Logarithm
  (log2 [x] "(log2 x) is the base 2 logarithm of x")
  (ln [x] "(ln x) is the natural (base e) logarithm of x")
  (log10 [x] "(log10 x) is the base 10 logarithm of x"))
(extend-protocol Logarithm
  Number
    (log2 [x] (DoubleMath/log2 x))
    (ln [x] (Math/log x))
    (log10 [x] (Math/log10 x)))

(defprotocol Exponent
  (exp [exponent] "(exp exponent) is Euler's number e raised to the power of exponent")
  (pow [base exponent] "(pow base exponent) is base raised to the power of exponent"))
(extend-protocol Exponent
  Number
    (exp [exponent] (Math/exp exponent))
    (pow [base exponent] (Math/pow base exponent)))

(defprotocol Root
  (root [degree radicand] "(root degree radicand) is the degree-th root of radicand")
  (sqrt [radicand] "(sqrt radicand) is square root of radicand"))
(extend-protocol Root
  Number
    (root [degree radicand] (Math/pow radicand (/ 1.0 degree)))
    (sqrt [radicand] (Math/sqrt radicand)))

(defprotocol Trigonometric
  (sin [x] "(sin x) is the trigonometric sine of x")
  (cos [x] "(cos x) is the trigonometric cosine of x")
  (tan [x] "(tan x) is the trigonometric tangent of x")
  (asin [x] "(asin x) is the arc sine of x")
  (acos [x] "(acos x) is the arc cosine of x")
  (atan [x] "(atan x) is the arc tangent of x"))
(extend-protocol Trigonometric
  Number
    (sin [x] (Math/sin x))
    (cos [x] (Math/cos x))
    (tan [x] (Math/tan x))
    (asin [x] (Math/asin x))
    (acos [x] (Math/acos x))
    (atan [x] (Math/atan x)))

(defprotocol Hyperbolic
  (sinh [x] "(sinh x) is the hyperbolic sine of x")
  (cosh [x] "(cosh x) is the hyperbolic cosine of x")
  (tanh [x] "(tanh x) is the hyperbolic tangent of x"))
(extend-protocol Hyperbolic
  Number
    (sinh [x] (Math/sinh x))
    (cosh [x] (Math/cosh x))
    (tanh [x] (Math/tanh x)))
