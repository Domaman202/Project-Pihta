## new-array
Создаёт массив заданного `рамера` и `типа`.

### Применение

1. `(new-array ^type i)`<br>
`type` - _тип массива_.<br>
`i` - _размер массива_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (#contentToString (new-array ^int 4)))))
```