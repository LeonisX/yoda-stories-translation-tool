Star Wars - Yoda Stories (USA) (20.03.1997) & Making Magic
==========================================================

* Region: `USA` (most likely Canada)
* Language: `English`
* Publisher: [`LucasArts Entertainment Company`](https://web.archive.org/web/19980214042448/http://www.lucasarts.com/org_index.html)
* Release date: `Spring 1997`
* Version: `1.1`

So far, we have at our disposal only the disk image, without printing.

This is a fairly rare disc. The game fixed bugs found by that time.
Similar fixes are implemented in all European releases.

It should be added that the second batch of fixes arrived later.
They were implemented in `Patch 6`, as well as in Yoda Stories `1.2`,
and partially in all European versions.

**Disk:**

* Recorded date: `20.03.1997 11:08:42`
* Volume Space Size (LBA): `249087`
* Volume Identifier: `YODA`
* Serial number: `3111830`
* Mastering codes:
   * `KAO OPTICAL PRODUCTS 3111830 ***RF105805-300*** IFPI L954`
   * `KAO OPTICAL PRODUCTS 311830 ***RF105805-200*** IFPI L954`

**Disc content:**

* `Star Wars: Yoda Stories (USA) (Rev 1)`
* `Star Wars: Making Magic (USA) (QA1.03) (03.09.1996)`
* Themed cursors, icons, wallpapers and sound effects for Windows.

Files changed:

* `YODESK.DTA`
* `YODESK.EXE`

Added an unwanted `YODESK.GID` file.


USA 1.1 version vs 1.0
----------------------

Since comparing changes in EXE files is quite time-consuming, we will focus our attention on the differences between DTA files.

Compared to the very first version of the game, several game locations have been fixed in 1.1: `151, 176, 203, 430, 535`.

**Zone 151**: Actions 4, 5

It was:

* Remove tile [11; 1; 2] (x, y, z coordinates)
* Remove tile [11; 2; 1]

Became:

* Place tile 1787 at [11; 1; 2] (x, y, z coordinates)
* Place tile 1787 at [11; 2; 2]
* Remove tile [11; 2; 1]

![](images/tiles/tauntaun.png)

This is a story with a `Tauntaun` randomly appearing on the right side of the screen.
Nice secret, you can find something in the equipment of that two-legged riding animal.

![](images/code/z151-a4-5.png)

Changed snowdrifts at the top of the map `151` (pink areas).

![](images/zones/151-diff.png)

**Zone 176**: new Monster `61` [7; 6] loot: 65535; dropsLoot: 0

![](images/tiles/1775.png)

![](images/zones/176.png)

Added a robot of the `R2` series, I do not remember whether something falls from it or not.
Much more interesting is the following fix:

`izx4:_unnamed2: 1 -> 0`

So far, one can only guess what the developers have changed here.

**Zone 203**: new Monster `61` [1; 3] loot: 65535; dropsLoot: 0

![](images/zones/203.png)

Another `R2` robot in a snowy location.

Also, in two places, tile `678` was replaced with tile `680`, but in fact these tiles are identical.

![](images/tiles/0678.png) ![](images/tiles/0680.png)

**Zone 430**: Action 4: fix in a rather long action, specifically in the NPC dialogue:

![](images/zones/430.png)

Invalid Y coordinate of the dialog.

![](images/code/z430-a4.png)

**Zone 535**: New Action 50 with 1 condition and 3 instructions:

![](images/zones/535.png)

```
Action 50
if
    placed-item-is: 5 5 1 780 809
then
    play-sound: 0
    remove-item: 809
    speak-npc: 5 5 "Han! Good to see him safe. Friends you cannot forget, mmm?"
```

![](images/tiles/0780.png) ![](images/tiles/0809.png)

The code is easy enough to understand. `Yoda` (tile `780`) is located at coordinates [5, 5, 1].
When you give `Han` to him (tile `809`), then Yoda delivers his speech.
In this case, tile `809` is removed from the inventory.

### Sound usage statistic

* `0` (schwing.wav): 496 (+1)

This is also true for all European versions of Yoda Stories.
