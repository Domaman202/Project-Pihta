## unparse
Преобразует _обработанные_ `выражения` в строку _де-парсинга_.

### Применение

1. `(unparse expr0 exprN)`<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (unparse (println "Казахстан угрожает нам бомбардировкой!")))))
```