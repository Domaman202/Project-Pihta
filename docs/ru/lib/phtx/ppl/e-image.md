## e-image
Создаёт `картинку`.

### Применение

1. `(e-image image)`<br>
`image` - _картика_.

### Примеры

```pihta
(use-ctx pht phtx/ppl
    (app-fn
        (#show (presentation "Тестовая Презентация" 1000
            (page-list
                (e-image (inc-img "res/0.jpg")))))))
```

***Требует наличие файла `0.jpg` в папке `res` исполняемого модуля.***