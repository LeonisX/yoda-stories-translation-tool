# Yoda Stories Translation Tool

Utility Yoda Stories Translation Tool

**Current status: starting only**

**Current stage: project development**

An original version: https://github.com/LeonisX/YExplorer

## TODO

* Dump all
* Dump texts to DOCX (separators, IACT/PUZ2 labels, ignore them, allow blocked text)
* Replace tiles, maps
* Replace text from DOCX
* Log
* HEX

* Prepare translated DTA & dump it here

* Restore functionality
* Add new tiles - already in Delphi version - we need a better solution: "PLUS" button at the end of tiles
* Dump from EXE file
* Dump palette from EXE file
* Dump/read to DOCX
* Validate text (first-last) phrases
* Hide debug tabs by default

* Show in UI: Chars, Names
* Show in UI info about puzzles (id, size, phrases count)
* IACT: show phrases count

* TODO tests!!!!!!!!!!!!!!!

* Remove all text from DTA, save puz2, iacts together, verify if all phrases was found
* Paste from clipboard (good idea)
* Validate loaded BMP palette
* In Map we save offset before title (ex. IZX3). But IACT - we save offset after title. Need to unify all. 
* PUZ2 - если смотреть в дампах, то там перед текстом попадается левый текст, вроде: Bogus text; Unused text, right; Look elsewhere for this text. 

### BMP

* https://www.infoworld.com/article/2077561/java-tip-60--saving-bitmap-files-in-java.html
* http://www.java2s.com/Tutorials/Java/Graphics_How_to/Image/Create_BMP_format_image.htm
* https://docs.oracle.com/javase/tutorial/2d/images/index.html
* https://coderanch.com/t/456757/java/creating-BufferedImage-save-bmp-file
* https://research.cs.queensu.ca/home/blostein/image/Example/BMPFile.java
* https://github.com/nayuki/BMP-IO
* http://image4j.sourceforge.net/#license
* https://commons.apache.org/proper/commons-imaging/
* https://github.com/imagej/imagej
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
* https://github.com/katjahahn/PortEx
* http://web.archive.org/web/20120708235509/http://java.net/projects/pe-file-reader
* http://sourceware.org/binutils/docs/binutils/windres.html



Uses modified code from http://image4j.sourceforge.net/ project