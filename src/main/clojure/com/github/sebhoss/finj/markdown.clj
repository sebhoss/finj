(ns com.github.sebhoss.finj.investment)
(:require [com.github.sebhoss.finj.math :refer :all]
            [clojure.test :refer :all]))

"
 1-2 Lineare Abschreibung 
 2-6 Arithmetisch-degressive Abschreibung
 7-9 Geometrisch-degressive Abschreibung - the geometrical-degressive depreciation
"

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

(defn depreciation-in-the-year-k
  "
   Calculates teh annual-depreciation of just one year, the year k.
    -annual-amount-of-the-reduction - Reduktionsbetrag der Abschreibung
  "
  
  [{:keys [physical-depreciation year annual-amount-of-the-reduction ]}]
    (- physical-depreciation (*(inc year) annual-depreciation))
 )

(defn annual-amount-of-the-reduction
  "
   Calculates the annual amount of the reduction
  "
  [{:keys [economic-life  physical-depreciation original-value declining-balance]}]
     (* 2 (/(-(* economic-life physical-depreciation)(- original-value declining-balance))(* economic-life(inc economic-life))))
)

(defn digital-depreciation-in-the-year-k
  "
   Calculates the digital-annual-depreciation of just one year, the year k.
    -annual-amount-of-the-reduction - Reduktionsbetrag der Abschreibung
  "
  
  [{:keys [economic-life year annual-amount-of-the-reduction ]}]
    (* annual-depreciation (-(dec year) economic-life))
 )

(defn digital-annual-amount-of-the-reduction
  "
   Calculates the digital annual amount of the reduction
  "
  [{:keys [economic-life  physical-depreciation original-value declining-balance]}]
     (* 2 (/(* 2 (- original-value declining-balance))(* economic-life(+ 1 economic-life))))
)
  
(defn geometrical-depreciation-in-the-year-k
  "
   Calculates the digital-annual-depreciation of just one year, the year k.
    -annual-amount-of-the-reduction - Reduktionsbetrag der Abschreibung
  "
  
  [{:keys [original-value years percentage-of-depreciation ]}]
    (* original-value (Math/pow (inc (/ percentage-of-depreciation 100)) years))
 )
   
 ( defn percentage-of-depreciation 
   "
    Calculates the percentage-of-depreciation.
   "
   
   [{:keys [ declining-balance original-value economic-life]}]
     (* 100 (inc (Math/pow (/ declining-balance original-value)(/ 1.0 economic-life))))
        
  )
 
 (defn geometrical-depreciation-in-the-year-k
  "
   Calculates the geometrical-annual-depreciation of just one year, the year k.
    -annual-amount-of-the-reduction - Reduktionsbetrag der Abschreibung
  "
  
  [{:keys [original-value percentage-of-depreciation year ]}]
    (* original-value (/ percentage-of-depreciation 100) (Math/pow (inc (/ percentage-of-depreciation 100))(inc year)))
 )
   