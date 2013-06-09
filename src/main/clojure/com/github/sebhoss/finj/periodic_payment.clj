(ns com.github.sebhoss.finj.periodic-payment
  (:require [com.github.sebhoss.finj.def :refer :all]))

(defnk due-payments [:amount :rate :period]
  (* amount (+ period
               (* rate (/ (inc period) 2)))))

(defnk immediate-payments [:amount :rate :period]
  (* amount (+ period
               (* rate (/ (dec period) 2)))))