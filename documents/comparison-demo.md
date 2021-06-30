Yoda Stories Demo Version Comparison
===============================

1.1 vs Demo version
-------------------

t's time to take a look at what exactly was cut from the Demo version of Yoda Stories.
There are many differences, so we will probably omit a few of the least significant ones.

You cannot save and load.

* Executable file
* Startup screen
* Tiles
* Zones
* Puzzles
* Characters

**Executable file**

Modified About dialog.

![](images/about-demo.png)

Yoda(tm) Stories Demo

Deactivated menu items:

* Replay Story
* Load World
* Save World
* World Control
* S&tatistics

Moreover, even if you activate them, the game will turn them off again during the boot process.


**Startup screen**

![](images/zones/startup-demo.png)

The inscription "Demo".

**Tiles**

Desert and Forest tiles have been cleared.
In fact, there is one level in the ice, on the planet Neshtab.

So even if full functionality is restored, most of the levels will look bare and impassable.

* 0-6
* 8-17
* 19-84
* 86-111
* 115-149
* 151-154
* 156-162
* 235-250
* 281-284
* 311-343
* 345-350
* 356
* 364-372
* 642-647
* 879-897
* 1184-1185
* 1188-1195
* 1215-1217
* 1282-1283
* 1309
* 1344-1347
* 1353
* 1357-1359
* 1399-1402
* 1451-1473
* 1484-1517
* 1519-1539
* 1581-1595
* 1619-1624
* 1631-1645
* 1663-1664
* 1667-1672
* 1756-1760
* 1813-1814
* 1872-1879
* 1890-1893
* 2059-2074

Replaced tiles:

* 855
* 872
* 874-875
* 877-878
* 902
* 961
* 1170
* 2121-2122 (Demo)

Added tiles:

* 2123-2127 (Demo)

![](images/tiles/0855.png)![](images/tiles/0872.png)![](images/tiles/0874.png)
![](images/tiles/0875.png)![](images/tiles/0877.png)![](images/tiles/0878.png)
![](images/tiles/0902.png)![](images/tiles/0961.png)![](images/tiles/1170.png)
![](images/tiles/2121.png)![](images/tiles/2122.png) original

![](images/tiles/demo/0855.png)![](images/tiles/demo/0872.png)![](images/tiles/demo/0874.png)
![](images/tiles/demo/0875.png)![](images/tiles/demo/0877.png)![](images/tiles/demo/0878.png)
![](images/tiles/demo/0902.png)![](images/tiles/demo/0961.png)![](images/tiles/demo/1170.png)
![](images/tiles/demo/2121.png)![](images/tiles/demo/2122.png)![](images/tiles/demo/2123.png)
![](images/tiles/demo/2124.png)![](images/tiles/demo/2125.png)![](images/tiles/demo/2126.png)
![](images/tiles/demo/2127.png) demo


**Zones**

Since planets with deserts and jungles are not available in the demo version, 
and the tiles associated with them were removed, 
these tiles were also removed from all the corresponding zones (replaced with transparent tiles).

Some snow locations were also erased.

**Zone 0**: +1 instruction for Action 0

![](images/zones/startup-demo.png)

Since the phrase "Demo" is displayed on the top layer, its bottom tile is removed during the flight of the X-Wing.

`remove-tile: 5 5 2 0 0 ""`

**Zones 76-77**: added Demo.

![](images/zones/demo/076.png) ![](images/zones/demo/077.png)

**Zones 93-96**: removed all mini-quests from Yoda, the scene of his abduction.

![](images/zones/demo/093.png) ![](images/zones/demo/094.png) ![](images/zones/demo/095.png) ![](images/zones/demo/096.png)

Removed phrase: `Dagobah! Yoda must be around here somewhere. I better find him and see what's on his mind...`

izx4._unnamed2: 1 -> 0

**Zone 115**: removed DROP_ITEM [12; 11] enabled: 1; argument: 1198

![](images/zones/demo/115.png)

A strange decision, since this is a deserted location.

**Zone 146**:

![](images/zones/demo/146.png)

Quest items will never appear in this location.

**Zone 151**: spaceport.

![](images/zones/demo/151.png)

`This is the tiny Spaceport on Neshtab. In a real game, I might find myself in the DESERT or a RAIN FOREST as easily as here, but the DEMO is all ICE, and there's only one ending...`

New actions performed upon arrival:

```
Action 16
if
    standing-on: 8 11 997 28789 19488
then
    remove-tile: 8 10 1 21280 28257 ""
    remove-tile: 9 10 1 109 29772 ""
    remove-tile: 7 11 1 109 32412 ""
    remove-tile: 8 12 1 0 0 ""
    remove-tile: 9 12 1 0 0 ""
    set-rect-needs-display: 7 10 9 12 20500 ""
    speak-hero: 0 0 0 0 0 "This is the tiny Spaceport on Neshtab. In a real game, I might find myself in the DESERT or a RAIN FOREST as easily as here, but the DEMO is all ICE, and there's only one ending..."
    disable-action: 8 0 25924 28525 24916 ""
```

```
Action 18
if
    enter-by-plane: 321 0 0 0 0
    hero-is-at: 8 11 0 24964 321
then
    place-tile: 8 10 1 355 0 ""
    place-tile: 9 10 1 355 30972 ""
    place-tile: 7 11 1 355 0 ""
    place-tile: 8 12 1 355 0 ""
    place-tile: 9 12 1 355 30309 ""
    set-rect-needs-display: 7 10 9 12 8249 ""
    disable-action: 0 0 0 0 31164 ""
```

izx4._unnamed2: 1 -> 0

Snowdrift removed: [11; 1]

**Zone 152**:

![](images/zones/demo/152.png)

`Greetings, sir! Welcome to our DEMO. If you need my services, please don't hesitate to visit."`

`Greetings, sir! Welcome to our DEMO!
I'm a MEDICAL DROID, General Practitioner Class 2-1B, fully qualified to treat all injuries and wounds. If you need my services, please don't hesitate to visit.
Meanwhile, this is dangerous country, so stay on the lookout for WEAPONS! You can never have too many.
I like my patients well- equipped... that way they stay healthy, and I can relax.`

`Greetings, sir! Welcome to our DEMO!`

**Zone 160**:

![](images/zones/demo/160.png)

izx4._unnamed2: 1 -> 0

**Zone 176**:

![](images/zones/demo/176.png)

izx4._unnamed2: 0 -> 1

**Zone 203**:

![](images/zones/demo/203.png)

izx4._unnamed2: 1 -> 0

**Zone 204**:

![](images/zones/demo/204.png)

Type: EMPTY -> FIND

Modified hotspot: DROP_QUEST_ITEM [9; 8] enabled: 1; argument: 65535

**Zone 210**:

![](images/zones/demo/210.png)

Removed all provided items.

izx4._unnamed2: 1 -> 0

**Zone 246**:

![](images/zones/demo/246.png)

izx4._unnamed2: 1 -> 0

**Zone 258**:

![](images/zones/demo/258.png)

Removed all required and provided items.

**Zone 263**:

![](images/zones/demo/263.png)

`This isn't the real game. It's only a DEMO.`

izx4._unnamed2: 1 -> 0

**Zone 264**:

![](images/zones/demo/264.png)

izx4._unnamed2: 1 -> 0

`If you want WEAPONS or MEDICINE, drop something on the table here, and I'll give you a DEMO of how the system works...`

`Nice junk. Say, if you enjoy this DEMO, why not buy the real game? Then you can thaw out in some warmer terrain...`

**Zone 294**:

![](images/zones/demo/294.png)

Removed all provided items.

**Zone 316**:

![](images/zones/demo/316.png)

Small check change - removed the `tile-at-is: 444 4 3 1 12346` condition. What for? The Shield Generator is on the map.
If this check succeeds, Luke then says, `"Hey! that's the ship's shield generator over there... If I could disable the thing, it might cause a distraction..."`

![](images/tiles/demo/0444.png)

**Zone 317**:

![](images/zones/demo/317.png)

izx4._unnamed2: 1 -> 0

**Zone 326**:

![](images/zones/demo/326.png)

izx4._unnamed2: 1 -> 0

**Zone 327**:

![](images/zones/demo/327.png)

izx4._unnamed2: 1 -> 0

**Zone 328**:

![](images/zones/demo/328.png)

izx4._unnamed2: 1 -> 0

**Zone 336**:

![](images/zones/demo/336.png)

izx4._unnamed2: 1 -> 0

`Playing the DEMO of LucasArts' new Desktop Adventure, I see. Well, we're peaceful snow farmers. Go away!`

**Zone 385**:

![](images/zones/demo/385.png)

Disabled completion of the quest upon arrival.

**Zone 407**:

![](images/zones/demo/407.png)

Disabled quest completion.

`mark-as-solved: 0 0 0 0 0 ""`

**Zone 462**:

![](images/zones/demo/462.png)

This zone cannot appear in the game, however, the text in it is slightly different from the final version:

It was:

* Instruction 0: `Good work, stranger! For rescuing me, I'll show you where I hid the ¥ that I stole from those stormtroopers!`
* Instruction 1: `All right... where?`
* Instruction 2: `Just follow me...`

Became:

* Instruction 0: `Good work, stranger! What can I possibly give you in return for rescuing me?`
* Instruction 1: `You mentioned something about a ¥...?`
* Instruction 2: `That's right; I did. Well, follow me...`

**Zone 465**:

![](images/zones/demo/465.png)

Removed all required items.

**Zone 489**:

![](images/zones/demo/489.png)

Added one required item: 

![](images/tiles/demo/0449.png)

**Zone 535**:

![](images/zones/demo/535.png)

Yoda's hut.

Removed all mini-quests and Bobba Fett.

izx4._unnamed2: 1 -> 0


**Zone 596**:

![](images/zones/demo/596.png)

Another forest zone.

`move-hero-to: 10 8 0 0 0 ""`

replaced by

```
change-zone: 595 0 0 0 0 ""
move-hero-to: 10 8 12576 8224 -26528 ""
change-zone: 595 8 8 0 30561 ""
```

**Zone 605**:

![](images/zones/demo/605.png)

`Whoa... dark in this hovel. And what's that scuttling noise???"`

`Whoa... dark in this hovel. And what's that scuttling noise???."`

**Zone 642**:

![](images/zones/demo/642.png)

Another invisible zone. The place where Luke wakes up after being knocked out by fighters.
In the demo version, it looks rather crude, in the final it was quite heavily reworked.

Removed half of the actions, the rest have been redesigned.

**Puzzle 108**:

It was:

`Luke! Time it is for your training to advance a step...[CR2]The Empire has set up a HIDDEN FACTORY in the snowy wastes of planet Neshtab, where they are building stormtrooper droids. These robots could tip the balance against the Rebel Alliance.[CR2]Find this factory you must! Destroy it you must! [CR2]Here! Help you finish what you start, this will...`

Became:

`Hello! A ripple in the Force I feel... you are playing a DEMO of LucasArts' new Desktop Adventure, mmm? I thought so... fool Yoda you cannot! If you enjoy it, why not buy real game from your local retailer, mmm?[CR2]Now then, Luke! Time to continue your training...[CR2]The Empire has set up a HIDDEN FACTORY in the snowy wastes of planet Neshtab, where they are building combat droids. These robots could tip the balance against the Rebel Alliance.[CR2]Find this factory you must! Destroy it you must! [CR2]Here! Help you finish what you start, this will...`

Pay attention, `[CR2]` means two line breaks.


**Characters**:

Tank-Wander

frame1: [2120, 1959, 2120, 2119, 1959, 2120, 1958, 1959] -> [2119, 1959, 2119, 2119, 1959, 2119, 1958, 1959]

Tank-Patrol

frame1: [2120, 1959, 2120, 2119, 1959, 2120, 1958, 1959] -> [2119, 1959, 2119, 2119, 1959, 2119, 1958, 1959]

Why this was done is unclear, but in some areas these tanks were also swapped.

![](images/tiles/demo/2119.png) ![](images/tiles/demo/2120.png)

    