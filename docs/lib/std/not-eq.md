## not-eq
Сравнивает __два__ `значения`, проверяет, что `первое` __не равно__ `второму`.

### Применение

1. `(not-eq valA valB)`<br>
`valA` - _первое значение_.<br>
`valB` - _второе значение_.<br><br>
2. `(!= valA valB)`<br>
`valA` - _первое значение_.<br>
`valB` - _второе значение_.<br><br>

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (!= 4 5))))
```