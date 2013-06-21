(ns com.github.sebhoss.finj.deprecation-test
  (:require [com.github.sebhoss.finj.deprecation :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest straight-line-annual-expense-test
  (testing "with positive integers"
    (is (= 180 (straight-line-annual-expense :fixed-asset 1000 :residual-value 100 :period 5)))
    (is (= 225 (straight-line-annual-expense :fixed-asset 2000 :residual-value 200 :period 8)))
    (is (= 225 (straight-line-annual-expense :fixed-asset 3000 :residual-value 300 :period 12)))))
