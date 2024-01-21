## generics


### Примеры

```pihta
(progn
    (cls Test [^Object]
        (ctor [] (ccall))
        (defn [[T ^Object]] foo T^ [[o T^]] o))
    (app-fn
        (println (typeof (#foo (new ^Test<^String>) "Test!")))))
```

```pihta
(progn
    (cls Test [^Object]
        (ctor [] (ccall))
        (defn [[T ^Object]] foo T^ [[o T^]] o))
    (app-fn
        (println (typeof (#foo<^String> (new ^Test) "Test!")))))
```

```pihta
(progn
    (cls [[T ^Object]] Test [^Object]
        (ctor [] (ccall))
        (defn foo T^ [[o T^]] o))
    (app-fn
        (println (typeof (#foo (new ^Test<^String>) "Test!")))))
```

```pihta
(progn
    (cls [[T ^Object]] Test [^Object]
        (ctor [] (ccall))
        (defn foo T^ [[o T^]] o))
    (app-fn
        (println (typeof (#foo<^String> (new ^Test) "Test!")))))
```