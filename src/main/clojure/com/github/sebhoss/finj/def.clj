;   This program is free software. It comes without any warranty, to the extent permitted by applicable law.
;   You can redistribute it and/or modify it under the terms of the Do What The Fuck You Want To Public
;   License, Version 2, as published by Sam Hocevar. See http://www.wtfpl.net/ for more details.

(ns com.github.sebhoss.finj.def)

(defn- is-condition-map? [form]
  (and (map? form) (or (:pre form) (:post form))))
 
(defn contains-keys? [m ks]
  (every? #(contains? m %) ks))
 
(defn- split-args
  "(split-args [:a :b :c :opt :d :e :f :opt-def :g 3 :h 4 :i 5])
   => {:opt-def {:g 3, :i 5, :h 4}, :opt [:d :e :f], :obligatory [:a :b :c]}"
  [args] 
  (loop [args args
         current :obligatory
         result {:obligatory [] :opt [] :opt-def []}]
    (if (empty? args) 
      (assoc
        (select-keys result [:obligatory :opt])
        :opt-def (apply hash-map (:opt-def result)))
      (let [curr (first args)
            state (case curr 
                    :opt :opt
                    :opt-def :opt-def
                    current)]
        (recur
          (rest args)
          state
          (if (not= state current)
            result
            (update-in result [state] conj curr)))))))
 
(defmacro defnk
  "Warning: Binds \"m\" to the args-map.
  ks-args are obligatory keys until a :opt or :opt-def; then optional keys as an
  alternating sequence of keywords and defaults values are expected.
  Asserts the obligatory keys are there and all keys are obligatory/optional in the arg-map.
  Allows a docstring after the fn-name and a condition-map." 
  [fn-name & more]
  (let [f                                (first more)
        docstring                        (if (string? f) f)
        more                             (if docstring (rest more) more)
        ks-args                          (first more)
        body                             (rest more)
        condition-map                    (if (is-condition-map? (first body)) (first body))
        body                             (if condition-map (rest body) body)        
        {:keys [obligatory opt opt-def]} (split-args ks-args)
        sym-vals                         (into {} (for [[k v] opt-def]
                                                    [(symbol (name k)) v]))
        all-ks                           (vec (concat obligatory opt (keys opt-def)))
        ks-as-symbols                    (map #(symbol (name %)) all-ks)
        check-ks-precond                 `{:pre [(contains-keys? ~'m ~(vec obligatory))
                                                 (every? #(some #{%} ~(vec all-ks)) (keys ~'m))]}] 
    `(defn
       ~(with-meta fn-name {:doc docstring})
       [& {:keys [~@ks-as-symbols] :or ~sym-vals :as ~'m}]
       ~(merge-with #(vec (concat %1 %2))
          check-ks-precond
          condition-map)
       ~@body)))
