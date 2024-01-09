## with-gens
Выполняет подстановку `generic-ов` в `выражение`.

### Применение

1. `(with-gens expr0 type0 typeN)`<br>
`expr0` - _выражение_.<br>
`type0` `typeN` - _generic-и_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (def [[list (with-gens (new ^java.util.ArrayList) ^String)]])
        (#add list "Hi!")
        (println (typeof (#get list 0)))))
```