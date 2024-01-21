## ctor
Определяет конструктор.

### Применение

1. `(ctor [[arg0 ^type0] [argN ^typeN]] (expr0) (exprN))`<br>
`[[arg0 ^type0] [argN ^typeN]]` - _аргументы_.<br>
`(expr0)` `(exprN)` - _тело_.

### Примеры

```pihta
(use-ctx pht
    (cls Test [^Object]
        (ctor [[i ^int] [j ^double]]
            (ccall)
            (println i)
            (println j)))
    (app-fn
        (println (new ^Test 12 21.33))))
```