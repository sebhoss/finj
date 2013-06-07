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
    (is (= 1 (signum 1)))
    (is (= 1 (signum 1/12))))
  (testing "with negative numbers"
    (is (= -1 (signum -1)))
    (is (= -1 (signum -1/12))))
  (testing "with zero"
    (is (= 0 (signum 0)))))

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

(deftest sgn-opposite?-test
  (testing "with equal signed integers"
    (is (not (sgn-opposite? 1 2)))
    (is (not (sgn-opposite? -1 -2))))
  (testing "with opposite signed integers"
    (is (sgn-opposite? 1 -2))
    (is (sgn-opposite? -1 2))
    (is (sgn-opposite? 0 -2))
    (is (sgn-opposite? 1 0))))

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
    (is (= -3 (mean 1 -7)))))

(deftest approx?-test
  (testing "with positive integers"
    (is (approx? 1 2 3)))
  (testing "with negative integers"
    (is (approx? -1 -2 3)))
  (testing "with zero"
    (is (approx? 0 0 1))))

(deftest approx-zero?-test
  (testing "with positive integers"
    (is (approx-zero? 1 2)))
  (testing "with negative integers"
    (is (approx-zero? -1 2)))
  (testing "with zero"
    (is (approx-zero? 0 2))))

(deftest gcd-test
  (testing "with positive integers"
    (is (= 5 (gcd 10 5))))
  (testing "with negative integers"
    (is (= 5 (gcd 10 -5)))
    (is (= 5 (gcd -10 5))))
  (testing "with zeros"
    (is (= 10 (gcd 10 0)))))

(deftest lcm-test
  (testing "with positive integers"
    (is (= 10 (lcm 10 5))))
  (testing "with negative integers"
    (is (= 10 (lcm 10 -5)))
    (is (= 10 (lcm -10 5))))
  (testing "with zeros"
    (is (= 0 (lcm 10 0)))))

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
    (is (thrown? AssertionError (sqrt -8))))
  (testing "with zero"
    (is (= 0.0 (sqrt 0)))))
