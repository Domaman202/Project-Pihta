## array-type
Создаёт `тип массива` из `типа элементов`.

### Применение

1. `(array-type ^type)`<br>
`type` - _тип элементов_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (array-type ^Object))))
```