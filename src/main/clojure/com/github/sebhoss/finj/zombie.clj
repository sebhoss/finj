(ns com.github.sebhoss.finj.zombie)

(defmacro filter-map [bindings pred m]
  `(select-keys ~m
    (for [~bindings ~m
      :when ~pred]
      ~(first bindings))))
; user=> (filter-map [key val] (even? (:attr val)) {:a {:attr 2} :b {:attr 3} :c {:attr 4}})
; {:c {:attr 4}, :a {:attr 2}}
; from http://stackoverflow.com/questions/2753874/how-to-filter-a-persistent-map-in-clojure