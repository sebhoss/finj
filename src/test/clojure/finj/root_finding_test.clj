;
; Copyright © 2013 Sebastian Hoß <mail@shoss.de>
; This work is free. You can redistribute it and/or modify it under the
; terms of the Do What The Fuck You Want To Public License, Version 2,
; as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
;

(ns finj.root-finding-test
  (:require [finj.root-finding :refer :all]
            [com.github.sebhoss.predicate :refer :all]
            [clojure.test :refer :all]))

(defn- f [x]
  (- (* x 2) 10))
(defn- f1 [x]
  (+ (* x 0) 2))

(deftest bisect-test
  (testing "with positive values"
    (is (≈ 5 (:estimate
               (bisect 
                 :function f
                 :lower-startpoint 0
                 :upper-startpoint 15))))))

(deftest secant-test
  (testing "with positive values"
    (is (≈ 5 (:estimate
               (secant
                 :function f
                 :first 0
                 :second 15))))))

(deftest newton-test
  (testing "with positive values"
    (is (≈ 5 (:estimate
               (newton
                 :function f
                 :derivative f1
                 :min-denominator 0.1
                 :start-value 15))))))

(deftest regula-falsi-test
  (testing "with positive values"
    (is (≈ 5 (:estimate
               (regula-falsi
                 :function f
                 :lower-startpoint 0
                 :upper-startpoint 15))))))
