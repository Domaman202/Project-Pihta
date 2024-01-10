## roll-right
Свертывает `выражения` __справо-налево__.

### Применение

1. `(roll-right (expr0) (exprN))`<br>
`(expr0)` `(exprN)` - _выражения_.<br><br>
2. `(<- (expr0) (exprN))`<br>
   `(expr0)` `(exprN)` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (roll-right 1 (+ 1) (* 2)))))
```

```pihta
(use-ctx pht
    (app-fn
        (println (<- 1 (+ 1) (* 2)))))
```