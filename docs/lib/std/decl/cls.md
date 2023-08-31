## cls
`Декларирует` `класс`.

### Применение

1. `(cls Test [^Any])`<br>
`Test` - _имя_.<br>
`Any` - _супер-класс_.
2. `(cls Test [^Any ^IFoo] (expr))`<br>
`Test` - _имя_.<br>
`Any` - _супер-класс_.<br>
`IFoo` - _реализуемый интерфейс_.<br>
`(expr)` - _тело_.

### Примеры

```pihta
(use-ctx std/decl std/base
    (cls App [^java.lang.Object]
        (defn main ^void [])
```