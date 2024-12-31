Tile Names and Genders
======================

TODO hex

#### TNAM Format

* 4 bytes: "`TNAM`"
* 4 bytes: section size
* TileNames, the last tileId = `-1` (`0xFFFF`), у него нет названия

TileNames Count = (SectionSize - 2) / 26

##### TileNames format

* 2 bytes: tileId
* 24 bytes: null-terminated tile name, название заканчивается байтом 0x00 (в название не включается).

#### TGEN Format

* 4 bytes: "`TGEN`"
* 4 bytes: section size (tiles count * 2)
* TileGenders - количество строго равно количеству тайлов

##### TileGenders format

* 2 bytes: gender


## Tile Names

Названия есть у предметов, а имена и названия у живых существ.

TODO таблица


## Tile Genders

В английском языке у слов нет рода. Максимум, что позволяет этот язык - обозначать множественное число.
Так что секции TGEN в американской версии Yoda Stories нет.

Другие Европейские языки куда ближе к пра-языку и могут иметь один из следующих родов:

* Мужской
* Женский
* Средний

Средний род есть только в Немецком языке, так как он является самым восточным из известных.


| Gender     | Value  |
|:----------:|:------:|
| Masculine  | 0      |
| Feminine   | 1      |
| Neuter     | 2      |
| Unknown    | 0x1684 |
 

A light run through the code showed that some letters are being checked, and that, most likely, the point here is just the endings of the words. And then it dawned on me! Did the Lucas have implemented word declension??? But words in German are not inflected. What then? Gender? I already have grouped tiles, and where TGEN = 1 there are actually tiles with female names. This is victory!

After studying the piece of code that interests me, the following algorithm was identified:

    If a substitution variable (¢ or ¥) is used, the last letter of the previous word is checked.
    If it is "r", then, depending on the TGEN (1 or 2), the ending of the word changes to "ie" or "as", respectively.


Substitution variables are only used in Instructions, which are part of Actions, which are part of Zones.

Genders in German are formed using the article, and what is most remarkable, genders in German and Russian do not always coincide. For some reason, their lightsaber is neuter.

So, der, die, das - that's the answer. It remains to check this in practice.

I open the DAT file and fill in all the tiles associated with the quest items with the values 01 00.

Now I need to find any dialogue that mentions quest items. Here. The rebel crashed in the jungle and needs a Transponder. We will definitely help him, but before that he will help us a little.


In the Yoda Stories Translation Tool I find this phrase, and in front of the variable I insert the article der.

Actually, I could change any dialogue by inserting "der ¥" into it, but, as you know, good thoughts come after.


I load the game before the dialogue, and here it is - the breakpoint worked, and then I received a confirmation of my guess!


It turns out that TGEN stands for Tile Gender. Suddenly.

The Spanish version code is more complicated. This language has only masculine and feminine gender, but the endings change in some eccentric way. In theory, the following replacement of the article is performed:

el -> la
un -> una

Логика работы для других языков должна быть аналогичная, предлагаем заинтересованных лиц изучить интересующую их версию игры.

В принципе, разработчики могли использовать пару битов атрибутов тайлов для обозначения пола.
Мы предполагаем, что идея перевода игры на другие языки возникла не сразу,
а когда такая необходимость появилась, то было проще создать новую секцию,
чем менять существующую, с вероятностью внести ошибки в уже протестированный код. 


Game state
----------

У нас уже есть строительные кирпичики игры. Тайлы обладают такими характеристиками как прозрачность,
проницаемость, подвижность и принадлежность к определённой группе.

Так же у тайлом могут быть:

* Название
* Род

С помощью тайлов можно нарисовать игровое пространство любой сложности, так как используется 3 слоя графики.
Они же представляют собой внешний вид предметов и составляют кадры анимации персонажей.

Так же тайлы могут иметь названия, например, названия предметов или имена персонажей.
Благодаря поддержке родов, названия в тексте получают верный артикль либо окончание.


Localization
------------

Названия тайлов переводить не только можно, но и нужно. Они встречаются в тексте игры.

Реализовать поддержку родов достаточно трудно потому, что вся логика находится в исполняемом файле игры,
как минимум нужно знать ассемблер процессоров `x86` и найти место в исполняемом файле,
где разместить код, поскольку, он наверняка не поместится.



Hacking possibilities
---------------------

TODO