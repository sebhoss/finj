;
; Copyright © 2013 Sebastian Hoß <mail@shoss.de>
; This work is free. You can redistribute it and/or modify it under the
; terms of the Do What The Fuck You Want To Public License, Version 2,
; as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
;

(ns finj.periodic-payment-test
  (:require [finj.periodic-payment :refer :all]
            [com.github.sebhoss.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest due-value-test
  (testing "with positive values"
    (is (≈ 320 (due-value :amount 100 :rate 0.10 :period 3)))))

(deftest immediate-value-test
  (testing "with positive values"
    (is (≈ 310 (immediate-value :amount 100 :rate 0.10 :period 3)))))
