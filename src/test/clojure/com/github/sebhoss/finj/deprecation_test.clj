;
; This program is free software. It comes without any warranty, to
; the extent permitted by applicable law. You can redistribute it
; and/or modify it under the terms of the Do What The Fuck You Want
; To Public License, Version 2, as published by Sam Hocevar. See
; http://www.wtfpl.net/ for more details.
;

(ns com.github.sebhoss.finj.deprecation-test
  (:require [com.github.sebhoss.finj.deprecation :refer :all]
            [com.github.sebhoss.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest straight-line-annual-expense-test
  (testing "with positive integers"
    (is (= 180 (straight-line-annual-expense :fixed-asset 1000 :residual-value 100 :period 5)))
    (is (= 225 (straight-line-annual-expense :fixed-asset 2000 :residual-value 200 :period 8)))
    (is (= 225 (straight-line-annual-expense :fixed-asset 3000 :residual-value 300 :period 12)))))

(deftest straight-line-expense-test
  (testing "with positive integers"
    (is (= '(900 900 900) (straight-line-expense :fixed-asset 3000 :residual-value 300 :period 3)))))

(deftest straight-line-accumulated-test
  (testing "with positive integers"
    (is (= '(900 1800 2700) (straight-line-accumulated :fixed-asset 3000 :residual-value 300 :period 3)))))

(deftest straight-line-book-value-test
  (testing "with positive integers"
    (is (= '(3000 2100 1200 300) (straight-line-book-value :fixed-asset 3000 :residual-value 300 :period 3)))))

(deftest declining-balance-rate-test
  (testing "with positive integers"
    (is (≈ 0.53584 (declining-balance-rate :fixed-asset 3000 :residual-value 300 :period 3)) 0.00001)))

(deftest declining-balance-rate-expense-test
  (testing "with positive integers"
    (is (= '(1607.5233499161661 746.1462430742686 346.33040700956514)
           (declining-balance-rate-expense :fixed-asset 3000 :residual-value 300 :period 3)))))

(deftest declining-balance-rate-accumulated-test
  (testing "with positive integers"
    (is (= '(1607.5233499161661 2353.6695929904345 2699.9999999999995)
           (declining-balance-rate-accumulated :fixed-asset 3000 :residual-value 300 :period 3)))))

(deftest declining-balance-rate-book-value-test
  (testing "with positive integers"
    (is (= '(3000 1392.4766500838339 646.3304070095653 300.00000000000017)
           (declining-balance-rate-book-value :fixed-asset 3000 :residual-value 300 :period 3)))))

(deftest sum-of-years-digit-expense-test
  (testing "with positive integers"
    (is (= '(1350 900 450) (sum-of-years-digit-expense :fixed-asset 3000 :residual-value 300 :period 3)))))

(deftest sum-of-years-digit-accumulated-test
  (testing "with positive integers"
    (is (= '(1350 2250 2700) (sum-of-years-digit-accumulated :fixed-asset 3000 :residual-value 300 :period 3)))))

(deftest sum-of-years-digit-book-value-test
  (testing "with positive integers"
    (is (= '(3000 1650 750 300)
           (sum-of-years-digit-book-value :fixed-asset 3000 :residual-value 300 :period 3)))))

(deftest units-of-production-expense-test
  (testing "with positive integers"
    (is (= '(450 900 1350)
           (units-of-production-expense :fixed-asset 3000 :residual-value 300 :production [100 200 300])))))

(deftest units-of-production-accumulated-test
  (testing "with positive integers"
    (is (= '(450 1350 2700)
           (units-of-production-accumulated :fixed-asset 3000 :residual-value 300 :production [100 200 300])))))

(deftest units-of-production-book-value-test
  (testing "with positive integers"
    (is (= '(3000 2550 1650 300)
           (units-of-production-book-value :fixed-asset 3000 :residual-value 300 :production [100 200 300])))))
