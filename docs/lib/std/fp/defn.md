## defn
Определяет `функцию`.

### Применение

1. `(defn foo ^void [[i ^int]] (unit))`<br>
`log` - _имя функции_.<br>
`^void` - _возвращаемый тип_.<br>
`[[i ^int]]` - _аргументы_.<br>
`(unit)` - _тело_.

### Примеры

```pihta
(use-ctx std/base
    (cls App [^java.lang.Object] (@static
        (defn foo ^void [[i ^int]]
            (#println (use std/base) "Foo!" i))
        (defn main ^void []
            (#foo ^App 333)))))
```