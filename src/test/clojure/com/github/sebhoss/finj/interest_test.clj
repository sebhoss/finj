;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.

(ns com.github.sebhoss.finj.interest-test
  (:require [com.github.sebhoss.finj.interest :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest amount-test
  (testing "with positive values"
    (is (≈ 250 (amount :present-value 500 :rate 0.10 :period 5)))))

(deftest final-value-test
  (testing "with positive values"
    (is (≈ 750 (final-value :present-value 500 :rate 0.10 :period 5)))))

(deftest present-value-test
  (testing "with positive values"
    (is (≈ 333 (present-value :final-value 500 :rate 0.10 :period 5)))))

(deftest rate-test
  (testing "with positive values"
    (is (≈ 1/10 (rate :present-value 500 :final-value 750 :period 5)))))

(deftest period-test
  (testing "with positive values"
    (is (≈ 5 (period :present-value 500 :rate 0.10 :final-value 750)))))

(deftest days-test
  (testing "with positive values"
    (is (≈ 1825 (days :present-value 500 :final-value 750 :rate 0.10))))
  (testing "with custom number of days per year"
    (is (≈ 1800 (days :present-value 500 :final-value 750 :rate 0.10 :days-per-year 360)))))
