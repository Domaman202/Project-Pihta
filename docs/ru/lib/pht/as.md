## as
Приводит `значение` к `типу`.

### Применение

1. `(as ^type val)`<br>
`^type` - _тип_.<br>
`val` - _значение_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (as ^double 33))))
```