Color palette
=============

Палитра хранится в файле `yodesk.exe`. Для самой свежей англоязычной версии игры её адрес равен `0x0550EF`.
Найти палитру в других версиях Yoda Stories тоже нетрудно, она находится примерно там же.
Первые 41 байт палитры заполнены нулями `0x00`, за ними следом можно увидеть два значения `0xFF`.
Обязательно изучите то, что находится до и после палитры - найдёте для себя много интересного.

Формат кодирования цвета: `BGRA` - 4 байта на 1 цвет. Синий-зелёный-красный-прозрачность.
На самом деле, четвёртый байт используется для выравнивания данных, чтобы соответствовать машинному слову.
Он нигде не используется, поскольку прозрачность в игре закреплена за конкретным цветом палитры с индексом `0`.




https://www.webfun.io/docs/gameplay/color-palette.html

