## set
Устанавливает `значение` в `переменную`/`поле`

### Применение

1. `(set vof val)`<br>
`vof` - _переменная_/_поле_.<br>
`val` - _значение_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (def [[^int i]])
        (set i 12)
        (println i)))
```

```pihta
(use-ctx pht
    (app
        (def [[j ^int]])
        (app-fn
            (set ^App/j 21)
            (println ^App/j))))
```