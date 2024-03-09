*<h1>Project "Pihta"</h1>*<br>
`Pihta` - compiled programming language
1. **Paradigms**
- Object Oriented Programming
- Imperative & Declarative
- Modular programming
2. **Typification**
- Static
- Weak
3. **Syntax similar to `Clojure`**
4. **Syntactic Sugar**
- ***Access to object/class members***

> (Java)
```java
a.b.c = 12
```
> (Fir)
```lisp
(set a/b/c 12)
```

- ***Extension functions***

> (Kotlin)
```kotlin
fun String.log() = println(this)
"Glory to Russia!".log()
```
> (Pihta)
```lisp
(efn ^String log ^void []
   (print this))
(#log "Glory to Russia!")
```

- ***Macros***

> (Lisp)
```lisp
(def-macro baza () (print "Glory to Russia!"))`
(baza)
```
> (Pihta)
```lisp
(def-macro base []
   (println "Glory to Russia!"))`
(baza)
```

- ***Convolution (*forms*/*instructions*)***

>(Clojure)
``` clojure
(-> 1 (+ 1) (* 2))
```
> (Pihta)
```lisp
(-> 1 (+ 1) (* 2))
```

- ***Form `cond`***
> (Lisp)
> *I didn't find an example that would work, sorry.*
> (Pihta)
```lisp
(def [[i -1]])
(cycle (<= i 1)
   (cond[
     [(< i 0) (println "Less!")]
     [(= i 0) (println "Zero!")]
     [(> i 0) (println "More!")]])
   (++ i))
```
5. ***Target Platforms***
- [x] JVM
- [ ] DotNet
- [ ] JavaScript
- [ ] Python
- [ ] Rave
- [X] C++