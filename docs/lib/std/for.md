## for
Цикл перебора элементов `коллекции` (`Array`|`Iterable`|`Iterator`).

### Применение

1. `(for [i arr] (expr0) (exprN))`<br>
`i` - _элемент_.<br>
`arr` - _коллекция_.<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (for [i (array-of 12 21 33)]
            (println i))))
```