(ns com.github.sebhoss.finj.periodic-payment-test
  (:require [com.github.sebhoss.finj.periodic-payment :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest due-value-test
  (testing "with positive values"
    (is (≈ 320 (due-value :amount 100 :rate 0.10 :period 3)))))

(deftest immediate-value-test
  (testing "with positive values"
    (is (≈ 310 (immediate-value :amount 100 :rate 0.10 :period 3)))))
