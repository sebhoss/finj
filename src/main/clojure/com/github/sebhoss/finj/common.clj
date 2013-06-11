(ns com.github.sebhoss.finj.common
  (:require [com.github.sebhoss.finj.def :refer :all]))

(defnk rate [:rate-per-cent]
  (/ rate-per-cent 100))

(defnk accumulation-factor [:rate]
  (inc rate))
