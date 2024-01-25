## @inline
Аннотация `abstract`.<br>
Применяется к `выражениям` типа:
1. `метод`

### Применение

1. `(@inline expr0 exprN)`
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (app
        (@inline
        (defn foo ^void []
            (println "Foo!")))
        (app-fn
            (#foo .))))
```