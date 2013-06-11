(ns com.github.sebhoss.finj.interest-test
  (:require [com.github.sebhoss.finj.interest :refer :all]
            [com.github.sebhoss.finj.math :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest amount-test
  (testing "with positive values"
    (is (≈ 250 (amount :present-value 500 :rate 0.10 :period 5)))))

(deftest future-value-test
  (testing "with positive values"
    (is (≈ 750 (future-value :present-value 500 :rate 0.10 :period 5)))))

(deftest present-value-test
  (testing "with positive values"
    (is (≈ 333 (present-value :future-value 500 :rate 0.10 :period 5)))))

(deftest rate-test
  (testing "with positive values"
    (is (≈ 1/10 (rate :present-value 500 :future-value 750 :period 5)))))

(deftest period-test
  (testing "with positive values"
    (is (≈ 5 (period :present-value 500 :rate 0.10 :future-value 750)))))

(deftest day-test
  (testing "with positive values"
    (is (≈ 1800 (day :present-value 500 :future-value 750 :rate 0.10))))
  (testing "with custom number of days per year"
    (is (≈ 1800 (day :present-value 500 :future-value 750 :rate 0.10 :days-per-year 360)))))
