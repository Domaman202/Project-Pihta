## macro-name
Вставка `макро`-`аргумента` в виде `символа` (`названия`/`имени`).

### Применение

1. `(macro-name name)`<br>
`name` - _имя_.

### Примеры

```pihta
(defmacro tcall [name args]
    (macro-inline [args] (mcall this (macro-name name) (macro-arg args))))
```