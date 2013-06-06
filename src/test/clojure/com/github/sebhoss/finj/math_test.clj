(ns com.github.sebhoss.finj.math-test
  (:require [com.github.sebhoss.finj.math :refer :all]
            [clojure.test :refer :all]))

(deftest abs-test
  (testing "with positive numbers"
    (is (= 1 (abs 1)))
    (is (= 2 (abs 2))))
  (testing "with negative numbers"
    (is (= 1 (abs -1)))
    (is (= 2 (abs -2))))
  (testing "with zero"
    (is (= 0 (abs 0)))))

(deftest sgn-eq?-test
  (testing "with equal signed numbers"
    (is (sgn-eq? 1 2))
    (is (sgn-eq? -1 -2)))
  (testing "with opposite signed numbers"
    (is (not (sgn-eq? -1 2)))
    (is (not (sgn-eq? 1 -2)))
    (is (not (sgn-eq? 0 2)))
    (is (not (sgn-eq? -1 0)))))

(deftest sgn-opposite?-test
  (testing "with equal signed numbers"
    (is (not (sgn-opposite? 1 2)))
    (is (not (sgn-opposite? -1 -2))))
  (testing "with opposite signed numbers"
    (is (sgn-opposite? 1 -2))
    (is (sgn-opposite? -1 2))
    (is (sgn-opposite? 0 -2))
    (is (sgn-opposite? 1 0))))

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
