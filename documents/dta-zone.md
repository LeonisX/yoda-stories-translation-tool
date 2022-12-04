Zones
=====

`ZONE` is the largest section of the DTA file, it exceeds the `TILE` section by 100 KB.
In addition, this is the most complicated section, so we apologize in advance,
if there are errors in this document.
[Yoda Stories Translation Tool project source code](https://github.com/LeonisX/yoda-stories-translation-tool/tree/main/src/main/java/md/leonis/ystt/model/yodesk) can be used as a sample data structure.

Zones are the game's maps, where the hero can walk around, solve puzzles and fight monsters.

They are made up of `9x9` or `18x18` tiles in three layers.
A ground layer, and object layer, used for collision detection, and a roof layer that is rendered above the hero.

The zone is a completely self-sufficient object, consisting of:

* 3 layers of tiles
* Monsters, enemies and NPCs
* Required, Provided and Goal items
* Actions, Scripts and Hotspots

During world generation, quest items and NPCs are randomly selected for the zone from the available list.
These items are selected semi-randomly to create a new story each time.

On the world map neighboring zones can be visited by walking off the current zone.
Additionally, zones are connected through doors.

In order to make zones a little more interesting to play and replay,
the game includes a custom scripting language. These *actions* are defined per zone.

Special points of interest on a zone are marked by *hotspots*. 
These locations mark doors, or places where an item can be used, or an NPC be placed by the world generator.


Format
------

TODO hex

Subsection headers:

* `ZONE`
  * `IZON`
    * `IZAX`
    * `IZX2`
    * `IZX3`
    * `IZX4`
    * `IACT`

There can be several `IACT` sections within one zone.


### ZONE format

* 4 bytes: "`ZONE`"
* 2 bytes: zones count (`0x0292` in hex)
* 658 Zones `IZON`

Note that following the `ZONE` chunk, there is no information about the full size of the `ZONE` section.
It becomes known only after all zones are loaded into memory.
The formula is:

size = sum(IZON.size + 2 + 4) + 2 (zones count)

_The size of the `IZON` section does not include the planet type (2 bytes) and the size of the `IZON` chunk itself (4 bytes)._


### IZON format

* 2 bytes: Planet Type
* 4 bytes: IZON section size (everything after "`IZON`")
* 2 bytes: zone sequence number, starting from `0`
* 4 bytes: "`IZON`"
* 4 bytes: size2: this is the size of the "`IZON`" chunk (4 bytes) + the size of zone spots. Always `0x1FA` (506) or `0x7AC` (1964) bytes
* 2 bytes: width of the zone in tiles. Either `9` or `18`.
* 2 bytes: height of the zone in tiles. Either `9` or `18`.
* 4 bytes: Zone Type
* 2 bytes: Shared Counter: scripting register shared between the zone and its rooms.
* 2 bytes: Planet Type (repeat)
* Zone Spots, consisting of tiles, 3 layers starting from the bottom. Amount: (width*height). One tileId takes 2 bytes.
* 2 bytes: HotSpots count
* HotSpots
* Zone Auxiliary #1 `IZAX`
* Zone Auxiliary #2 `IZX2`
* Zone Auxiliary #3 `IZX3`
* Zone Auxiliary #4 `IZX4`
* 2 bytes: Actions count
* Actions `IACT`

#### HotSpot format

* 4 bytes: HotSpot type
* 2 bytes: x
* 2 bytes: y
* 2 bytes: enabled
* 2 bytes: argument


### Zone Auxiliary #1 format

* 4 bytes: "`IZAX`"
* 4 bytes: size
* 4 bytes: _unnamed2 flag
* 2 bytes: Monsters count
* Monsters
* 2 bytes: Required Items count
* Required Items
* 2 bytes: Goal Items count
* Goal Items

#### Monsters format

* 2 bytes: Character ID
* 2 bytes: x
* 2 bytes: y
* 2 bytes: loot - references the item (loot - 1) that will be dropped if the monster is killed.
If set to `0xFFFF` the current zone's quest item will be dropped.
* 4 bytes: dropsLoot - if this field is anything other than `0` the monster may drop an item when killed.
* 4x Waypoints

#### Waypoints format

* 4 bytes: x
* 4 bytes: y

### Zone Auxiliary #2 format

* 4 bytes: "`IZX2`"
* 4 bytes: size
* 2 bytes: Provided Items count
* Provided Items: items that can be gained when the zone is solved. Each occupies 2 bytes (tileId)

### Zone Auxiliary #3 format

* 4 bytes: "`IZX3`"
* 4 bytes: size
* 2 bytes: NPCs count
* NPCs: non-playable characters that can be placed in the zone to trade items with the hero. Each occupies 2 bytes (tileId)

### Zone Auxiliary #4 format

* 4 bytes: "`IZX4`"
* 4 bytes: size
* 2 bytes: _unnamed2 flag


### Actions format

* 4 bytes: "`IACT`"
* 4 bytes: size
* 2 bytes: Conditions count
* Conditions
* 2 bytes: Instructions count
* Instructions

#### Conditions format

* 2 bytes: Condition opcode, one from 36 available
* 10 bytes: 5 arguments, each takes 2 bytes
* 2 bytes: text length (always 0)
* Text: never used, but seems to be included to share the type structure with Instructions.

#### Instructions format

* 2 bytes: Instruction opcode, one from 38 available
* 10 bytes: 5 arguments, each takes 2 bytes
* 2 bytes: text length
* Text

It's hard to imagine how long it took to understand such a confusing data structure!

Let's examine the objects in the order they appear.

[source 1](https://www.webfun.io/docs/gameplay/zones.html),