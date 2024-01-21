## rfn
Возвращает _ссылку_ на _метод_.

### Применение

1. `(rfn ^type0 ^type1 name)`<br>
`^type0` - _тип ссылки_.<br>
`^type1` - _тип_.<br>
`name` - _имя метода_.<br><br>
2. `(rfn ^type instance name)`<br>
`^type` - _тип ссылки_.<br>
`instance` - _объект_.<br>
`name` - _имя метода_.<br><br>
3. `(rfn . ^type name)`<br>
`^type` - _тип_.<br>
`name` - _имя метода_.<br><br>
4. `(rfn . instance name)`<br>
`instance` - _объект_.<br>
`name` - _имя метода_.

### Примеры

```pihta
(use-ctx pht
    (import java
        (types java.util.function.Function))
    (cls Test [^Object] (@static
        (defn foo ^String [[o ^String]] o)))
    (app-fn
        (println (#apply (rfn ^Function ^Test foo) "Foo!"))))
```

```pihta
(progn
    (import java
        (types java.util.function.Function))
    (cls Test [^Object]
        (ctor [] (ccall))
        (defn foo ^String [[o ^String]] o))
    (app-fn
        (println (#apply (rfn ^Function (new ^Test) foo) "Foo!"))))
```

```pihta
(use-ctx pht
    (import java
        (types java.util.function.Function))
    (cls Provider [^Object] (@static
        (defn foo ^String [[o ^String]] o)))
    (cls Consumer [^Object] (@static
        (defn test ^void [[f ^Function]]
            (println (#apply f "Слава России!")))))
    (app-fn
        (#test ^Consumer (rfn . ^Provider foo))))
```