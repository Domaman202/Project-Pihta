### Применение

1. `(fn ^Runnable [a b] (expr0))`<br>
`^Runnable` - _тип_.<br>
`[a b]` - _аргументы_.<br>
`(expr0)` - _выражения_.<br><br>
2. `(fn [a b] (expr0))`<br>
`[a b]` - _аргументы_.<br>
`(expr0)` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (import java
        (type java.lang.Runnable))
    (app
        (defn test ^void [[o ^Runnable]]
            (#run o))
        (defn main ^void [] (progn
            (#test ^App (fn ^Runnable [] (println "Арен брат, с днюхой!")))))))
```

```pihta
(use-ctx pht
    (import java
        (type java.lang.Runnable))
    (app
        (defn test ^void [[o ^Runnable]]
            (#run o))
        (defn main ^void [] (progn
            (#test ^App (fn [] (println "Арен брат, с днюхой!")))))))
```