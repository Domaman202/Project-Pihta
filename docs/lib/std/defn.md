## defn
Определяет _функцию_ / _метод_.

### Применение

1. `(defn name ^typeR [[arg0 ^type0] [argN ^typeN]] (expr0) (exprN))`<br>
`name` - _имя_.<br>
`^typeR` - _возвращаемый тип_.<br>
`arg` - _имя аргумента_<br>
`^type` - _тип аргумента_.<br>
`(expr0)` `(exprN)` - _тело_.

### Примеры

```pihta
(use-ctx pht
    (app
        (defn foo ^void []
            (println "Foo!"))
        (app-fn
            (#foo ^App))))
```