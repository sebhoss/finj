;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See "http://www.wtfpl.net/":http://www.wtfpl.net/ for
;   more details.

(ns com.github.sebhoss.finj.common-test
  (:require [com.github.sebhoss.finj.common :refer :all]
            [clojure.test :refer :all]))

(deftest rate-test
  (testing "with integers"
    (is (= 1/10 (rate :rate-per-cent 10))))
  (testing "with floats"
    (is (= 0.125 (rate :rate-per-cent 12.5)))))

(deftest accumulation-factor-test
  (testing "with integers"
    (is (= 2 (accumulation-factor :rate 1))))
  (testing "with floats"
    (is (= 2.0 (accumulation-factor :rate 1.0))))
  (testing "with ratios"
    (is (= 5/4 (accumulation-factor :rate 1/4)))))
