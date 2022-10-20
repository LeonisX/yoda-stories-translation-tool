Путь к файлу настроек игры
==========================

`%WINDIR%\Yodesk.ini`

Однако операционные системы Windows `Vista`, `7`, `10` и выше не позволяют писать в защищённые каталоги без прав администратора.
Любые попытки записи в `%PROGRAMFILES%`, `%PROGRAMDATA%` и `%WINDIR%` перенаправляются в `%LOCALAPPDATA%\VirtualStore`.

Для пользователя с никнеймом `user` файл настроек доступен по любому из следующих путей:

* `C:\Documents and Settings\user\AppData\Local\VirtualStore\Windows\yodesk.ini`
* `C:\Documents and Settings\user\Local Settings\VirtualStore\Windows\yodesk.ini`
* `C:\Users\user\AppData\Local\VirtualStore\Windows\yodesk.ini`
* `C:\Users\user\Local Settings\VirtualStore\Windows\yodesk.ini`