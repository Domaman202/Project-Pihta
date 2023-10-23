## app
Создаёт ___класс__ приложения_ и выполяет в нём `выражения`.

### Применение

1. `(app (expr0) (exprN))`<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (app (defn main ^void []
        (println "Апчхи!"))))
```