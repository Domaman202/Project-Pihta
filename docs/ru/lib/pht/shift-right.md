## shift-right
_Битовый_ сдвиг __вправо__.<br>
Принимает `значение` и `сдвиг`.

### Применение

1. `(shift-right val offset)`<br>
`val` - _значение_.<br>
`offset` - _сдвиг_.<br><br>
2. `(>> val offset)`<br>
`val` - _значение_.<br>
`offset` - _сдвиг_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (>> 1024 10))))
```