## catch
Выполняет выражения в `теле` и обрабатывает `исключения` опеределенного `типа`.<br>
При обработке `исключения`:
1. Помещает `исключение` в `переменную`,
2. Выполняет `выражение ловли`.

### Применение

1. `(catch [[e0 ^type0 expr0][eN ^typeN exprN]] body)`<br>
`e` - _переменная_.<br>
`^type` - _тип_.<br>
`expr` - _выражение ловли_.<br>
`body` - _тело_.

### Примеры

```pihta
(use-ctx pht
    (import java [
        [types [
            java.lang.Exception
            java.lang.RuntimeException]]])
    (app-fn
        (catch [[_ ^RuntimeException    (println "Runtime Exception!")]
                [_ ^Exception           (println "Exception!")]]
            (throw (new ^Exception)))))
```