;
; This program is free software. It comes without any warranty, to
; the extent permitted by applicable law. You can redistribute it
; and/or modify it under the terms of the Do What The Fuck You Want
; To Public License, Version 2, as published by Sam Hocevar. See
; http://www.wtfpl.net/ for more details.
;

; Load project namespaces
(require '(com.github.sebhoss.finj [annuity :as annuity]
                                   [annuity-test :as annuity-test]
                                   [common :as common]
                                   [common-test :as common-test]
                                   [compound-interest :as compound-interest]
                                   [compound-interest-test :as compound-interest-test]
                                   [deprecation :as deprecation]
                                   [deprecation-test :as deprecation-test]
                                   [interest :as interest]
                                   [interest-test :as interest-test]
                                   [investment :as investment]
                                   [investment-test :as investment-test]
                                   [loan :as loan]
                                   [loan-test :as loan-test]
                                   [pension :as pension]
                                   [pension-test :as pension-test]
                                   [periodic-payment :as periodic-payment]
                                   [periodic-payment-test :as periodic-payment-test]
                                   [ratio :as ratio]
                                   [ratio-test :as ratio-test]
                                   [root-finding :as root-finding]
                                   [root-finding-test :as root-finding-test]
                                   [share-price :as share-price]
                                   [share-price-test :as share-price-test]))

; 'clojure.test', 'clojure.repl' and 'clojure.tools.namespace.repl' support
(require '[clojure.tools.namespace.repl :refer :all])
(require '[clojure.repl :refer :all])
(require '[clojure.test :refer :all])

; Call (rat) to run all tests, or (rat "math") to run only tests in that namespace
(defn rat
  ([] (run-all-tests #"com.github.sebhoss.finj.*-test"))
  ([namespace] (run-all-tests (re-pattern (format "com.github.sebhoss.finj.%s-test" namespace)))))
