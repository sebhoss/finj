;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See "http://www.wtfpl.net/":http://www.wtfpl.net/ for
;   more details.

;;; Load all finj namespaces
;; 0. Require dependencies
(require '[clojure.java.classpath :refer [classpath]])
(require '[clojure.tools.namespace.find :refer [find-namespaces]])
(require '[clojure.string :refer [blank? split]])

;; 1. Find all namespaces inside this project and all dependencies on the classpath
(defn finj-namespace? [namespace]
  (not (blank? (re-find #".*?finj" (.toString namespace)))))

(def finj-namespaces
  (find-namespaces (filter finj-namespace? (classpath))))

;; 2. Require every namespace with its name as alias
(defn ns-alias [namespace]
  (symbol (last (split (.toString namespace) #"\."))))

(defn ns-alias-split [namespace]
  (vector namespace (ns-alias namespace)))

(def finj-namespace-alias
  (map ns-alias-split finj-namespaces))

(defn require-namespace [[namespace alias]]
  (require (vector namespace :as alias)))

(defn load-finj-namespaces []
  (when (empty? (remove nil?
                  (map require-namespace finj-namespace-alias)))
      :ok))

;; 3. Load finj namespaces (+ create alias for load-finj-namespaces)
(def lfn (load-finj-namespaces))


;;; 'refresh' support
(require '[clojure.tools.namespace.repl :refer [refresh]])


;;; 'doc', 'source', etc. support
(require '[clojure.repl :refer :all])


;;; Alias function which runs all tests inside the finj project
(require '[clojure.test :as test])

(defn rat
  "[R]uns-[A]ll-[T]ests inside the whole project or in a separate namespace.

   Examples:
     * (rat)          - Run all tests in all project namespaces
     * (rat \"common\") - Run all tests in the common/-namespace
     * (rat \"math\")   - Run all tests in the math/-namespace"
  ([] (test/run-all-tests #"com.github.sebhoss.finj.*-test"))
  ([namespace] (test/run-all-tests
                 (re-pattern (format "com.github.sebhoss.finj.%s-test"
                                     namespace)))))
