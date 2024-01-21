## list-of
Создаёт `список` из `элементов`.

### Применение

1. `(list-of (expr0) (exprN))`<br>
`(expr0)` `(exprN)` - _элементы_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (list-of 12 21 33))))
```