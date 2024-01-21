## alias-type
Создаёт `псевдоним` для `типа`.

### Применение

1. `(alias-type [[type0 alias0][typeN aliasN]])`<br>
`type` - _тип_.<br>
`alias` - _псевдоним_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (alias-type [[java.lang.Integer i32]])
        (println ^i32)))
```
