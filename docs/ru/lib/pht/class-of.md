## class-of
Получает `java.lang.Class` `типа`.

### Применение

1. `(class-of ^type)`<br>
`type` - _тип.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println (#getName (class-of ^Object)))))
```