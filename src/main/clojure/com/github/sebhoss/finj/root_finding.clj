(ns com.github.sebhoss.finj.root_finding
  "Root finding algorithms"
  (:require [com.github.sebhoss.finj.math :refer :all]))

(defrecord SearchResult
  "Record for results of the iter-search function."
  [result estimationError numberOfIterations prePredecessor predecessor])

(defn iter-search
  "Iterative search for a value using the last two values as inputs."
  [^IFn function, ^Number firstPrePredecessor, ^Number firstPredecessor, ^Number tolerance,
   ^Number maxIterations, ^IFn next-result, ^IFn next-lower, ^IFn next-upper]
  {:pre [(>= tolerance 0)
         (>= maxIterations 1)]
   :post [(< (abs (:estimationError %)) tolerance)
          (>= (:numberOfIterations %) 1)]}
  (loop [prePredecessor firstPrePredecessor predecessor firstPredecessor iteration 1]
    (if (<= iteration maxIterations)
      (let [value (next-result prePredecessor predecessor)
            estimationError (function value)
            result (SearchResult. value estimationError iteration prePredecessor predecessor)]
        (if (< (abs estimationError) tolerance)
          result
          (recur (next-lower result) (next-upper result) (inc iteration))))
      (throw (IllegalArgumentException. "max number of steps exceeded - could not satisfy tolerance")))))


(defn bisect
  "Root-finding method which repeatedly bisects an interval and then selects a subinterval in which a root must lie for
   further processing. It is a very simple and robust method, but it is also relatively slow. Because of this, it is
   often used to obtain a rough approximation to a solution which is then used as a starting point for more rapidly
   converging methods. The method is also called the binary search method or the dichotomy method.

   Parameters:
    * function        - The function to apply to each possible root.
    * lowerStartpoint - The lower bound of the initial search-interval.
    * upperStartpoint - The upper bound of the initial search-interval.
    * tolerance       - The maximum tolerance allowed for a possible root to become a root.
    * maxIterations   - The maximum numbers of iterations to run before throwing an exception."
  [function, lowerStartpoint, upperStartpoint, tolerance, maxIterations]
  {:pre [(< lowerStartpoint upperStartpoint)
         (cross-lt-gt (function lowerStartpoint) (function upperStartpoint) 0)]
   :post [(< (:prePredecessor %) (:predecessor %))]
   :see-also "http://en.wikipedia.org/wiki/Bisection_method"}
  (iter-search function lowerStartpoint upperStartpoint tolerance maxIterations
    ; function to calculate the next root
    #(mean [%1 %2])
    ; function to determine the next lower bound
    #(if (< (:estimationError %) 0) (:result %) (:prePredecessor %))
    ; function to determine the next upper bound
    #(if (< (:estimationError %) 0) (:predecessor %) (:result %))
    ))


(defn secant
  "The secant method is a root-finding method."
  [function, first, second, tolerance, maxIterations]
  {:see-also "http://en.wikipedia.org/wiki/Secant_method"}
  (iter-search function first second tolerance maxIterations
    ; function to calculate the next root
    #(let [a %1 b %2 fa (function a) fb (function b)]
       (- a (* (/ (- a b) (- fa fb)) fa)))
    ; function to determine the next predecessor
    #(:result %)
    ; function to determine the next pre-predecessor
    #(:prePredecessor %)
    ))

(defn newton
  "The newton method is a root-finding method."
  [function, derivative, minDenominator, startValue, tolerance, maxIterations]
  {:see-also "http://en.wikipedia.org/wiki/Newton%27s_method"}
  (iter-search function nil startValue tolerance maxIterations
    ; function to calculate the next root
    #(let [denominator (derivative %2)]
       (if (< (abs denominator) minDenominator)
         (throw (IllegalArgumentException. "denominator too small"))
         (- %2 (/ (function %2) denominator))))
    ; newton does not use a lower bound, so return nil, but still require one parameter for iter-search
    #(let [x %] nil)
    ; function to determine the predecessor in the next recursive call
    #(:result %)
    ))

(defn regula-falsi
  "The false position method or regula falsi method is a term for problem-solving methods in arithmetic, algebra, and
   calculus."
  [function, lowerStartpoint, upperStartpoint, tolerance, maxIterations]
  {:pre [(< lowerStartpoint upperStartpoint)
         (cross-lt-gt (function lowerStartpoint) (function upperStartpoint) 0)]
   :post [(< (:prePredecessor %) (:predecessor %))]
   :see-also "http://en.wikipedia.org/wiki/False_position_method"}
  (iter-search function lowerStartpoint upperStartpoint tolerance maxIterations
    ; function to calculate the next root
    #(let [a %1 b %2 fa (function a) fb (function b)]
       (- a (/ (* fa (- b a)) (- fb fa))))
    ; function to determine the next lower bound
    #(if (< (:estimationError %) 0) (:result %) (:prePredecessor %))
    ; function to determine the next upper bound
    #(if (< (:estimationError %) 0) (:predecessor %) (:result %))
    ))

(defn f [x]
  (+ (* x 2) 3))
(defn f1 [x]
  2)

(defn g [x]
  (+ (exp x 3) (* x 4)))
(defn g1 [x]
  (+ (* x 3) 4))

; => (bisect f -2 15 0.00001 100)
; => (secant g -2 15 0.00001 100)
