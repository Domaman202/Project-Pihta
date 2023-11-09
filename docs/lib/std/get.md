## get
Получает значение `переменной`.

### Применение

1. `(get var)`<br>
`var` - _переменная_.<br><br>
2. `var`<br>
`var` - _переменная_.

### Примеры


```pihta
(use-ctx pht
    (app-fn
        (def [[i 33]])
        (println i))))
```

```pihta
(use-ctx pht
    (app-fn
        (def [[i 44]])
        (println (get i)))))
```