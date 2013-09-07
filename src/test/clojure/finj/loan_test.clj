;
; Copyright © 2013 Sebastian Hoß <mail@shoss.de>
; This work is free. You can redistribute it and/or modify it under the
; terms of the Do What The Fuck You Want To Public License, Version 2,
; as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
;

(ns finj.loan-test
  (:require [finj.loan :refer :all]
            [com.github.sebhoss.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest rate-balance-due-test
  (testing "with positive values"
    (is (≈ 200 (rate-balance-due :loan 500 :period 3 :repayment-period 5)))))

(deftest rate-interest-amount-test
  (testing "with positive values"
    (is (≈ 30 (rate-interest-amount :loan 500 :period 3 :repayment-period 5 :rate 0.1)))))

(deftest annuity-test
  (testing "with positive values"
    (is (≈ 131 (annuity :loan 500 :period 5 :accumulation-factor 1.1)))))

(deftest annuity-amount-test
  (testing "with positive values"
    (is (≈ 98 (annuity-amount :loan 500 :annuity 131 :period 3 :accumulation-factor 1.1)))))

(deftest annuity-balance-due-test
  (testing "with positive values"
    (is (≈ 231 (annuity-balance-due :loan 500 :annuity 131 :period 3 :accumulation-factor 1.1)))))

(deftest annuity-interest-amount-test
  (testing "with positive values"
    (is (≈ 106 (annuity-interest-amount :annuity 131 :first-annuity-amount 20 :period 3 :accumulation-factor 1.1)))))

(deftest period-test
  (testing "with positive values"
    (is (≈ 5 (period :loan 500 :annuity 131 :accumulation-factor 1.1)))))
