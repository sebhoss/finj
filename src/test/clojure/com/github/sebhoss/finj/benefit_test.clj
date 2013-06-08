(ns com.github.sebhoss.finj.benefit-test
  (:require [com.github.sebhoss.finj.benefit :refer :all]
            [com.github.sebhoss.finj.math :refer :all]
            [clojure.test :refer :all]))

(deftest accumulation-factor-test
  (testing "with integers"
    (is (= 2 (accumulation-factor :rate 1))))
  (testing "with floats"
    (is (= 2.0 (accumulation-factor :rate 1.0))))
  (testing "with ratios"
    (is (= 5/4 (accumulation-factor :rate 1/4)))))

(deftest final-due-value-test
  (testing "with fuzzy results"
    (is (fuzzy-eq? 110 (final-due-value
                         :payment 100
                         :accumulation-factor 1.1
                         :period 1) 0.00001))
    (is (fuzzy-eq? 231 (final-due-value
                         :payment 100
                         :accumulation-factor 1.1
                         :period 2) 0.00001))))

(deftest final-immediate-value-test
  (testing "with exact results"
    (is (= 100.0 (final-immediate-value
                   :payment 100
                   :accumulation-factor 1.1
                   :period 1)))
    (is (= 210.0 (final-immediate-value
                   :payment 100
                   :accumulation-factor 1.1
                   :period 2)))))
