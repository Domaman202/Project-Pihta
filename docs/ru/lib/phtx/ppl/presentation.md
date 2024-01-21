## presentation
Создаёт `презентацию`.<br>
Устанавливает `заголовок` и `время появления` презентации.<br>
Последовательно добавляет `слайды` и выполняет `выражения`.

### Применение

1. `(presentation title blackout (expr0) (exprN))`<br>
`title` - _заголовок_.<br>
`blackout` - _время появления_.<br>
`expr0` `exprN` - _слайды_ и _выражения_.

### Примеры

```pihta
(use-ctx pht phtx/ppl
    (import phtx/ppl (types ru.DmN.phtx.ppl.utils.Presentation))
    (app-fn
        (#setDarkTheme ^Presentation)
        (#show (presentation "Тестовая Презентация" 1000
            (page-list
                (e-title "Крещение Руси" 36)
                (e-text  (inc-txt "res/0.txt") 24)
                (e-image (inc-img "res/0.jpg"))
                (e-text  (inc-txt "res/1.txt") 24))
            (page-list
                (e-title "Флаги Российской Империи" 36)
                (e-image (inc-img "res/1.jpg"))
                (e-title "Флаги Российской Империи" 36)
                (e-image (inc-img "res/2.jpg")))
            (page-list
                (for [i (range 0 19)]
                    (e-title (+ "i = " i) 36)))))))
```