## as-gens
Приводит `значение` к `типу` и выполняет подстановку `generic-ов` в `выражение`.

### Применение

1. `(as-gens type0 expr0 type1 typeN)`<br>
`type0` - _тип_.<br>
`expr0` - _выражение_.<br>
`type1` `typeN` - _generic-и_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (def [[list (as-gens ^java.util.List (new ^java.util.ArrayList) ^String)]])
        (#add list "Hi!")
        (println (typeof list))
        (println (typeof (#get list 0))))
```