Yoda Stories Graphics Translation Guide
==============================

[Содержание](translation-guide.md)

A fully translated game has its own localized ones: 

* Zone Actions
* Puzzles
* Tile names

Yoda Stories Translation Tool (YSTT) полностью автоматизирует процесс извлечения и вставки текста.

![](images/gui-actions.png)

Для всех групп текста используется один универсальный алгоритм:

1. Выгрузить оригинальный текст в текстовый документ
2. Перевести текст
3. Загрузить текст обратно в YSTT
4. Заменить текст в DTA файле

## Текстовый файл

Файлы с оригинальным текстом имеют расширение DOCX. Их можно открыть в Open Office или Microsoft Word.
Этот формат был выбран для того, чтобы в качестве образца сразу же видеть оригинал.
К тому же, такой формат облегчает проверку перевода и замену фраз в игре.

Кроме самого текста в документе есть служебные данные:

* Идентификаторы строк
* CRC32
* Source character encoding
* Destination character encoding

Идентификаторы строк необходимы для корректной замены текста в игре.

CRC32 это контрольная сумма файла YODESK.DTA. Изменять её не нужно. Если перевод выполнялся для другой версии игры, то будет выведено предупреждение.

Source character encoding это кодировка оригинальной игры. Изменять её не нужно. 

Destination character encoding - это кодировка того языка, на который будет переводиться игра.
Если здесь обозначена не ваша кодировка, то замените на свою, это важно.

Несколько советов:

1. Не трогайте идентификаторы строк. Они необходимы для корректной замены текста в игре.
2. Обязательно оставляйте специальные символы: ¥ и ¢
3. Для проверки орфографии, выделите свой текст и выберите в текстовом редакторе язык для него.
4. Если после загрузки текста в YSTT перевод имеет неверную кодировку, то поменяйте её в настройках утилиты.
5. После загрузки любого текстового документа Destination character encoding меняется на значение, указанное в нём.


## Zone Actions

Вся игровая логика каждой Зоны реализована в Actions. В игре встречаются абсолютно не интерактивные Зоны,
но есть и такие, у которых может быть до 15 Действий, и даже больше.

Каждой Действие состоит из Условий и Инструкций. Если все Условия удовлетворены, то выполняются все Инструкции.

Инструкции могут сопровождаться текстом. Таким образом реализованы все диалоги между Люком, NPC и врагами.

При переводе текста обращайте внимание на ID Зоны. 
Если вы не понимаете, о чём идёт речь в тексте, а такие места встречаются, то в YSTT можно посмотреть Зону и освежить свою память.

Файл: actions.docx


## Puzzles

Puzzles это другой способ взаимодействия с NPC.

Каждый паззл может содержать до 5 фраз следующего содержания:

* REQUEST
* THANK
* OFFER
* MISSION
* UNUSED

Например:

* REQUEST: My hyperdrive needs a new ALLUVIAL DAMPER! Do you think you could find one for me?
* THANK: My hyperdrive is as good as new!
* OFFER: In return, I can let you have this ALLUVIAL DAMPER... it's just the thing for your hyperdrive.

Миссии уполномочен рассказывать только Йода, например:

Luke! Great danger there is!

The Imperial Fleet has learned of the HIDDEN REBEL BASE on icy planet Thaldo. Attack is their plan! You must WARN the Rebels, Luke!

Only this can I give you...

Зная, как устроены Паззлы, вы с меньшей вероятностью выполните ошибку при переводе.

Файл: puzzles.docx


## Tile Names

Это самая простая, и в то же время самая кропотливая часть перевода. Пожалуй, лучше начинать именно с неё,
потому что названия предметов в изобилии встречаются и в тексте игры, а разночтений быть не должно.

В игре используется 245 уникальных предметов и существ!

Файл: tilenames.docx


### Character encodings

Несмотря на то, что Юникод на момент создания игры уже существовал, операционные системы семейства Windows 9x поддерживают его весьма ограниченно.
Зато активно использовались региональные кодировки, или кодовые страницы, это практика, известна ещё со времён DOS.
Идея заключается в том, что в зависимости от языка системы, одни и те же символы расширенной кодировки ASCII имели разное начертание.

Первые 127 символов кодировки ASCII оставались неизменными, но все последующие могли сильно различаться:

```
windows-1252: € ‚ƒ„…†‡ˆ‰Š‹Œ Ž  ‘’“”•–—˜™š›œ žŸ ¡¢£¤¥¦§¨©ª«¬­®¯°±²³´µ¶·¸¹º»¼½¾¿ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖ×ØÙÚÛÜÝÞßàáâãäåæçèéêëìíîïðñòóôõö÷øùúûüýþÿ
windows-1250: € ‚ „…†‡ ‰Š‹ŚŤŽŹ ‘’“”•–— ™š›śťžź ˇ˘Ł¤Ą¦§¨©Ş«¬­®Ż°±˛ł´µ¶·¸ąş»Ľ˝ľżŔÁÂĂÄĹĆÇČÉĘËĚÍÎĎĐŃŇÓÔŐÖ×ŘŮÚŰÜÝŢßŕáâăäĺćçčéęëěíîďđńňóôőö÷řůúűüýţ˙
windows-1251: ЂЃ‚ѓ„…†‡€‰Љ‹ЊЌЋЏђ‘’“”•–— ™љ›њќћџ ЎўЈ¤Ґ¦§Ё©Є«¬­®Ї°±Ііґµ¶·ё№є»јЅѕїАБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя
```

Это приводило к определённым трудностям в то время, но и при переводе игры этому следует уделить внимание.

Поскольку Java (язык, на котором написана утилита Yoda Stories Translation Tool) изначально использует Юникод, то 
для правильного извлечения, и последующей замены текста необходимо верно подобрать кодировки, поскольку выполняются такие преобразования:

Source character encoding (dumping) -> Unicode (internal representation) -> Destination character encoding (inserting)

Для оригинала игры на английском языке, подойдёт любая кодировка, поскольку вполне хватает первых 127 символов (почти).


#### Variables

Исключение составляют два символа: ¥ (0xA5; Ґ cyrillic) и ¢ (0xA2; ў cyrillic), которые используются в Zone Actions для подстановки названий предметов.
Своего рода переменные значения. 

При переводе важно, чтобы эти специальные символы оставались неизменными. В коде реализована такая проверка.

Поэтому оригинальная кодировка будет не ASCII, а windows-1252 (Latin-1). Эта кодировка автоматически подходит
для таких западноевропейских вариантов как: испанский, итальянский и французский.

В настоящее время всё ещё не найдены французский и итальянский переводы игры. Напишите мне на tv-games@mail.ru, если у вас есть эти игры. 


#### Word files

Если вы обратите внимание на файл [crcs.json](/crcs.json), то в нём как раз и описаны исходные кодировки для каждого известного релиза игры.

Она используется при дампинге текста из файла данных yodesk.dta.

В DOCX файлах это значение указано в справочных целях как "Source character encoding", и его нет смысла менять на что-то другое.

Зато есть смысл поменять значение "Destination character encoding", если игра переводится на язык, не принадлежащий кодировке windows-1252.


#### Encodings (code pages)

| Encoding | Name  |
|:-:|---|
| windows-1250 | Eastern European (Latin 2) |
| windows-1251 | Cyrillic (Slavic) |
| windows-1252 | Western European (Latin-1, ANSI) |
| windows-1253 | Greek |
| windows-1254 | Turkish (Latin 5) |
| windows-1255 | Hebrew |
| windows-1256 | Arabic |
| windows-1257 | Baltic |
| windows-1258 | Vietnamese |
| x-windows-874 | Thai |
| windows-31j | Japanese |
| x-windows-iso2022jp | Japanese ISO-2022 |
| x-mswin-936 | Chinese Simplified |
| x-windows-950 | Chinese Traditional |
| x-MS950-HKSCS | Chinese Traditional + Hong Kong |
| x-windows-949 | Korean |
| x-Johab | Korean (Johab) |

Остальные названия кодировок можно посмотреть в [этом документе](https://docs.oracle.com/javase/8/docs/technotes/guides/intl/encoding.doc.html) (вторая колонка). 

Если в программе не оказалось нужной вам кодировки, то добавьте её вручную в файл charsets.json.