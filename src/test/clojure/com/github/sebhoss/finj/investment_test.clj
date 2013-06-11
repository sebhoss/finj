(ns com.github.sebhoss.finj.investment-test
  (:require [com.github.sebhoss.finj.investment :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest net-present-value-test
  (testing "with positive values"
    (is (≈ 551 (net-present-value
                 :rate 0.1
                 :cashflows [-1000 500 600 800])))))

(deftest adjusted-present-value-test
  (testing "with positive values"
    (is (≈ 258 (adjusted-present-value
                 :value-without-liabilities 250
                 :borrowed-capital [200 300 400 350]
                 :rate 0.1
                 :risk-free-rate 0.08)))))
