## @final
Аннотация `final`.<br>
Применяется к `выражениям` типа:
1. `поле`

### Применение

1. `(@final expr0 exprN)`<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (app
        (@final
        (fld [[i ^int]]))
        (defn <clinit> ^void []
            (set ^App/i 333))
        (app-fn
            (println ^App/i))))
```