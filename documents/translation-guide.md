Yoda Stories Translation Guide
==============================

![](images/baby-yoda.png)

A fully translated game has its own localized ones: 

* Startup screen
* Start, Win and Loose screens
* Text in Actions
* Text in Puzzles
* Tile names
* Fully translated executable file
* Fully translated Help file

The task is not easy, but very exciting. Самое важное, что переводить игру можно вдвоём, например, один графику, другой текст.

We have prepared a documentation for you, where each operation is described in steps. Good luck!

* [Graphics](graphics-translation-guide.md)
* [Text](text-translation-guide.md)
* [YODESK.EXE](exe-translation.md)
* YODESK.HLP

## Какую версию переводить

В настоящее время известны следующие версии Yoda Stories:

* Star Wars - Yoda Stories (14.02.1997) (Eng) (v1.0)
* Star Wars - Yoda Stories (14.02.1997) (Eng) (v1.0) (Patch v6)
* Star Wars - Yoda Stories (20.03.1997) (Eng) (v1.1)
* **Star Wars - Yoda Stories (10.08.1998) (Eng) (v1.2) (Patch v6)**
* Star Wars - Yoda Stories (18.02.1997) (Eng) (Demo)
* Star Wars - Yoda Stories (22.05.1997) (Spa)
* Star Wars - Yoda Stories (25.06.1997) (Ger)
* Star Wars - Yoda Stories (13.12.2001) (T-Spa_Selva Translators)
* Star Wars - Yoda Stories (12.11.2006) (T-Rus_PRO)
* TODO My translation

Они идентифицируются по контрольным суммам файлов yodesk.exe и yodesk.dta.

Yoda Stories Translation Tool (далее по тексту YSTT) после загрузки игры выводит всю необходимую информацию
о версии, а так же даёт необходимые рекомендации.

![](images/gui-common.png)

**Если YSTT не смог идентифицировать вашу версию игры, то обязательно пришлите её нам для изучения.**

Мы настоятельно рекомендуем брать в качестве основы ТОЛЬКО версию Star Wars - Yoda Stories (10.08.1998) (Eng) (v1.2) (Patch v6),
поскольку в ней находятся самые последние исправления.

Искать эту игру стоит в сборнике Star Wars - Yoda Stories & Behind The Magic - Vehicles Special Edition, который,
в свою очередь, входил в сборник LucasArts Archives Vol. IV: Star Wars Collection II.

Все остальные версии продавались как сборник Star Wars - Yoda Stories & Making Magic.

В качестве альтернативы можно взять и русский перевод от Leonis, но надо понимать, что перевод перевода может
оказаться ещё дальше от оригинала.

## Алгоритм перевода DTA файла

1. Сдампить все ресурсы из yodesk.dta
2. Перевести их и вставить обратно

Среди сдампленных ресурсов для перевода потребуются следующие файлы:

* stup.bmp
* *.pal
* clipboard.bmp
* iact2.docx
* puz2.docx
* tilenames2.docx

Если операция вставки ресурсов выполняется не за один раз, то есть, допустим, что сначала была вставлена графика, и файл
yodesk.dta был сохранён, то его CRC32 поменяется, и со вставкой текста при следующем запуске YSTT будут трудности. 
Утилита будет искать ресурсы в каталоге output-unk.
В таком случае перенесите все переведённые ресурсы в каталог output-unk.

После перевода игры обязательно отправьте её нам, чтобы бы добавили в базу данных известных переводов Yoda Stories.
