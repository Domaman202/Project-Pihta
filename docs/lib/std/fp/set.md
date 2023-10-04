## set
Устанавливает `знание` `выражения` `переменной`/`полю`.

### Применение

1. `(set i (expr))`<br>
`i` - _переменная_/_поле_ (поле относится к `this`).<br>
`(expr)` - _выражение_.<br><br>
2. `(set this/i (expr))`<br>
`this/i` - _поле_ `i` принадлежащее _переменной_/_полю_ `this`.<br>
`(expr)` - _выражение_.

### Примеры

```pihta
(def [[i 12]])
(set i 21)
```

```pihta
(use-ctx std/base
    (obj App [^java.lang.Object] (
        (field [[i ^int]])
        (defn main ^void []
            (set this/i 33)))))
```