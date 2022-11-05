Path to game settings file
==========================

`%WINDIR%\Yodesk.ini`

However, Windows operating systems `Vista`, `7`, `10` and above do not allow writing to protected directories without administrator rights.
Any attempts to write to `%PROGRAMFILES%`, `%PROGRAMDATA%` and `%WINDIR%` are redirected to `%LOCALAPPDATA%\VirtualStore`.

For a user with the nickname `user`, the settings file is available in any of the following paths:

* `C:\Documents and Settings\user\AppData\Local\VirtualStore\Windows\yodesk.ini`
* `C:\Documents and Settings\user\Local Settings\VirtualStore\Windows\yodesk.ini`
* `C:\Users\user\AppData\Local\VirtualStore\Windows\yodesk.ini`
* `C:\Users\user\Local Settings\VirtualStore\Windows\yodesk.ini`