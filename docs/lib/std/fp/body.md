## body
Создаёт `тело`.<br>
Последовательно выполняет `выражения` в `теле`.

### Применение

1. `(body (expr0) (exprN))`<br>
`(expr0)` `(exprN)` - _выражения_.

### Примеры

```pihta
(use #std/base)
(def [[i 12]])
(body
    (def [[i 21]])
    (#println std i))
(#println std i)
```