## import
Импортирует из `модуля`:
1. `Типы`
2. `Методы`
3. `Расширения`
4. ~~`Аннотации`~~
5. `Макросы`

### Применение

1. `(import [[c0 [p0 pN]][cN [p0 pN]]])`<br>
`c` - _категория_.<br>
`p` - _параметр_.

### Примеры

***№1***
```pihta
(use-ctx pht
    (import "java" [[types [java.lang.System]]])
    (app-fn
        (println ^System)))
```

***№2***

*test/src.pht*
```pihta
(use-ctx pht test/helper
    (import "test/helper" [[methods [Foo.foo]]])
    (app-fn
        (#foo .)))
```
*test/helper/src.pht*
```pihta
(use-ctx pht
    (cls Foo [^Object] (@static
        (defn foo ^void []
            (println "Foo!")))))
```

***№3***

*test/src.pht*
```pihta
(use-ctx pht test/helper
    (import "test/helper" [[extends [StringExtends]]])
    (app-fn
        (#log "Hi!")))
```
*test/helper/src.pht*
```pihta
(use-ctx pht
    (cls StringExtends [^Object]
        (efn ^String log ^void []
            (println this))))
```

***№5***

*test/src.pht*
```pihta
(use-ctx pht test/helper
    (import "test/helper" [[macros [print-hi]]])
    (app-fn (print-hi)))
```
*test/helper/src.pht*
```pihta
(use-ctx pht
    (cls Foo [^Object] (@static
        (defn foo ^void []
            (println "Foo!")))))
```