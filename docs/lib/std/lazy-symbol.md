## lazy-symbol
Собирает символ из нескольких `значений`.<br>
Собирает строку из нескольких `значений` (во время компиляции).
В случае использования в макросе - вычисляется __один раз__.

### Применение

1. `(symbol val0 valN)`<br>
`val0` `valN` - _значения_.

### Примеры

```pihta
(use-ctx pht
    (defmacro test []
        (valn-repeat 10
            (println (symbol (rand-symbol)))))
    (defmacro test-lazy []
        (valn-repeat 10
            (println (lazy-symbol (rand-symbol)))))
    (app-fn
        (println "Простой:\n")
        (test)
        (println "\nЛенивый:\n")
        (test-lazy)))
```