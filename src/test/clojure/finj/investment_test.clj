;
; Copyright © 2013 Sebastian Hoß <mail@shoss.de>
; This work is free. You can redistribute it and/or modify it under the
; terms of the Do What The Fuck You Want To Public License, Version 2,
; as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
;

(ns finj.investment-test
  (:require [finj.investment :refer :all]
            [com.github.sebhoss.predicate :refer :all]
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

(deftest equivalent-annual-cost-test
  (testing "the example from wikipedia"
    (is (≈ 31360 (equivalent-annual-cost :investment 50000 :period 3 :maintenance 13000 :rate 0.05)))
    (is (≈ 30708 (equivalent-annual-cost :investment 150000 :period 8 :maintenance 7500 :rate 0.05)))))
