## use-ctx
Подключает `модули` в __локальный контекст__.<br>
Последовательно выполняет `выражения` в __локальном контексте__.<br>
Не поддерживает макросы.

### Применение

1. `(use-ctx module0 moduleN (expr0) (exprN))`<br>
`module0` `moduleN` - _модули_.<br>
`(expr0)` `(exprN)` - _выражения_.

### Примеры

```pht
(use-ctx pht
    (app-fn
        (println "Здравствуй,")
        (println "Товарищ!")))
```