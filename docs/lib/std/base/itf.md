## itf
Определяет `интерфейс`.

### Применение</h4>

1. `(itf ITest [^IFoo])`<br>
`ITest` - _имя_.<br>
`IFoo` - _супер-интерфейс_.
2. `(itf ITest [^IFoo] (expr))`<br>
`ITest` - _имя_.<br>
`IFoo` - _супер-интерфейс_.<br>
`(expr)` - _тело_.

### Примеры

```pihta
(use-ctx std/base
    (itf IFoo []
        (defn foo ^void [] (unit))))
```