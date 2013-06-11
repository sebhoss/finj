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
