(ns com.github.sebhoss.finj.math
  (:require midje.sweet))

(facts "about 'abs'"
  (fact "The absolute value of a positive number is the number itself."
    (abs 1) => 1
    (abs 2) => 2
    (abs 1000) => 1000)
  (fact "The absolute value of zero is zero."
    (abs 0) => 0)
  (fact "The absolute value of a negative number is the non-negative value of itself."
    (abs -1) => 1
    (abs -2) => 2
    (abs -1000) => 1000))

(facts "about 'sgn-eq'"
  (fact "Two positive numbers have the same sign."
    (sgn-eq 1 2) => true
    (sgn-eq 0 7) => true)
  (fact "Two negative numbers have the same sign."
    (sgn-eq -1 -2) => true
    (sgn-eq -5 0) => true)
  (fact "A positive and a negative number do not share the same sign."
    (sgn-eq 1 -1) => false
    (sgn-eq -2 2) => false
    (sgn-eq -1000 1234) => false))
