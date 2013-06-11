(ns com.github.sebhoss.finj.compound-interest-test
  (:require [com.github.sebhoss.finj.compound-interest :refer :all]
            [com.github.sebhoss.finj.math :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest future-value-test
  (testing "with positive values"
    (is (≈ 805 (future-value :present-value 500 :rate 0.1 :period 5)))))

(deftest amount-test
  (testing "with positive values"
    (is (≈ 305 (amount :present-value 500 :rate 0.1 :period 5)))))

(deftest present-value-test
  (testing "with positive values"
    (is (≈ 500 (present-value :future-value 805 :rate 0.1 :period 5)))))

(deftest yield-test
  (testing "with positive values"
    (is (≈ 10 (yield :future-value 805 :present-value 500 :period 5) 0.1))))

(deftest period-test
  (testing "with positive values"
    (is (≈ 5 (period :future-value 805 :present-value 500 :rate 0.1) 0.1))))

(deftest mixed-value-test
  (testing "with positive values"
    (is (≈ 974 (mixed-value :present-value 500 :rate 0.1 :period 5 :start-part 1 :end-part 1)))))

(deftest in-year-value-test
  (testing "with positive values"
    (is (≈ 822 (in-year-value :present-value 500 :rate 0.1 :period 5 :in-year-period 12)))))

(deftest relative-in-year-rate-test
  (testing "with positive values"
    (is (≈ 0.03 (relative-in-year-rate :rate 0.12 :in-year-period 4) 0.01))))

(deftest conformal-in-year-rate-test
  (testing "with positive values"
    (is (≈ 0.03 (conformal-in-year-rate :rate 0.12 :in-year-period 4) 0.01))))

(deftest effective-in-year-rate-test
  (testing "with positive values"
    (is (≈ 0.12 (effective-in-year-rate :relative-in-year-rate 0.03 :in-year-period 4) 0.01))))

(deftest continuous-value-test
  (testing "with positive values"
    (is (≈ 824 (continuous-value :present-value 500 :rate 0.1 :period 5)))))

(deftest intensity-test
  (testing "with positive values"
    (is (≈ 0.09 (intensity :rate 0.1) 0.01))))

(deftest rate-test
  (testing "with positive values"
    (is (≈ 0.1 (rate :intensity 0.09) 0.01))))
