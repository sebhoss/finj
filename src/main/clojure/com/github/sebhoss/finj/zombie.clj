(ns com.github.sebhoss.finj.zombie)

(defmacro filter-map [bindings pred m]
  `(select-keys ~m
    (for [~bindings ~m
      :when ~pred]
      ~(first bindings))))
; user=> (filter-map [key val] (even? (:attr val)) {:a {:attr 2} :b {:attr 3} :c {:attr 4}})
; {:c {:attr 4}, :a {:attr 2}}
; from http://stackoverflow.com/questions/2753874/how-to-filter-a-persistent-map-in-clojure

; name-with-attributes by Konrad Hinsen:
(defn name-with-attributes
  "To be used in macro definitions.
   Handles optional docstrings and attribute maps for a name to be defined
   in a list of macro arguments. If the first macro argument is a string,
   it is added as a docstring to name and removed from the macro argument
   list. If afterwards the first macro argument is a map, its entries are
   added to the name's metadata map and the map is removed from the
   macro argument list. The return value is a vector containing the name
   with its extended metadata map and the list of unprocessed macro
   arguments."
  [name macro-args]
  (let [[docstring macro-args] (if (string? (first macro-args))
                                 [(first macro-args) (next macro-args)]
                                 [nil macro-args])
    [attr macro-args]          (if (map? (first macro-args))
                                 [(first macro-args) (next macro-args)]
                                 [{} macro-args])
    attr                       (if docstring
                                 (assoc attr :doc docstring)
                                 attr)
    attr                       (if (meta name)
                                 (conj (meta name) attr)
                                 attr)]
    [(with-meta name attr) macro-args]))

; defnk by Meikel Brandmeyer:
(defmacro defnk
 "Define a function accepting keyword arguments. Symbols up to the first
 keyword in the parameter list are taken as positional arguments.  Then
 an alternating sequence of keywords and defaults values is expected. The
 values of the keyword arguments are available in the function body by
 virtue of the symbol corresponding to the keyword (cf. :keys destructuring).
 defnk accepts an optional docstring as well as an optional metadata map."
 [fn-name & fn-tail]
 (let [[fn-name [args & body]] (name-with-attributes fn-name fn-tail)
       [pos kw-vals]           (split-with symbol? args)
       syms                    (map #(-> % name symbol) (take-nth 2 kw-vals))
       values                  (take-nth 2 (rest kw-vals))
       sym-vals                (apply hash-map (interleave syms values))
       de-map                  {:keys (vec syms)
                                :or   sym-vals}]
   `(defn ~fn-name
      [~@pos & options#]
      (let [~de-map (apply hash-map options#)]
        ~@body))))


;(defnk myfunc [:a :b 5 :c] (+ a b c))

;    // Efficient method for computing the arithmetic mean.
;    // The alternative (x + y) / 2 fails for large values.
;    // The alternative (x + y) >>> 1 fails for negative values.
;    return (x & y) + ((x ^ y) >> 1);
;(defn mean [x y]
;  (+ (bit-and x y)
;     (bit-shift-right (bit-xor x y) 1)))
