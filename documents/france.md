Star Wars - Yoda Stories (Fra) & Making Magic
=============================================

* Регион: `Франция`, вероятно, Бельгия, французская Канада и Швейцария
* Язык: `французский`
* Издатель: [`Ubi Soft Entertainment`](http://web.archive.org/web/19971010184138/http://www.ubisoft.fr/)
* Планируемая дата выпуска: `апрель 1997` года
* Возрастной рейтинг: `отсутствует`
* UPC: `3 362932 248507`

Дату выпуска удалось найти на рекламном листке, который поставляется в комплекте с игрой.

[![](images/cover/thumb/yoda-stories-france-box-front.jpg)](images/cover/yoda-stories-france-box-front.jpg)
[![](images/cover/thumb/yoda-stories-france-box-back.jpg)](images/cover/yoda-stories-france-box-back.jpg)
[![](images/cover/thumb/yoda-stories-france-jewel-case-front.jpg)](images/cover/yoda-stories-france-jewel-case-front.jpg)
[![](images/cover/thumb/yoda-stories-france-disk-front.jpg)](images/cover/yoda-stories-france-disk-front.jpg)

Любопытно, что скриншоты на коробке не переведены.
Это наводит на мысль, что и некоторые другие игры могли быть локализованы, 
но на коробке использовались скриншоты из американской версии.

Текст на коробке, за исключением скриншотов полностью французский. Возрастного рейтинга на упаковке нет.

Диск:

* Дата записи: `07.05.1997 10:21:28`
* Volume Space Size (LBA): `252658`
* Volume Identifier: `YODAFRENCH`

Содержимое диска:

* `Star Wars: Yoda Stories (France)`
* `Star Wars: Making Magic (France) (FD1.03) (22.09.1996)`
* Тематические курсоры, иконки, обои и звуковые эффекты для Windows.

Можно заметить, что диск был записан в начале мая, издатель не вписался в запланированные строки.

`Star Wars - Making Magic` на этом диске отличается от версии для США, готовой уже 3 сентября.
Интерфейс полностью переведён, так же можно встретить два десятка новых файлов,
вероятно, с дополнительными материалами. По файлам шрифтов можно понять,
что где-то во вселенной ещё есть подобные энциклопедии на корейском и японском языках.

Результаты сравнения с Испанской версией показывают, что локализованы следующие файлы:

* `data\drivers\bootdisk.exe` [48346 bytes]
* `data\launch\launch.trs` [89807 bytes]
* `data\system\hope.trs` [64190 bytes]
* `support\bootdisk.exe` [48346 bytes]

Вероятно, есть смысл попытаться перевести энциклопедию на русский язык, и если не удастся сделать это
с англоязычным вариантом, то взять за основу одну из Европейских версий.


The French version vs Spanish version
-------------------------------------

Французская версия игры среди Европейских версий была разработана самой первой.
В ней ещё отсутствует демо-версия рельсового шутера Rebel Assault II,
и исправлено меньше зон, чем у последующих игр.

Но при сравнении Европейских версий мы будем опираться прежде всего на
[Испанское издание](spain.md),
потому что оно раньше других появилось в сети, лучше изучено,
и очень похоже на все другие версии, то есть, не придётся каждый раз перечислять одни и те же различия.

По сравнению с Испанской игрой, 
French version of the game is missing very important fixes for zones `472` and `572`.

So, on the one hand, this is the very first European version of Yoda Stories,
but on the other hand, it has the fewest bug fixes for zones `72, 236, 407`.
Подробнее об этих исправлениях написано на страничке, посвящённой [версии 1.2](usa-12.md).

_It's ironic that this game was the last one I got._

Here, as in the German version, the loading screen and Zone 0 have not been corrupted.

Language differences aside, these versions are near identical.

**Tiles**:

To accommodate all text, the number of tiles has been increased from `2123` to `2135`.
So many tiles were required in order to write text between the lines without limiting oneself to the dimensions of the tiles.

* Changed tiles: 2090-2108
* 11 new tiles: 2123-2134

These tiles are used in zones 76-77.

**Zones 76-77**:

![](images/zones/076fr.png) ![](images/zones/077fr.png)

**Zones 472, 572**: Major fixes missing.
It would be logical to assume that these bugs were discovered only in May 1997.

The differences in the structure of TGEN are large, 
but it seemed that not as much as with the German version.
