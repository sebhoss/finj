;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.

(ns com.github.sebhoss.finj.share-price-test
  (:require [com.github.sebhoss.finj.share-price :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest market-price-test
  (testing "with real/nominal-capital"
    (is (≈ 80 (market-price :real-capital 800 :nominal-capital 1000))))
  (testing "with nominal-rate, etc."
    (is (≈ 57 (market-price :nominal-rate 0.1 :accumulation-factor 1.1 :effective-accumulation-factor 1.23 :period 5))))
  (testing "with real/nominal-benefit"
    (is (≈ 800/9 (market-price :real-benefit 120 :nominal-benefit 135))))
  (testing "with real/nominal-rate and benefit"
    (is (≈ -590 (market-price :period 5 :nominal-rate 0.13 :real-rate 0.1 :real-benefit 120))))
  (testing "with effective accumulator and nominal-rate"
    (is (≈ 35 (market-price :period 5 :effective-accumulation-factor 1.23 :nominal-rate 0.13))))
  (testing "with effective accumulator, nominal-rate and agio"
    (is (≈ 1279 (market-price :period 5 :effective-accumulation-factor 1.23 :nominal-rate 0.13 :agio 35))))
  (testing "with real/nominal-rate"
    (is (≈ 1.3 (market-price :nominal-rate 0.13 :real-rate 0.1))))
  (testing "without parameters"
    (is (thrown? IllegalArgumentException (market-price)))))

(deftest real-rate-test
  (testing "with positive values"
    (is (≈ 0.1 (real-rate :market-price 800 :nominal-rate 0.08 :agio 10 :period 5)))))
