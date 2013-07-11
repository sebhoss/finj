(ns com.github.sebhoss.finj.ratio-test
  (:require [com.github.sebhoss.finj.ratio :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest debt-ratio-test
  (testing "with integers"
    (is (= 1/10 (debt-ratio :total-debt 1000 :total-assets 10000)))))

(deftest debt-to-capital-ratio-test
  (testing "with integers"
    (is (= 1/11 (debt-to-capital-ratio :debt 1000 :equity 10000)))))

(deftest debt-to-equity-ratio-test
  (testing "with integers"
    (is (= 1/10 (debt-to-equity-ratio :debt 1000 :equity 10000)))))

(deftest debtor-collection-period-test
  (testing "with integers"
    (is (= 73/2 (debtor-collection-period :average-debtor 1000 :credit-sales 10000)))))

(deftest current-ratio-test
  (testing "with integers"
    (is (= 1/10 (current-ratio :current-assets 1000 :current-liabilities 10000)))))

(deftest capital-adequacy-ratio-test
  (testing "with integers"
    (is (= 11/25 (capital-adequacy-ratio :tier-1-capital 1000 :tier-2-capital 1200 :risk-weighted-assets 5000)))))

(deftest capital-recovery-factor-test
  (testing "with positive numbers"
    (is (≈ 0.23097 (capital-recovery-factor :rate 0.05 :period 5)))))

(deftest capitalization-rate-test
  (testing "with integers"
    (is (= 1/10 (capitalization-rate :income 1000 :cost 10000)))))

(deftest equity-ratio-test
  (testing "with integers"
    (is (= 1/10 (equity-ratio :equity 1000 :total-assets 10000)))))