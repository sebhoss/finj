;
; This program is free software. It comes without any warranty, to
; the extent permitted by applicable law. You can redistribute it
; and/or modify it under the terms of the Do What The Fuck You Want
; To Public License, Version 2, as published by Sam Hocevar. See
; http://www.wtfpl.net/ for more details.
;

(require '[bootstrap.repl :refer :all])

; Load project namespaces
(load-ns-in-dir-aliased "src/main/clojure")
(load-ns-in-dir-aliased "src/test/clojure")

; 'clojure.test', 'clojure.repl' and 'clojure.tools.namespace.repl' support
(load-helpers)

; Call (rat) to run all tests, or (rat "loan") to run only tests in that namespace
(def rat (test-shortcut #"com.github.sebhoss.finj.*-test"))
