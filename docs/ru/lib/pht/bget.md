## bget
Получает значение `переменной` из `блока`.

### Применение

1. `(bget var block)`<br>
`var` - _переменная_.<br>
`block` - _блок_.<br><br>
2. `var@block`<br>
`var` - _переменная_.<br>
`block` - _блок_.

### Примеры


```pihta
(use-ctx pht
    (app-fn
        (named-block first
            (def [[i 12]])
            (named-block second
                (def [[i 21]])
                (println (bget i first))))))
```

```pihta
(use-ctx pht
    (app-fn
        (named-block first
            (def [[i 12]])
            (named-block second
                (def [[i 21]])
                (println i@first)))))
```