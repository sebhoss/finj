(ns com.github.sebhoss.finj.investment)

(defn 	annual-depreciation 
  "
   Calculates the depreciation-on-a-straight-line-basis of a time series in Years.

   Options:
     :annual-depreciation - Jährliche Abschreibung
     :physical-depreciation - Wertminderung.
     :original-value  - Anfangswert.
     :book-value - Buchwert
     :declining-balance - Rest-Buchwert
     :economic-life - Nutzungsdauer

   Examples:
     - (annual-depreciation :original-value 1000 :declining-balance 10 :economic-life 5)

   References:
     
  "

  [{:keys [original-value declining-balance economic-life]}]
    (/ (- original-value declining-balance) economic-life)
)

(defn accumulated-depreciation
  "
   Calculates the accumulated depreciation of goods.

   References:
     - http://
  "

  [{:keys [ economic-life physical-deprecaioation]}]
    (* economic-life physical-deprecaioation)
)
(defn book-value-based-on-years
  "
   Calculates the book-value after a period of time in book-value-based-on-years
  
  "
  [{:keys [ original-value years physical-depreciation]}]
   (- original-value (* years physical-depreciation))
)