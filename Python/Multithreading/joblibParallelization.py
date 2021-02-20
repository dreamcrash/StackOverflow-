/** Original Question : https://stackoverflow.com/questions/65206662

How to use joblib parallelization with in-class methods that don't return anything

I am currently trying to implement a parallel for loop using joblib in python 3.8.3.

In the for loop I want to apply a class method to an instance of 
one class while applying a method in another. This is a MWE that 
I did to try and see if my idea works, but it does not. Does anyone have any idea how to get this to work?

**/

from joblib import Parallel, delayed


class A:
    def __init__(self):
        self.val = 0

    def add5(self):
        self.val += 5


class B:
    def __init__(self):
        self.obj = [A() for _ in range(10)]

    def apply(self):
        """ this is where I'm trying to use joblib:
        for a in self.obj:
            a.add5()"""

        def f(x):
            x.add5()
            return x

        self.obj = Parallel(n_jobs=-1)(delayed(f)(x) for x in self.obj)

    def prnt(self):
        print([a.val for a in self.obj])


b = B()
b.prnt()  # returns [0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
b.apply()
b.prnt()  # returns [0, 0, 0, 0, 0, 0, 0, 0, 0, 0] but
          # I expect [5, 5, 5, 5, 5, 5, 5, 5, 5, 5]
