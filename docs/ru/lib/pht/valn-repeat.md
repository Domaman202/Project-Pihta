## valn-repeat
Во время компиляции:
1. Повторяет `выражения`.
2. Собирает `valn` из повторённых `выражений`.

### Применение

1. `(valn-repeat i (expr0) (exprN))`<br>
`i` - _количество_.<br>
`expr` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (valn-repeat 3 (println "Слава России!"))))
```