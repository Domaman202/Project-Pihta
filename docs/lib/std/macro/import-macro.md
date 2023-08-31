## import-macro
Импортирует `макросы`.

### Применение

1. `(import-macro [pht.std.let])`<br>
`pht.std.let` - _макрос_.
2. `(import-macro [pht.std.*])`<br>
`pht.std.*` - _макросы_.

### Примеры

```pihta
(use std/base)
(import-macro [pht.std.let])
(def [[i 21]])
(let [[i 12]] (
    (#println std i)))
(#println std i)
```