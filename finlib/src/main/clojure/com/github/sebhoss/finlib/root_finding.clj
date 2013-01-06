(ns com.github.sebhoss.finlib.root_finding)

(defn bisect
  "
   The bisection method in mathematics is a root-finding method which repeatedly bisects an interval
   and then selects a subinterval in which a root must lie for further processing. It is a very
   simple and robust method, but it is also relatively slow. Because of this, it is often used to
   obtain a rough approximation to a solution which is then used as a starting point for more rapidly
   converging methods. The method is also called the binary search method or the dichotomy method.

   Pseudocode:
    INPUT: Function f, endpoint values a, b, tolerance TOL, maximum iterations NMAX
		CONDITIONS: a < b, either f(a) < 0 and f(b) > 0 or f(a) > 0 and f(b) < 0
		OUTPUT: value which differs from a root of f(x)=0 by less than TOL

		N ← 1
		While N ≤ NMAX { limit iterations to prevent infinite loop
		  c ← (a + b)/2 new midpoint
		  If (f(c) = 0 or (b – a)/2 < TOL then { solution found
		    Output(c)
		    Stop
		  }
		  N ← N + 1 increment step counter
		  If sign(f(c)) = sign(f(a)) then a ← c else b ← c new interval
		}
		Output('Method failed.') max number of steps exceeded

   References:
     - http://en.wikipedia.org/wiki/Bisection_method
     - https://s3.amazonaws.com/torkian/torkian/Site/Research/Entries/2008/2/28_Root-finding_algorithm_Java_Code_(_Secant,_Bisection,_Newton_)_files/NO5A.java
  "

  [function, lowerStartpoint, higherStartpoint, tolerance, maxIterations]
    [function, lowerStartpoint, higherStartpoint, tolerance, maxIterations]
)

(defn secant
  "
   The secant method is a root-finding method.

   Matlab code:
    f=@(x)x^2-612;
		x(1)=10;
		x(2)=30;
		for i=3:7
		    x(i) = x(i-1) - (f(x(i-1)))*((x(i-1) - x(i-2))/(f(x(i-1)) - f(x(i-2))));
		end
		root=x(7)

   References:
     - http://en.wikipedia.org/wiki/Secant_method
     - https://s3.amazonaws.com/torkian/torkian/Site/Research/Entries/2008/2/28_Root-finding_algorithm_Java_Code_(_Secant,_Bisection,_Newton_)_files/NO6A.java
  "
  [function]
    [function]
)

(defn newton
  "
   The newton method is a root-finding method.

   References:
     - http://en.wikipedia.org/wiki/Newton%27s_method
     - https://s3.amazonaws.com/torkian/torkian/Site/Research/Entries/2008/2/28_Root-finding_algorithm_Java_Code_(_Secant,_Bisection,_Newton_)_files/NO7.java
  "
  [function]
    [function]
)