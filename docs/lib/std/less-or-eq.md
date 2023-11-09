## less-or-eq
Сравнивает __два__ `значения`, проверяет, что `первое` __меньше или равно__ `второму`.

### Применение

1. `(less-or-eq valA valB)`<br>
`valA` - _первое значение_.<br>
`valB` - _второе значение_.<br><br>
2. `(<= valA valB)`<br>
`valA` - _первое значение_.<br>
`valB` - _второе значение_.<br><br>

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (<= 3 3))))
```