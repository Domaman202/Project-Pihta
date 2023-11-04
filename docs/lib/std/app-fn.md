## app-fn
Создаёт ___функцию__ приложения_ и выполяет в ней `выражения`.

### Применение

1. `(app-fn (expr0) (exprN))`<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println "Слава Советскому Союзу!")))
```

```pihta
(use-ctx pht
    (app
        (app-fn
            (println "Слава России!"))))
```