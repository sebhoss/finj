;
; Copyright © 2013 Sebastian Hoß <mail@shoss.de>
; This work is free. You can redistribute it and/or modify it under the
; terms of the Do What The Fuck You Want To Public License, Version 2,
; as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.
;

(ns finj.common
  (:require [com.github.sebhoss.def :refer :all]))

(defnk rate [:rate-per-cent]
  (/ rate-per-cent 100))

(defnk accumulation-factor [:rate]
  (inc rate))
