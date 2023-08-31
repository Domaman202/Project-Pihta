## macro-arg
Вставка `макро`-`аргумента`.

### Применение

1. `(macro-arg name)`<br>
`name` - _имя_.

### Примеры

```pihta
(defmacro log [data]
    (#println (use std/base) (macro-arg data)))
(log "Слава КПСС!")
```