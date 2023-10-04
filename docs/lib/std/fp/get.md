## get
Получает `значение` `переменной`.

### Применение

1. `(get i)`<br>
`i` - _переменная_.

### Примеры

```pihta
(def [[i 12]])
(#println (use std/base) (get i))
```

## get!
Получает `значение` `переменной`/`поля`.

### Применение

1. `i`<br>
`i` - _переменная_/_поле_ (относится к `this`).<br><br>
2. `this/i`<br>
`this/i` -  _поле_ `i` принадлежащее _переменной_/_полю_ `this`.

### Примеры

```pihta
(def [[i 12]])
(#println (use std/base) i)
```

```pihta
(use-ctx std/base
    (obj App [^java.lang.Object] (
        (field [[i ^int]])
        (defn main ^void [] (
            (set this/i 33)
            (#println (use std/base) this/i))))))
```