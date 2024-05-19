## value
Вырадение `значения`.<br>

#### Поддерживает типы:
1. nil
2. Число
3. Символ
4. Строка
5. Тип

#### Примеры:
1. `nil`
2. `12` / `21L` / `33.44f` / `202.203`
3. `'@'`
4. `"Слава России!"`
5. `^Object` / `^List<^Object>`

### Применение

1. `(value val)`<br>
`val` - _значение_.<br><br>
2. `val`<br>
`val` - _значение_.

### Примеры

```pihta
(use-ctx pht
    (app-fn
        (println 0 nil)
        (println 1 12)
        (println 2 21.33)
        (println 3 44f)
        (println 4 202l)
        (println 5 '@')
        (println 6 "Текст")
        (println 7 ^Object)
        (println 8 ^java.util.List<^Object>)
        (println 9 (value nil))
        (println 10 (value 12))
        (println 11 (value 21.33))
        (println 12 (value 44f))
        (println 13 (value 202l))
        (println 14 (value '@'))
        (println 15 (value "Текст"))
        (println 16 (value ^Object))
        (println 17 (value ^java.util.List<^Object>))))
```
***(Работает на JVM)***

```pihta
(use-ctx pht 
    (app-fn
        (test-fn 0 nil)
        (test-fn 1 12)
        (test-fn 2 21.33)
        (test-fn 3 44f)
        (test-fn 4 202l)
        (test-fn 5 '@')
        (test-fn 6 "Текст")
        (test-fn 7 (value nil))
        (test-fn 8 (value 12))
        (test-fn 9 (value 21.33))
        (test-fn 10 (value 44f))
        (test-fn 11 (value 202l))
        (test-fn 12 (value '@'))
        (test-fn 13 (value "Текст"))))
```
***(Работает везде)***

### Тесты

1. `test/pht/cpp/value`
2. `test/pht/jvm/value`