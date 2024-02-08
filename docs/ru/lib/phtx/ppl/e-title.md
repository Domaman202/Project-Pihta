## e-title
Создаёт `заголовок`.

### Применение

1. `(e-title text font)`<br>
`text` - _текст_.<br>
`font` - _размер шрифта_.

### Примеры

```pihta
(use-ctx pht phtx/ppl
    (app-fn
        (#show (presentation "Тестовая Презентация" 1000
            (page-list
                (e-title "Россия - это наша Родина!" 56))))))
```