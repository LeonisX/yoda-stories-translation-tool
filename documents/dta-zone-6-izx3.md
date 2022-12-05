Zone Auxiliary #3
=================

`IZX3` is the next auxiliary object, the purpose of which is to store a list of NPCs.

Non-playable characters perform an important function - they can communicate,
especially talkative humanoids know up to 5 phrases, but the most important thing is that they ask for something,
and having received the desired, they give the quest item as gratitude.

The NPC communication logic is described in the [PUZ2](dta-puz2.md) section, which we will study last.
And within the framework of `Zone Auxiliary #3`, we can add that when generating a map, an NPC is selected randomly from the proposed list.
This increases the variability of Yoda Stories.

		
NPCs
----

NPCs are represented in the game by a single tile and are not animated.

| TileId | Image                      | Name                | Uses | Zones |
|:------:|----------------------------|---------------------|------|-------|
| 1056 | ![](images/tiles/1056.png) | Nikto | 54 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1063 | ![](images/tiles/1063.png) | Abyssin | 54 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1061 | ![](images/tiles/1061.png) | Defel | 53 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1062 | ![](images/tiles/1062.png) | Groucho | 53 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1064 | ![](images/tiles/1064.png) | Nien Nunb | 53 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1067 | ![](images/tiles/1067.png) | Chico | 53 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1069 | ![](images/tiles/1069.png) | Harpo | 53 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1072 | ![](images/tiles/1072.png) | Bith | 53 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1052 | ![](images/tiles/1052.png) | Gnudo Heap | 52 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1057 | ![](images/tiles/1057.png) | Frodo | 52 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1058 | ![](images/tiles/1058.png) | Duros | 52 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1066 | ![](images/tiles/1066.png) | Brainee2 | 52 | 19, 23, 39, 75, 78, 80, 82, 83, 83, 84, 85, 86, 88, 90, 91, 138, ... |
| 1053 | ![](images/tiles/1053.png) | Advorzse | 51 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1055 | ![](images/tiles/1055.png) | Bilbo | 51 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1071 | ![](images/tiles/1071.png) | Labria | 51 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1054 | ![](images/tiles/1054.png) | Advorzse2 | 43 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
|  785 | ![](images/tiles/0785.png) | Captain Blob | 32 | 19, 23, 39, 75, 78, 80, 82, 83, 84, 85, 86, 88, 90, 91, 138, 148, ... |
| 1602 | ![](images/tiles/1602.png) | Asinus Testa | 29 | 150, 157, 231, 247, 300, 303, 307, 348, 353, 354, 359, 362, 368, 387, 423, 426, ... |
| 1600 | ![](images/tiles/1600.png) | Uncle Jimmy | 27 | 150, 157, 231, 300, 303, 307, 348, 353, 354, 359, 362, 368, 387, 423, 426, 431, ... |
| 1818 | ![](images/tiles/1818.png) | Tahmboix | 27 | 19, 23, 39, 75, 185, 300, 303, 307, 314, 322, 359, 362, 423, 431, 442, 458, ... |
| 1821 | ![](images/tiles/1821.png) | Porker | 27 | 19, 23, 39, 75, 185, 300, 303, 307, 314, 322, 359, 362, 423, 431, 442, 458, ... |
| 1823 | ![](images/tiles/1823.png) | Jimmy Corrigan | 27 | 19, 23, 39, 75, 185, 300, 303, 307, 314, 322, 359, 362, 423, 431, 442, 458, ... |
| 1603 | ![](images/tiles/1603.png) | Private Lime | 26 | 150, 157, 231, 300, 303, 348, 353, 354, 359, 362, 368, 387, 423, 426, 431, 442, ... |
| 1827 | ![](images/tiles/1827.png) | Nai'ah | 26 | 19, 23, 39, 75, 185, 300, 303, 307, 314, 322, 359, 362, 423, 431, 442, 458, ... |
| 1826 | ![](images/tiles/1826.png) | Xizor Sal Ud | 25 | 19, 23, 39, 75, 185, 300, 303, 307, 314, 322, 359, 362, 423, 431, 442, 458, ... |
| 1820 | ![](images/tiles/1820.png) | Dr. Filth | 23 | 185, 300, 303, 307, 314, 322, 359, 362, 423, 431, 442, 458, 461, 486, 503, 505, ... |
| 1824 | ![](images/tiles/1824.png) | Hiball | 23 | 185, 300, 303, 307, 314, 322, 359, 362, 423, 431, 442, 458, 461, 486, 503, 505, ... |
| 1825 | ![](images/tiles/1825.png) | Bill Carson | 22 | 185, 300, 303, 307, 314, 322, 359, 362, 423, 431, 442, 458, 461, 486, 503, 505, ... |
| 1831 | ![](images/tiles/1831.png) | Rumple | 22 | 23, 185, 300, 303, 307, 314, 359, 362, 423, 431, 442, 458, 461, 486, 503, 505, ... |
| 1832 | ![](images/tiles/1832.png) | Shaker | 22 | 23, 185, 300, 303, 307, 314, 359, 362, 423, 431, 442, 458, 461, 486, 503, 505, ... |
| 1833 | ![](images/tiles/1833.png) | Chubbs | 22 | 23, 185, 300, 303, 307, 314, 359, 362, 423, 431, 442, 458, 461, 486, 503, 505, ... |
| 1830 | ![](images/tiles/1830.png) | Betsy Page | 21 | 23, 185, 300, 303, 307, 314, 359, 362, 423, 442, 458, 461, 486, 503, 505, 512, ... |
| 1794 | ![](images/tiles/1794.png) | Yea Martini | 20 | 23, 185, 300, 303, 307, 314, 322, 423, 426, 431, 442, 458, 461, 486, 503, 505, ... |
| 1065 | ![](images/tiles/1065.png) | Brainee(left) | 19 | 83, 84, 86, 88, 148, 150, 303, 348, 359, 368, 423, 426, 431, 458, 486, 518, ... |
| 1822 | ![](images/tiles/1822.png) | Nanuk | 18 | 185, 300, 303, 307, 314, 322, 359, 431, 442, 458, 461, 512, 518, 521, 522, 565, ... |
| 1819 | ![](images/tiles/1819.png) | Limburger(Left) | 14 | 303, 307, 359, 423, 431, 442, 458, 486, 503, 512, 518, 522, 565, 582 |
| 1604 | ![](images/tiles/1604.png) | Back | 8 | 150, 348, 426, 458, 518, 522, 565, 582 |
|  780 | ![](images/tiles/0780.png) | Yoda | 5 | 93, 94, 95, 96, 535 |
| 1981 | ![](images/tiles/1981.png) | Imp Off One | 1 | 185 |
| 1982 | ![](images/tiles/1982.png) | Imp Off Too | 1 | 185 |

### Zone 185

In fact, there is not much to say about this zone, 
except that in the room you can randomly chat with imperial officers helping the rebels. 
In total, you can randomly meet 30 different NPCs at the location.
There are no special scripts here, the NPC is randomly generated on one of 
the [hotspots](dta-zone-3-hotspots.md) of the `Spawn Location` type.

![](images/zones/185h.png)
