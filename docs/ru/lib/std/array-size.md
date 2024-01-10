## array-size
Возвращает `размер` `массива`.

### Применение

1. `(array-size arr)`<br>
`arr` - _массив_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (array-size (array-of 202 203 213 666 777 999)))))
```