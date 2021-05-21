# Yoda Stories Translation Tool

Utility Yoda Stories Translation Tool

**Current status: read/modify, dump, translate DTA, hack EXE file**

**Current stage: polishing, testing**

**Powered with Kaitai structure format from Cyco**

[Read Yoda Stories Translation Guide](documents/translation-guide.md)

[Read Guide About YODESK.EXE Modifying](documents/modify-exe.md)

An original version: https://github.com/LeonisX/YExplorer

## TODO

* Log
* HEX
* Тяжёлые процессы дампинга лучше вынести в отдельный поток

* Сломан вывод таблицы Map (нужна ли?)

* Dump structure (hi, mid, lo levels)

* Show extra data in UI

* Compare all characters dumps
* UnsignedInt, ... - изучить доступные решения. идея простая - возвращать number, но если минус, то тип на порядок больше.
* Article about unsigned (hex editor, port from delphi, mathematic, before java 8, current solutions, examples)

* Paste from clipboard (good idea)

* Think how to drag-drop from UI from Windows Explorer (Open file)
* TODO 223.res in description, if need
* Full instruction: EXE, DTA: MarkDown
* Resolve all TODO
* remove all [CR2], [CR] code
* Bug when show-hide zone layers (#0 map)

* Identify Characters MovementTypes

* About Actions scripts: https://www.webfun.io/docs/scripting/index.html


### Other YS / DA solutions

**yodesk.ksy**

* https://www.webfun.io/
* https://github.com/cyco/WebFun
* https://github.com/cyco/kaitai_struct_formats/blob/add-yodesk/game/yodesk.ksy
* https://github.com/kaitai-io/kaitai_struct_formats/pull/403

* https://github.com/a-kr/jsyoda
* https://github.com/mikepthomas/yoda-stories
* https://github.com/shinyquagsire23/DesktopAdventures
* https://github.com/IceReaper/DesktopAdventuresToolkit
* https://github.com/digitall/scummvm-deskadv

* https://github.com/auselen/SWYodaStories
* https://github.com/Archenoth/yodesk-notes
* https://github.com/cyco/deskfun-preview

* https://github.com/kirbysayshi/yoda-stories-docker

### Unsigned:

* https://github.com/robig/axis2-adb/tree/master/src/main/java/org/apache/axis2/databinding/types
* https://github.com/google/guava/tree/master/guava/src/com/google/common/primitives
* https://github.com/jOOQ/jOOU/tree/main/jOOU/src/main/java/org/joou
* https://github.com/OPCFoundation/UA-Java-Legacy/tree/master/src/main/java/org/opcfoundation/ua/builtintypes
* http://faculty.nps.edu/brutzman/vrtp/javadoc/dis-java-vrml/mil/navy/nps/util/package-summary.html
* https://stackoverflow.com/questions/430346/why-doesnt-java-support-unsigned-ints
* https://stackoverflow.com/questions/8345603/is-javas-lack-of-unsigned-primitive-types-a-characteristic-of-java-the-platform
* https://habr.com/ru/post/225901/
* https://www.teamdev.com/downloads/jniwrapper/javadoc/com/jniwrapper/package-summary.html

### BMP

####Accepted:

* https://github.com/nayuki/BMP-IO

####Other solutions:

* https://github.com/imagej/imagej
* https://www.infoworld.com/article/2077561/java-tip-60--saving-bitmap-files-in-java.html
* http://www.java2s.com/Tutorials/Java/Graphics_How_to/Image/Create_BMP_format_image.htm
* https://docs.oracle.com/javase/tutorial/2d/images/index.html
* https://coderanch.com/t/456757/java/creating-BufferedImage-save-bmp-file
* https://research.cs.queensu.ca/home/blostein/image/Example/BMPFile.java
* http://image4j.sourceforge.net/#license
* https://commons.apache.org/proper/commons-imaging/

* https://www.programcreek.com/java-api-examples/?api=java.awt.image.IndexColorModel
* http://www.java2s.com/Tutorials/Java/Graphics_How_to/Image/Swap_Palette_for_an_BufferedImage.htm
* https://docs.oracle.com/javase/8/docs/api/javax/imageio/package-summary.html

### Hex Editors (Java FX)

* https://github.com/Velocity-/Hexstar
* https://github.com/Braincoke/HexViewer

* https://bined.exbin.org/library/

* https://stackoverflow.com/questions/32317443/does-javafx-or-swing-have-a-class-where-i-can-display-and-highlight-text-and-con
* https://github.com/FXMisc/RichTextFX

### DOCX

* http://poi.apache.org/
* https://stackoverflow.com/questions/44016335/read-the-tables-data-from-the-docx-files/44061001

### Java EXE

* http://jsmooth.sourceforge.net/
* http://launch4j.sourceforge.net/
* https://portableapps.com/apps/utilities/java_portable

### PE

* http://pecoff4j.sourceforge.net/
* https://github.com/kichik/pecoff4j
* https://github.com/jonnyzzz/PE

* https://github.com/katjahahn/PortEx
* http://web.archive.org/web/20120708235509/http://java.net/projects/pe-file-reader
* http://sourceware.org/binutils/docs/binutils/windres.html



Uses modified code from http://image4j.sourceforge.net/ project