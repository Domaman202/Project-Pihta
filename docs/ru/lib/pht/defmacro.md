## def-macro
Определяет _макрос_.

### Применение

1. `(def-macro name [arg0 argN] expr0 exprN)`<br>
`name` - _имя_.<br>
`arg0` `argN` - _аргументы_.<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (def-macro print-sum [a b]
            (println (+ (macro-arg a) (macro-arg b))))
        (print-sum 12 21)))
```