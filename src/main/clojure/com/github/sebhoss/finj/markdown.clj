(ns com.github.sebhoss.finj.investment)
"
 1-2 Lineare Abschreibung 
 2-6 Arithmetisch-degressive Abschreibung
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



  