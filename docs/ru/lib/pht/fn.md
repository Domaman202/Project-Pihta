## fn
Создаёт __безымянную__ функцию, возвращает _ссылку_ на неё.

### Применение

1. `(fn ^Runnable [a b][c] (expr0))`<br>
`^Runnable` - _тип ссылки_.<br>
`[a b]` - _внешние переменные_.<br>
`[c]` - _аргументы_.<br>
`(expr0)` - _выражения_.<br><br>
2. `(fn [a b][c] (expr0))`<br>
`[a b]` - _внешние переменные_.<br>
`[c]` - _аргументы_.<br>
`(expr0)` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (import java
        [[types [java.lang.Runnable]]])
    (app
        (defn test ^void [[o ^Runnable]]
            (#run o))
        (app-fn
            (#test ^App (fn ^Runnable [][] (println "Арен брат, с днюхой!"))))))
```

```pihta
(use-ctx pht
    (import java
        [[types [java.lang.Runnable]]])
    (app
        (defn test ^void [[o ^Runnable]]
            (#run o))
        (app-fn
            (#test ^App (fn [][] (println "Арен брат, с днюхой!"))))))
```

```pihta
(use-ctx pht
    (import java
        [[types [java.lang.Runnable]]])
    (app
        (defn test ^void [[o ^Runnable]]
            (#run o))
        (app-fn
            (def [[i 12] [j 21]])
            (#test ^App (fn ^Runnable [i j][] (println (+ i j)))))))
```

```pihta
(use-ctx pht
    (import java
        [[types [java.lang.Runnable]]])
    (app
        (defn test ^void [[o ^Runnable]]
            (#run o))
        (fld [[i ^int] [j ^int]])
        (app-fn
            (set i 12)
            (set j 21)
            (#test ^App (fn [i j][] (println (+ i j)))))))
```