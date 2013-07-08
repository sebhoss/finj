;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See "http://www.wtfpl.net/":http://www.wtfpl.net/ for
;   more details.

(ns com.github.sebhoss.finj.math-test
  (:require [com.github.sebhoss.finj.math :refer :all]
            [clojure.test :refer :all]))

(deftest sgn-test
  (testing "with positive numbers"
    (is (= "+" (sgn 1)))
    (is (= "+" (sgn 1/12))))
  (testing "with negative numbers"
    (is (= "-" (sgn -1)))
    (is (= "-" (sgn -1/12))))
  (testing "with zero"
    (is (= "" (sgn 0)))))

(deftest signum-test
  (testing "with positive numbers"
    (is (= 1.0 (signum 1)))
    (is (= 1.0 (signum 1/12))))
  (testing "with negative numbers"
    (is (= -1.0 (signum -1)))
    (is (= -1.0 (signum -1/12))))
  (testing "with zero"
    (is (= 0.0 (signum 0)))))

(deftest sgn-eq?-test
  (testing "with equal signed integers"
    (is (sgn-eq? 1 2))
    (is (sgn-eq? -1 -2)))
  (testing "with opposite signed integers"
    (is (not (sgn-eq? -1 2)))
    (is (not (sgn-eq? 1 -2)))
    (is (not (sgn-eq? 0 2)))
    (is (not (sgn-eq? -1 0))))
  (testing "with zeros"
    (is (sgn-eq? 0 0))))

(deftest sgn-different?-test
  (testing "with equal signed integers"
    (is (not (sgn-different? 1 2)))
    (is (not (sgn-different? -1 -2))))
  (testing "with opposite signed integers"
    (is (sgn-different? 1 -2))
    (is (sgn-different? -1 2))
    (is (sgn-different? 0 -2))
    (is (sgn-different? 1 0))))

(deftest abs-test
  (testing "with positive integers"
    (is (= 1 (abs 1))))
  (testing "with negative integers"
    (is (= 1 (abs -1))))
  (testing "with zero"
    (is (= 0 (abs 0)))))

(deftest mean-test
  (testing "with positive integers"
    (is (= 2 (mean 1 3)))
    (is (= 3 (mean 1 5)))
    (is (= 4 (mean 1 7))))
  (testing "with negative integers"
    (is (= -2 (mean -1 -3)))
    (is (= -3 (mean -1 -5)))
    (is (= -4 (mean -1 -7))))
  (testing "with positive and negative integers"
    (is (= -1 (mean 1 -3)))
    (is (= 2 (mean -1 5)))
    (is (= -3 (mean 1 -7))))
  (testing "with variable arguments"
    (is (= 3 (mean 1 2 3 4 5))))
  (testing "with vectors"
    (is (= 3 (mean [1 3 5])))))

(deftest floor-test
  (testing "with positive numbers"
    (is (= 1.0 (floor 1.2))))
  (testing "with negative numbers"
    (is (= -2.0 (floor -1.2))))
  (testing "with zero"
    (is (= 0.0 (floor 0)))))

(deftest ceil-test
  (testing "with positive numbers"
    (is (= 2.0 (ceil 1.2))))
  (testing "with negative numbers"
    (is (= -1.0 (ceil -1.2))))
  (testing "with zero"
    (is (= 0.0 (ceil 0)))))

(deftest round-test
  (testing "with positive numbers"
    (is (= 1 (round 1.2))))
  (testing "with negative numbers"
    (is (= -1 (round -1.2))))
  (testing "with zero"
    (is (= 0 (round 0.0)))))

(deftest sqrt-test
  (testing "with positive integers"
    (is (= 2.0 (sqrt 4))))
  (testing "with negative integers"
    (is (Double/isNaN (sqrt -8))))
  (testing "with zero"
    (is (= 0.0 (sqrt 0)))))

(deftest root-test
  (testing "with positive integers"
    (is (= 2.0 (root 3 8))))
  (testing "with negative integers"
    (is (Double/isNaN (root 3 -8))))
  (testing "with zero"
    (is (= 0.0 (root 3 0)))))

(deftest ln-test
  (testing "with integer"
    (is (float? (ln (int 1)))))
  (testing "with long"
    (is (float? (ln (long 1)))))
  (testing "with float"
    (is (float? (ln (float 1)))))
  (testing "with double"
    (is (float? (ln (double 1))))))

(deftest log10-test
  (testing "with integer"
    (is (float? (log10 (int 1)))))
  (testing "with long"
    (is (float? (log10 (long 1)))))
  (testing "with float"
    (is (float? (log10 (float 1)))))
  (testing "with double"
    (is (float? (log10 (double 1))))))

(deftest pow-test
  (testing "with positive integers"
    (is (float? (pow 2 10))))
  (testing "with negative integers"
    (is (float? (pow -2 2)))
    (is (float? (pow 2 -2))))
  (testing "with zero"
    (is (float? (pow 2 0)))
    (is (float? (pow 0 2)))))

(deftest exp-test
  (testing "with positive integers"
    (is (float? (exp 2))))
  (testing "with negative integers"
    (is (float? (exp -2))))
  (testing "with zero"
    (is (float? (exp 0)))))
