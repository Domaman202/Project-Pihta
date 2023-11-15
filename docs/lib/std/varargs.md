## @varargs
Аннотация `varargs`.<br>
Применяется к `выражениям` типа `метод`.

### Применение

1. `(@varargs expr0 exprN)`<br>
`expr0` `exprN` - _выражения_.

### Примеры

```pihta
(use-ctx pht
    (cls StringExtends [^Object]
        (@varargs (efn ^String sub ^String [[arr (array-type ^String)]] (progn
            (def [[str (new ^StringBuilder this)]])
            (for [e arr]
                (#append str e))
            (#toString str)))))
    (app-fn
        (println (- "Слава " "России!"))))
```