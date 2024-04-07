## @native
Аннотация `native`.<br>
Применяется к `выражениям` типа:
1. `класс`

### Применение

1. `(@native expr0 exprN)`<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (@native
    (cls ^Test [] (@static
        (defn foo ^void []
            (println "Foo!")))))
    (app-fn
        (#foo ^Test)))
```