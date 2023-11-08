## alias-type
Создаёт `псевдоним` для `типа`.

### Применение

1. `(alias-type type alias)`<br>
`type` - _тип_.<br>
`alias` - _псевдоним_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (alias-type java.lang.Integer i32)
        (println ^i32))
```
