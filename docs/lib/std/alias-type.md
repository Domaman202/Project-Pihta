## alias-type
Создаёт `псевдоним` для `типа`.

### Применение

1. `(alias-type Type Alias)`<br>
`Type` - _тип_.<br>
`Alias` - _псевдоним_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (alias-type java.lang.Integer i32)
        (println ^i32))
```
