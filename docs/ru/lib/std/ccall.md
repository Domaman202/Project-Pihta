## ccall
Вызывает __конструктор предка__.

### Применение

1. `(ccall (arg0) (argN))`<br>
`arg0` `argN` - _аргументы_.

### Примеры

```pihta
(use-ctx pht
    (cls Test [^Object]
        (ctor [] (ccall)))
    (app-fn
        (println (new ^Test))))
```