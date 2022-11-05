Path to game settings file
==========================

The Yoda Stories game stores all of its settings in the standard INI file `Yodesk.ini`.

`%WINDIR%\Yodesk.ini`

However, `Windows` operating systems `Vista`, `7`, `10` and above do not allow writing to protected directories without administrator rights.
Any attempts to write to `%PROGRAMFILES%`, `%PROGRAMDATA%` and `%WINDIR%` are redirected to `%LOCALAPPDATA%\VirtualStore`.

For a user with the nickname `user`, the settings file is available in any of the following paths:

* `C:\Documents and Settings\user\AppData\Local\VirtualStore\Windows\yodesk.ini`
* `C:\Documents and Settings\user\Local Settings\VirtualStore\Windows\yodesk.ini`
* `C:\Users\user\AppData\Local\VirtualStore\Windows\yodesk.ini`
* `C:\Users\user\Local Settings\VirtualStore\Windows\yodesk.ini`

The demo version of the game saves its settings in the `YodaDemo.ini` file.

Example settings file:

```
[OPTIONS]
Terrain=1
MIDILoad=1
PlaySound=0
PlayMusic=0
Difficulty=50
GameSpeed=140
WorldSize=1
Count=1
HScore=748
LScore=623
LCount=0
[GameData]
Alaska0=-1321095903,183,4,380,286,380,372
Nevada0=-278855738,4,3,89,119,189
Oregon0=158416824,214,3,348,346,412
```

There are two sections here, `[OPTIONS]` and `[GameData]`, which are worth talking about in more detail.

TODO

https://www.webfun.io/docs/files/yodesk-ini.html