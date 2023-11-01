### Применение

### Применение

1. `(fn ^Runnable [a b][c] (expr0))`<br>
`^Runnable` - _тип_.<br>
`[a b]` - _внешние переменные_.<br>
`[c]` - _аргументы_.<br>
`expr0` - _выражения_.<br><br>
2. `(fn [a b][c] (expr0))`<br>
`[a b]` - _внешние переменные_.<br>
`[c]` - _аргументы_.<br>
`expr0` - _выражения_.


### Примеры

```pihta
(use-ctx pht
    (import java
        (type java.lang.Runnable))
    (app
        (defn test ^void [[o ^Runnable]]
            (#run o))
        (defn main ^void [] (progn
            (def [[i 12] [j 21]])
            (#test ^App (rfn ^Runnable [i j][] (println (+ i j))))))))
```

```pihta
(use-ctx pht
    (import java
        (type java.lang.Runnable))
    (app
        (defn test ^void [[o ^Runnable]]
            (#run o))
        (field [[i ^int] [j ^int]])
        (defn main ^void [] (progn
            (set i 12)
            (set j 21)
            (#test ^App (rfn [i j][] (println (+ i j))))))))
```