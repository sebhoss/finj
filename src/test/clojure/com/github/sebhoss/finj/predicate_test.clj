;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.

(ns com.github.sebhoss.predicate-test
  (:require [com.github.sebhoss.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest ≈-test
  (testing "with positive integers"
    (is (≈ 1 2 3)))
  (testing "with negative integers"
    (is (≈ -1 -2 3)))
  (testing "with zero"
    (is (≈ 0 0 1)))
  (testing "with 2 parameters (default epsilon = 1)"
    (is (≈ 1.2 1.5))))

(deftest ≈0-test
  (testing "with positive integers"
    (is (≈0 1 2)))
  (testing "with negative integers"
    (is (≈0 -1 2)))
  (testing "with zero"
    (is (≈0 0 2)))
  (testing "with 1 parameter (default epsilon = 1)"
    (is (≈0 0.5))))