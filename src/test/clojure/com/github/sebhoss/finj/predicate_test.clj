(ns com.github.sebhoss.finj.predicate-test
  (:require [com.github.sebhoss.finj.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest fuzzy-eq?-test
  (testing "with positive integers"
    (is (fuzzy-eq? 1 2 3)))
  (testing "with negative integers"
    (is (fuzzy-eq? -1 -2 3)))
  (testing "with zero"
    (is (fuzzy-eq? 0 0 1))))

(deftest fuzzy-zero?-test
  (testing "with positive integers"
    (is (fuzzy-zero? 1 2)))
  (testing "with negative integers"
    (is (fuzzy-zero? -1 2)))
  (testing "with zero"
    (is (fuzzy-zero? 0 2))))