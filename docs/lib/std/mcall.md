## mcall
Вызывает метод.

### Применение

1. `(mcall ^type name arg0 argN)`<br>
`^type` - _класс_.<br>
`name` - _имя_.<br>
`arg0` `argN` - _аргументы_.<br><br>
2. `(mcall instance name arg0 argN)`<br>
`instance` - _объект_.<br>
`name` - _имя_.<br>
`arg0` `argN` - _аргументы_.<br><br>

3. `(#name ^type arg0 argN)`<br>
`^type` - _класс_.<br>
`name` - _имя_.<br>
`arg0` `argN` - _аргументы_.<br><br>
4. `(#name instance arg0 argN)`<br>
`instance` - _объект_.<br>
`name` - _имя_.<br>
`arg0` `argN` - _аргументы_.<br><br>
5. `(#name . arg0 argN)`<br>
`.` - ___текущий класс или объект___.<br>
`name` - _имя_.<br>
`arg0` `argN` - аргументы.

### Примеры

```pihta
(use-ctx pht
    (app
        (defn foo ^void []
            (println "Foo!"))
        (app-fn
            (mcall ^App foo))))
```

```pihta
(use-ctx pht
    (app
        (defn foo ^void []
            (println "Foo!"))
        (app-fn
            (#foo ^App))))
```

```pihta
(use-ctx pht
    (cls Test [^Object]
        (ctor [] (ccall))
        (defn foo ^void []
            (println "Foo!")))
    (app-fn
        (mcall (new ^Test) foo)))
```

```pihta
(use-ctx pht
    (cls Test [^Object]
        (ctor [] (ccall))
        (defn foo ^void []
            (println "Foo!")))
    (app-fn
        (#foo (new ^Test))))
```

```pihta
(use-ctx pht
    (app
        (defn fib ^int [[n ^int]]
            (if (<= n 1)
                n
                (+  (#fib . (- n 2))
                    (#fib . (- n 1)))))
        (app-fn
            (println (#fib . 10)))))
```