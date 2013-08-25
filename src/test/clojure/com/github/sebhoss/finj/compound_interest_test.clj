;
; This program is free software. It comes without any warranty, to
; the extent permitted by applicable law. You can redistribute it
; and/or modify it under the terms of the Do What The Fuck You Want
; To Public License, Version 2, as published by Sam Hocevar. See
; http://www.wtfpl.net/ for more details.
;

(ns com.github.sebhoss.finj.compound-interest-test
  (:require [com.github.sebhoss.finj.compound-interest :refer :all]
            [com.github.sebhoss.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest final-value-test
  (testing "with positive values"
    (is (≈ 805 (final-value :present-value 500 :rate 0.1 :period 5)))))

(deftest amount-test
  (testing "with positive values"
    (is (≈ 305 (amount :present-value 500 :rate 0.1 :period 5)))))

(deftest present-value-test
  (testing "with positive values"
    (is (≈ 500 (present-value :final-value 805 :rate 0.1 :period 5)))))

(deftest yield-test
  (testing "with positive values"
    (is (≈ 10 (yield :final-value 805 :present-value 500 :period 5) 0.1))))

(deftest period-test
  (testing "with positive values"
    (is (≈ 5 (period :final-value 805 :present-value 500 :rate 0.1) 0.1))))

(deftest actual-value-test
  (testing "with positive values"
    (is (≈ 974 (actual-value :present-value 500 :rate 0.1 :period 5 :start-part 1 :end-part 1)))))

(deftest final-annual-value-test
  (testing "with positive values"
    (is (≈ 822 (final-annual-value :present-value 500 :rate 0.1 :period 5 :in-year-period 12)))))

(deftest relative-annual-rate-test
  (testing "with positive values"
    (is (≈ 0.03 (relative-annual-rate :rate 0.12 :in-year-period 4) 0.01))))

(deftest conformal-annual-rate-test
  (testing "with positive values"
    (is (≈ 0.03 (conformal-annual-rate :rate 0.12 :in-year-period 4) 0.01))))

(deftest effective-annual-rate-test
  (testing "with positive values"
    (is (≈ 0.12 (effective-annual-rate :relative-annual-rate 0.03 :in-year-period 4) 0.01))))

(deftest final-continuous-value-test
  (testing "with positive values"
    (is (≈ 824 (final-continuous-value :present-value 500 :rate 0.1 :period 5)))))

(deftest intensity-test
  (testing "with positive values"
    (is (≈ 0.09 (intensity :rate 0.1) 0.01))))

(deftest rate-test
  (testing "with positive values"
    (is (≈ 0.1 (rate :intensity 0.09) 0.01))))
