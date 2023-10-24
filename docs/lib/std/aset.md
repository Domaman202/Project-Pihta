## aset
Присваивает `значение` элементу `массиву` по `индексу`.

### Применение

1. `(aset arr i v)`<br>
`arr` - _массив_.<br>
`i` - _индекс элемента_.<br>
`v` - _значение_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (def [[arr (new-array ^int 4)]])
        (aset arr 0 12)
        (aset arr 3 21)
        (println (#contentToString arr))))
```