## defn
`Декларирует` `функцию`.

### Применение

1. `(defn foo ^void [[i ^int]])`<br>
`log` - _имя функции_.<br>
`^void` - _возвращаемый тип_.<br>
`[[i ^int]]` - _аргументы_.<br>

### Примеры

```pihta
(use-ctx std/decl std/base
    (cls App [^java.lang.Object]
        (@static (defn main ^void []))))
```