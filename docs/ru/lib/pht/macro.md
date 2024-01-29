## macro
Вставка _макроса_.

### Применение

1. `(name arg0 argN)`<br>
`name` - _имя_.<br>
`arg0` `argN` - _аргументы_.<br><br>
2. `(macro name arg0 argN)`<br>
`name` - _имя_.<br>
`arg0` `argN` - _аргументы_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (def-macro print-sum [a b]
            (println (- (macro-arg a) (macro-arg b))))
        (print-sum 3333 1111)))
```

```pihta
(use-ctx pht
    (app-fn
        (def-macro print-sum [a b]
            (println (- (macro-arg a) (macro-arg b))))
        (macro print-sum 213 202)))
```