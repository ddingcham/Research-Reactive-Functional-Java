# Imperative vs declarative  
> Given a number of items, it returns a number of their corresponding  
> [link](https://medium.com/@FranRiadigos/functional-reactive-programming-with-rxjava-part-1-dc33aa9b5492)  


#### side effect // java -> instance reference / call by reference  
> A function or expression is said to have a side effect  
> if it modifies some state  
> or has an observable interaction 
> with calling functions or the outside world  

* when you modify a global or a static variable outside the scope of a class  
* when the result or the own execution of a function is different due to the  
  use of an external variable, for instance a state,  
* when an exception is thrown and changes the expected behaviour of a program...

#### What difference a functional approach makes ?  
> some rules in a pure functional programming style.  
> pure function : no side effect  
> // Haskell is supposed to be a pure functional language  
> 이상적인 룰임 (현실 X)  

* You cannot use loop statements  
* All functions must have only one parameter.  
* Each function contains only one single expression  
* There must not be any side effect.  
* All variables must remain **immutable**  
* Operations on a data structure cannot be destructive  
* The execution order does not matter
* You must use static typing  
  > don't have states ?  

#### Dealing with RxJava and Third Party Libraries  
> general, good practice : wrapping external libraries(Third Part Libraries)  
> in Wrapper (ex : RxJava)      

* Loose Coupling classes from the third party library  
* Independent from an external contract or an interface  
* Ability to switch to another library easily without any side effect  
* The wrapper is the only one which implements the changes  
* **Easy to test by creating our own mocks**

#### Conclusion (DeclarativeWithObservable.java)  
