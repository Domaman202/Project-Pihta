## throw
Бросает `исключение`.

### Применение

1. `(throw e)`<br>
`e` - _исключение_.

### Примеры

```pihta
(use-ctx
    (app-fn
        (throw (new ^java.lang.RuntimeException))
        (println "Недостежимый код")))
```