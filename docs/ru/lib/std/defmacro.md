## defmacro
Определяет _макрос_.

### Применение

1. `(defmacro name [arg0 argN] expr0 exprN)`<br>
`name` - _имя_.<br>
`arg0` `argN` - _аргументы_.<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (defmacro print-sum [a b]
            (println (+ (macro-arg a) (macro-arg b))))
        (print-sum 12 21)))
```