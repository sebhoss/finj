;
; This program is free software. It comes without any warranty, to
; the extent permitted by applicable law. You can redistribute it
; and/or modify it under the terms of the Do What The Fuck You Want
; To Public License, Version 2, as published by Sam Hocevar. See
; http://www.wtfpl.net/ for more details.
;

(ns finj.annuity-test
  (:require [finj.annuity :refer :all]
            [com.github.sebhoss.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest present-immediate-factor-test
  (testing "with positive integers"
    (is (≈ 4 (present-immediate-factor :rate 0.05 :period 5)))))

(deftest future-immediate-factor-test
  (testing "with positive integers"
    (is (≈ 5 (future-immediate-factor :rate 0.05 :period 5)))))

(deftest present-immediate-value-test
  (testing "with positive integers"
    (is (≈ 432 (present-immediate-value :payment 100 :rate 0.05 :period 5)))))

(deftest present-due-value-test
  (testing "with positive integers"
    (is (≈ 454 (present-due-value :payment 100 :rate 0.05 :period 5)))))

(deftest future-immediate-value-test
  (testing "with positive integers"
    (is (≈ 552 (future-immediate-value :payment 100 :rate 0.05 :period 5)))))

(deftest future-due-value-test
  (testing "with positive integers"
    (is (≈ 580 (future-due-value :payment 100 :rate 0.05 :period 5)))))

(deftest perpetuity-immediate-value-test
  (testing "with positive integers"
    (is (≈ 2000 (perpetuity-immediate-value :payment 100 :rate 0.05)))))

(deftest perpetuity-due-value-test
  (testing "with positive integers"
    (is (≈ 2100 (perpetuity-due-value :payment 100 :rate 0.05)))))
