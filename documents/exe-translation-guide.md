Yoda Stories EXE Translation Guide
==================================

[Содержание](translation-guide.md)

A fully translated YS executable file has its own localized/modified ones: 

* Menu
* Dialogs
* Strings
* Optionally windows size
* Optionally dialog tool tip size

Yoda Stories Translation Tool (YSTT) originally planned to translate text in an EXE file as well. 
However, the structure of Windows PE headers is complex enough to quickly implement it.

There are many third-party Portable Executables editors, and in fact, the main difficulty is in choosing the one that suits best.

Our choice settled on the Restorator utility, but it is paid, therefore, we will publish a list of several more tools in alphabetical order, 
so that you choose the one that will allow you to perform the translation with comfort. Any allows you to fully translate text strings, menus and dialog boxes.

* PE Explorer (129$)
* ResEdit (Free)
* Resource Builder (59$)
* Resource Hacker (Free)
* Resource Tuner (49$)
* Restorator (72$)
* XN Resource Editor (Free)

YSTT сообщает эту же информацию, но дополнительно может открыть сайт выбранной утилиты.

![](images/gui-exe.png)


## Text translation

В рамках этого руководства мы не в состоянии описать принцип работы каждого редактора PE ресурсов.
Ограничимся лишь несколькими советами.

Характеристики хорошего редактора:

* Изменение меню, диалогов и строковых ресурсов
* Возможность менять строки пакетом, а не по одной
* Визуальный редактор диалогов
* Создание резервных копий исполняемого файла

При переводе строк следует учитывать некоторые особенности. В частности, одни строковые ресурсы используются в самой игре, 
обычно это фразы R2-D2. Другие можно увидеть при появлении каких-то предупреждений и ошибок.

Диалоги R2-D2 форматируются автоматически, однако, некоторые фразы отформатированы с помощью принудительных переносов строк: "\r\n".
Их желательно оставить.

О переносе строк предупреждений следует позаботиться самостоятельно: "\n". 
Практика показала, что строки можно делать длиннее чем в оригинальной игре, диалоговое окно автоматически растягивается.


## Ширина окна и диалогов

The main goal of these changes is to expand the window controls to accommodate longer text strings.

* Read article [Hacking Yoda Stories article (Eng)](http://tv-games.ru/forum/blog.php?b=2683)
* Read article [Hacking Yoda Stories article (Rus)](http://tv-games.ru/forum/blog.php?b=2682)

All found offsets are valid for `Yoda Stories (10.08.1998) (Eng) (v1.2) (Patch v6)`.

`Yoda Stories Translation Tool` is able to make these changes out of the box - there is no need to edit the file by hand.

Original example:

![](images/before-resize.png)

After the changes: 

![](images/after-resize.png)


## Main Window

When translating the Yoda Stories game into Russian, it turned out that the Tile Names do not fit in the inventory and goes to the ScrollBar.
It is necessary to expand:

* MainWindow
* Inventory Grid View

Move to the right: 

* ScrollBar

### Window size

The window is 525 pixels wide. This is 0x20D (0D020000 in LittleEndian)

For an unambiguous search for a value, it is need to find the following sequence of bytes:

Offset: 0x52DF

```
       vvvv
8D3C45 0D020000 6A08FF     (width)  	
```

It also needs to be replaced at this address:  

Offset: 0x5393

```
       vvvv
C74708 0D020000 C7470C
```

### Inventory Grid View

The Inventory Grid View is 489 pixels wide. This is 0x1E9 (E9010000 in LittleEndian) 

For an unambiguous search for a value, it is need to find the following sequence of bytes:

Offset: 0x1CA5D

```
             vvvv
C7828C320000 E9010000 C78294	(right coordinate)		
```

### Scroll Bar

To move to the right, it is need to change the left and right coordinates of the Scroll Bar.
Their current values: 

* Left: 496. This is 0x1F0 (F0010000 in LittleEndian)
* Right: 512. This is 0x200 (00020000 in LittleEndian)

For an unambiguous search for a value, it is need to find the following sequence of bytes:

Offset: 0x1CA67

```
             vvvv 
C78294320000 F0010000 B8FC00		(left)		offset: 0x1CA67
```

Offset: 0x1CA87

```
			 vvvv
C7829C320000 00020000 C782A4		(right)		offset: 0x1CA87
```


## Dialog Tool Tip

To make reading easier and to reduce the need for scrolling text, it is need to change the dialog box.
To do this, it is need to expand:

* White Round Rectangle
* Text Area (EDIT)

Move to the right:

* Dialog Control Buttons

Research has shown that it is enough to change the size of the Text Area, and all other controls will correctly respond to these changes.

Since the width of the Text Area is not constant and is calculated runtime, it is need to replace part of the code:

It was: 

```
lea     edx, [ebx+ebx*4]
add     eax, edx
```

Became: 

```
mov    eax, 0xA2
```

For an unambiguous search for a value, it is need to find the following sequence of bytes:

Offset: 0x8B17

```
8D149B03C2
```

replace with: 

```
b8a2000000          
```


## Text scrolling timers

`Not implemented.`

The timer delay value is 64 milliseconds.

It was found 4 timers, but experiments to reduce delays lead to the fact that after a click, the text scrolls by 1-2 lines,
and it is need to focus on the mouse to scroll either 1 or 2 lines at the same time. 

```
  vv
6A646A018B481C
```
