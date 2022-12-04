Characters
==========

TODO hex

#### CHAR Format

* 4 bytes: "`CHAR`"
* 4 bytes: section size 0x1946 (6470 bytes)
* 77 Characters, the last index = `-1` (`0xFFFF`), after these two bytes, reading data should be stopped.

#### Characters Format

* 2 bytes: index (0, 1, 2, ...)
* 4 bytes: "`ICHA`"
* 4 bytes: size
* 16 bytes: null-terminated character title
* 2 bytes: CharacterType
* 2 bytes: MovementType
* 2 bytes: probablyGarbage1
* 4 bytes: probablyGarbage2
* CharFrame #1
* CharFrame #2
* CharFrame #3

1 Character occupies 84 bytes.

##### CharFrame Format

* 8 tiles (2 bytes length), 16 bytes total.


#### CHWP Format

TODO hex

* 4 bytes: "`CHWP`"
* 4 bytes: section size 0x1D0 (464 bytes)
* CharacterWeapons, the last index = `-1` (`0xFFFF`), after these two bytes, reading data should be stopped.

##### CharacterWeapon Format

* 2 bytes: index (0, 1, 2, ...)
* 2 bytes: reference
* 2 bytes: health

1 CharacterWeapon occupies 6 bytes.

If the character referenced by index is a `monster`,
this is a reference to their `weapon`: Darth Vader: 69 (Evil Force), StormTrooper: 11 (Imperial Blaster), and so on.
Otherwise, this is the index of the weapon's `sound`.


#### CAUX Format

TODO hex

* 4 bytes: "`CAUX`"
* 4 bytes: section size 0x136 (310 bytes)
* CharacterAuxiliaries, the last index = `-1` (`0xFFFF`), after these two bytes, reading data should be stopped.

##### CharacterAuxiliary Format

* 2 bytes: index (0, 1, 2, ...)
* 2 bytes: damage

1 CharacterAuxiliary occupies 4 bytes.


## Characters

The structure of the `Character` object describes characteristics such as:

* Name
* Character type
* Movement type
* Health level
* Link to the weapon, or to the sound (if it is a weapon)
* Damage level
* 3 frames of animation

Thus, you can create the main character, hostile humanoids, unfriendly or neutral fauna,
robots, equipment and even weapons.

The main purpose of `Character` is to provide animation to an object and identify its type,
and, if necessary, provide it with additional properties and characteristics.


### Health

All characters have different health levels. Where health is not important, or regulated by the game engine,
it is equal to 1 (or 0). In particular, this applies to Luke and weapons.

The health level of ordinary monsters varies from 2 to 10.
Tough monsters and bosses can have up to 30 health,
and Bobba Fett is generally 99. Indestructible objects have a health level of -1 (`0xFFFF`).

Rare monster `Gurk` seems to be immortal.


### Damage

The damage of neutral characters is -1.
The weakest in the food chain are: `Luke` (damage 1), and his two lightsabers (damage 2 and 3 respectively).
Monster damage ranges from 2 to 10.
Strong monsters and bosses can knock out up to 30 health points at a time, `Bobba Fett`, by the way, only 10.


### Weapon

Luke Enemy and enemy Jawa are armed with simple Blasters. Darth Vader owns `Evil Force`.
Stormtroopers, Mercenaries, Battle Droids and Tanks are equipped with the `Imperial Blaster`.

Neutral robots, equipment and other fauna do not have weapons by default.

Weapon damage is taken into account if a shot is fired.
Melee damage depends on the monster's abilities and is generally different from weapon damage.

|  #  |     Tile                  |  Weapon           | Damage | Owners                     | Sound        |
|:---:|:-------------------------:|:-----------------:|:------:|:--------------------------:|:------------:|
| 9 | ![](images/tiles/0512.png)  | Blaster           | 4      | ![](images/tiles/0784.png) ![](images/tiles/1976.png) | blaster.wav  |
| 10 | ![](images/tiles/0513.png) | Blaster Rifle     | 6      |                            | rifle.wav    |
| 11 | ![](images/tiles/0513.png) | Imperial Blaster  | 5      | ![](images/tiles/0426.png) ![](images/tiles/0504.png) ![](images/tiles/0784.png) ![](images/tiles/0792.png) ![](images/tiles/0793.png) ![](images/tiles/0800.png) ![](images/tiles/1060.png) ![](images/tiles/1068.png) ![](images/tiles/1070.png) ![](images/tiles/1169.png) ![](images/tiles/1313.png) ![](images/tiles/1722.png) ![](images/tiles/1769.png) ![](images/tiles/1770.png) ![](images/tiles/1837.png) ![](images/tiles/1851.png) ![](images/tiles/1852.png) ![](images/tiles/1959.png) | impblst.wav  |
| 12 | ![](images/tiles/0514.png) | Thermal Detonator | 8      |                            | banglrg.wav  |
| 13 | ![](images/tiles/0511.png) | The Force         | 99     |                            | force.wav    |
| 14 | ![](images/tiles/0510.png) | Light Saber (New) | 3      |                            | saberswg.wav |
| 69 | ![](images/tiles/1955.png) | Evil Force        | 12     | ![](images/tiles/0788.png) | force.wav    |
| 75 | ![](images/tiles/0018.png) | Light Saber       | 2      |                            | saberswg.wav |

From the table it becomes clear that Luke Skywalker does not have any weapons by default.
The lightsaber is given to him by the game engine at the beginning of each mission, as soon as Luke arrives in zone `#93`.

Starting weapon(s), depending on completed missions:

| Missions |     Weapon(s)              |
|:--------:|:--------------------------:|
| 0-5      | ![](images/tiles/0018.png) |
| 5-10     | ![](images/tiles/0510.png) |
| 10-15    | ![](images/tiles/0510.png) ![](images/tiles/0511.png) |
| 16+      | ![](images/tiles/0510.png) ![](images/tiles/0511.png) ![](images/tiles/1432.png) |

> Few people remember that `Spirit Heart` once resurrects the main character in the event of his death.


### Garbage

Two characteristics that are currently considered unused in most cases have standard values:

* 2 bytes: probablyGarbage1: `0xFFFF`
* 4 bytes: probablyGarbage2: `0`

For `Light Saber` probablyGarbage1 = `149`. `Boba Fett` has `446` and `1` respectively.


### Character Types

The developers did not produce additional sections, placing the characters and weapons in one.
This can be confusing at first, but it's easy enough to distinguish them if you know the type:

| Id  | Character Type |
|:---:|:--------------:|
|  1  |     Hero       |
|  2  |     Enemy      |
|  4  |     Weapon     |

    
The `ENEMY` type is assigned to everything except for the main character and several types of weapons.
Generators, ventilation systems, gears and even neutral robots are all `ENEMY`!

Main character:

|  #  |     Tile                   |  Name           | Movement Type | Health | Melee Damage | Shoot Damage |
|:---:|:--------------------------:|:---------------:|:-------------:|:------:|:------------:|:------------:|
| 1   | ![](images/tiles/0799.png) | Luke Skywalker  | None          | 1      | 1            | -            |

Enemies:

|  #  |     Tile                   |  Name           | Movement Type | Health | Melee Damage | Shoot Damage |
|:---:|:--------------------------:|:---------------:|:-------------:|:------:|:------------:|:------------:|
| 1   | ![](images/tiles/0784.png) | BLANK           | Unspecific 3  | 30     | -1           | -            |
| 2   | ![](images/tiles/0784.png) | Jawa2           | Unspecific 3  | 30     | -1           | -            |
| 3   | ![](images/tiles/0786.png) | Mousebot        | Unspecific 5  | 2      | 3            | -            |
| 4   | ![](images/tiles/0792.png) | Probot-snow     | Wander        | 10     | 25           | 5            |
| 5   | ![](images/tiles/0796.png) | Mine Droid      | Droid         | 2      | 2            | -            |
| 6   | ![](images/tiles/0800.png) | Boba Fett       | Unspecific 1  | 99     | 10           | 5            |
| 7   | ![](images/tiles/1722.png) | ST-Hard         | Unspecific 1  | 6      | 5            | 5            |
| 8   | ![](images/tiles/1722.png) | ST-MildAttack   | Unspecific 2  | 4      | 3            | 5            |
| 15  | ![](images/tiles/1098.png) | Wampa           | Unspecific 1  | 8      | 15           | -            |
| 16  | ![](images/tiles/1169.png) | SuperTrooper    | Unspecific 1  | 10     | 8            | 5            |
| 17  | ![](images/tiles/1059.png) | TuscanHard      | Unspecific 1  | 5      | 3            | -            |
| 18  | ![](images/tiles/1059.png) | TuscanMildAttac | Unspecific 2  | 3      | 4            | -            |
| 19  | ![](images/tiles/1060.png) | Boushh          | Unspecific 4  | 4      | 5            | 5            |
| 20  | ![](images/tiles/1068.png) | Greedo          | Unspecific 1  | 6      | 5            | 5            |
| 21  | ![](images/tiles/1070.png) | Ree Yees        | Unspecific 4  | 5      | 6            | 5            |
| 22  | ![](images/tiles/1261.png) | Baby Wampa      | Unspecific 3  | 3      | 6            | -            |
| 23  | ![](images/tiles/1313.png) | Snowtrooper-H   | Unspecific 1  | 6      | 5            | 5            |
| 24  | ![](images/tiles/1313.png) | SnowTrooper-M   | Unspecific 2  | 4      | 3            | 5            |
| 25  | ![](images/tiles/1722.png) | ST- Sit         | Sit           | 8      | 6            | 5            |
| 26  | ![](images/tiles/1313.png) | Snowtrooper-Sit | Sit           | 6      | 6            | 5            |
| 27  | ![](images/tiles/1346.png) | Generator       | Animation     | -1     | -1           | -            |
| 28  | ![](images/tiles/1722.png) | ST- Patrol      | Patrol        | 6      | 5            | 5            |
| 29  | ![](images/tiles/1372.png) | SandWorm        | Unspecific 3  | 2      | 4            | -            |
| 30  | ![](images/tiles/1366.png) | DesertBeetle    | Unspecific 3  | 2      | 2            | -            |
| 31  | ![](images/tiles/0504.png) | Torture Droid   | Droid         | 5      | 20           | 5            |
| 32  | ![](images/tiles/1383.png) | Sarlacc         | Animation     | 4      | 15           | -            |
| 33  | ![](images/tiles/1386.png) | Scorpion        | Unspecific 4  | 5      | 10           | -            |
| 34  | ![](images/tiles/1404.png) | IceBug          | Unspecific 3  | 3      | 4            | -            |
| 35  | ![](images/tiles/1405.png) | SnowStar        | Unspecific 3  | 2      | 4            | -            |
| 36  | ![](images/tiles/0355.png) | TimerPatrol     | Patrol        | -1     | -1           | -            |
| 37  | ![](images/tiles/0355.png) | TimerWander     | Unspecific 3  | -1     | -1           | -            |
| 38  | ![](images/tiles/0784.png) | BrownJawaEnemy  | Unspecific 3  | 3      | 4            | 4            |
| 39  | ![](images/tiles/1913.png) | Snowman         | Unspecific 4  | 25     | 30           | -            |
| 40  | ![](images/tiles/1959.png) | Tank-Wander     | Wander        | 30     | 15           | 5            |
| 41  | ![](images/tiles/1959.png) | Tank-Patrol     | Patrol        | 30     | 15           | 5            |
| 42  | ![](images/tiles/1595.png) | Gurk            | Sit           | -1     | 15           | -            |
| 43  | ![](images/tiles/0784.png) | RedMadJawa      | Animation     | -1     | -1           | -            |
| 44  | ![](images/tiles/0784.png) | JawaBrnSit      | Sit           | 0      | 0            | 5            |
| 45  | ![](images/tiles/1595.png) | LittleRancor    | Unspecific 2  | 15     | 10           | -            |
| 46  | ![](images/tiles/1592.png) | RancorLarm      | Animation     | 15     | 20           | -            |
| 47  | ![](images/tiles/1800.png) | RancorRarm      | Animation     | 15     | 20           | -            |
| 48  | ![](images/tiles/1797.png) | RancorHead      | Animation     | 20     | 20           | -            |
| 49  | ![](images/tiles/1799.png) | RancorBody      | Animation     | 20     | 20           | -            |
| 50  | ![](images/tiles/0793.png) | Probot-desert   | Wander        | 15     | 25           | 5            |
| 51  | ![](images/tiles/1605.png) | Ewok            | Unspecific 3  | 30     | -1           | -            |
| 52  | ![](images/tiles/1837.png) | Imp-ScaredyGuy  | Scaredy       | -1     | 6            | 5            |
| 53  | ![](images/tiles/1632.png) | Bugs            | Animation     | -1     | -1           | -            |
| 54  | ![](images/tiles/0426.png) | InvisiSitOneHit | Sit           | 1      | -1           | -            |
| 55  | ![](images/tiles/1837.png) | Scardy-Killable | Scaredy       | 5      | 6            | 5            |
| 56  | ![](images/tiles/1739.png) | TurtleEnemy     | Unspecific 3  | 5      | 2            | -            |
| 57  | ![](images/tiles/1740.png) | BatThing        | Unspecific 3  | 4      | 2            | -            |
| 58  | ![](images/tiles/1744.png) | Jigokiller      | Unspecific 1  | 6      | 10           | -            |
| 59  | ![](images/tiles/1768.png) | ShivaDroid      | Wander        | 35     | -1           | -            |
| 60  | ![](images/tiles/1769.png) | IG88            | Unspecific 1  | 10     | 8            | 5            |
| 61  | ![](images/tiles/1776.png) | R2Unit          | Droid         | 35     | -1           | -            |
| 62  | ![](images/tiles/1770.png) | ForestTrooperM  | Unspecific 2  | 4      | 3            | 5            |
| 63  | ![](images/tiles/1770.png) | ForestTrooperH  | Unspecific 1  | 6      | 5            | 5            |
| 64  | ![](images/tiles/1770.png) | ForestTroopSit  | Sit           | 8      | 6            | 5            |
| 65  | ![](images/tiles/1846.png) | Gears           | Animation     | -1     | -1           | -            |
| 66  | ![](images/tiles/0426.png) | InvisEnemy-Sit  | Sit           | 6      | 4            | 5            |
| 67  | ![](images/tiles/1880.png) | SnowWorm        | Unspecific 4  | 8      | 6            | -            |
| 68  | ![](images/tiles/0788.png) | DarthVader      | Sit           | 5      | 8            | 12           |
| 70  | ![](images/tiles/1976.png) | LukeEnemy       | Unspecific 1  | 5      | 5            | 4            |
| 71  | ![](images/tiles/1851.png) | ImpOff-Green-H  | Unspecific 1  | 4      | 3            | 5            |
| 72  | ![](images/tiles/1852.png) | ImpOff-Grey-M   | Unspecific 2  | 4      | 3            | 5            |
| 73  | ![](images/tiles/1741.png) | BatPatrol       | Patrol        | -1     | -1           | -            |
| 74  | ![](images/tiles/2036.png) | Dainoughout     | Unspecific 4  | 12     | 15           | -            |
| 76  | ![](images/tiles/2039.png) | CeilingFan      | Animation     | -1     | -1           | -            |

> There are several invisible enemies in the game.


### The rarest characters

|  #  |     Tile                   |  Name           | Health | Damage | Zones              |
|:---:|:--------------------------:|:---------------:|:------:|:------:|:------------------:|
| 20  | ![](images/tiles/1068.png) | Greedo          | 6      | 5      | 72                 |
| 36  | ![](images/tiles/0355.png) | TimerPatrol     | -1     | -1     | 211                |
| 39  | ![](images/tiles/1913.png) | Snowman         | 25     | 30     | 223                |
| 42  | ![](images/tiles/1595.png) | Gurk            | -1     | 15     | 548                |
| 45  | ![](images/tiles/1595.png) | LittleRancor    | 15     | 10     | 379                |
| 46  | ![](images/tiles/1592.png) | RancorLarm      | 15     | 20     | 379                |
| 47  | ![](images/tiles/1800.png) | RancorRarm      | 15     | 20     | 379                |
| 48  | ![](images/tiles/1797.png) | RancorHead      | 20     | 20     | 379                |
| 49  | ![](images/tiles/1799.png) | RancorBody      | 20     | 20     | 379                |
| 53  | ![](images/tiles/1632.png) | Bugs            | -1     | -1     | 409                |
| 52  | ![](images/tiles/1837.png) | Imp-ScaredyGuy  | -1     | 6      | 412                |
| 60  | ![](images/tiles/1769.png) | IG88            | 10     | 8      | 519                |
| 64  | ![](images/tiles/1770.png) | ForestTroopSit  | 8      | 6      | 372                |
| 70  | ![](images/tiles/1976.png) | LukeEnemy       | 5      | 5      | 580                |
| 73  | ![](images/tiles/1741.png) | BatPatrol       | -1     | -1     | 624                |
| 74  | ![](images/tiles/2036.png) | Dainoughout     | 12     | 15     | 271                |
| 31  | ![](images/tiles/0504.png) | Torture Droid   | 5      | 20     | 316, 319           |
| 40  | ![](images/tiles/1959.png) | Tank-Wander     | 30     | 15     | 161, 645           |
| 54  | ![](images/tiles/0426.png) | InvisiSitOneHit | 1      | -1     | 6, 410             |
| 55  | ![](images/tiles/1837.png) | Scardy-Killable | 5      | 6      | 289, 412           |
| 68  | ![](images/tiles/0788.png) | DarthVader      | 5      | 8      | 266, 498           |
| 41  | ![](images/tiles/1959.png) | Tank-Patrol     | 30     | 15     | 110, 166, 357      |
| 65  | ![](images/tiles/1846.png) | Gears           | -1     | -1     | 412, 580, 613      |
| 1   | ![](images/tiles/0784.png) | BLANK           | 30     | -1     | 14, 54, 72, 286    |
| 28  | ![](images/tiles/1722.png) | ST- Patrol      | 6      | 5      | 285, 316, 345, 365 |
| 51  | ![](images/tiles/1605.png) | Ewok            | 30     | -1     | 470, 476, 561, 573 |
| 76  | ![](images/tiles/2039.png) | CeilingFan      | -1     | -1     | 126, 411, 412, 626 |
| 37  | ![](images/tiles/0355.png) | TimerWander     | -1     | -1     | 344, 345, 489, 496, 580 |

The invisible characters `TimerPatrol` and `TimerWander` are used as timers.
For example, in zone #211 `TimerPatrol` has a strictly defined trajectory,
and with each appearance at a certain point in the location, the counter increases by one.

#### Zones of the rarest characters

**Zone 72**: armed mercenary `Greedo`.

![](images/zones/072.png)

**Zone 211**: invisible `TimerPatrol` as timer.
If you do not come to the aid of the rebel within a certain time, he will die.

![](images/zones/211.png)

**Zone 223**: mini boss `Snowman`. 
It will be possible to fight with him only if more than 6 missions are successfully completed.
However, in this case, the probability of this meeting is small: 1 to 10.
If you manage to defeat the central snowman, then his brothers will appear in the four corners of the map.
After the final victory, Luke's health level will rise to 300,
and 7 Thermal Detonators will immediately appear in his inventory!

![](images/zones/223.png)

**Zone 548**: almost invulnerable `Gurk`. Visually, this monster strongly resembles `Little Rancor`,
and he calls himself `Grrk`, but in any case, ordinary weapons do not cause him any harm.
You must have `Gas Grenade` with you, without defeating him you will not receive a quest item.

![](images/zones/548.png)

**Zone 379**: mini boss `Rancor`, `Little Rancor`. There are a total of 7 monsters to defeat in the pit,
but `Rancor` is in four parts. However, both in appearance and in damage this monster is far from its TV original.

![](images/zones/379.png)

**Zone 409**: `Bugs` (Alessian Terror Moth).
They do not pose any danger, but they can scare city dwellers.
Like any other insects, they fly into the light.

![](images/zones/409.png)

**Zone 412**: cowardly imperial officer `Imp-ScaredyGuy`. 

![](images/zones/412.png)

**Zone 519**: robot-mercenary `IG-88B`. It does not cause any particular difficulties.

![](images/zones/519.png)

**Zone 372**: aggressive `ForestTroopSit`. It does not cause any particular difficulties.

![](images/zones/372.png)

**Zone 580**: 12 unfriendly `Luke` clones.

![](images/zones/580.png)

**Zone 624**: `BatPatrol`, aka `Jungle Ray`. Caught with `Grappling Hook`.

![](images/zones/624.png)

**Zone 271**: mini boss `Dianoga`, aka `Dainoughout`. You need to get her heart.

![](images/zones/271.png)


### Movement Types

All characters have one important characteristic - movement type.
There are 13 such types in total.

It is important to note the `NONE` type for weapons, as well as the `ANIMATION` type for static items and static monsters.

|  #  | Movement Type | Characters                  |
|:---:|:-------------:|:---------------------------:|
| 0   | None          | **Luke Skywalker**, all **weapons** |
| 1   | Unspecific 1  | ![](images/tiles/0800.png) ![](images/tiles/1059.png) ![](images/tiles/1068.png) ![](images/tiles/1098.png) ![](images/tiles/1169.png) ![](images/tiles/1313.png) ![](images/tiles/1722.png) ![](images/tiles/1744.png) ![](images/tiles/1769.png) ![](images/tiles/1770.png) ![](images/tiles/1851.png) ![](images/tiles/1976.png) |
| 2   | Unspecific 2  | ![](images/tiles/1059.png) ![](images/tiles/1313.png) ![](images/tiles/1595.png) ![](images/tiles/1722.png) ![](images/tiles/1770.png) ![](images/tiles/1852.png) |
| 3   | Unspecific 3  | ![](images/tiles/0355.png) ![](images/tiles/0784.png) ![](images/tiles/1261.png) ![](images/tiles/1366.png) ![](images/tiles/1372.png) ![](images/tiles/1404.png) ![](images/tiles/1405.png) ![](images/tiles/1605.png) ![](images/tiles/1739.png) ![](images/tiles/1740.png) |
| 4   | Sit           | ![](images/tiles/0426.png) ![](images/tiles/0784.png) ![](images/tiles/0788.png) ![](images/tiles/1313.png) ![](images/tiles/1595.png) ![](images/tiles/1722.png) ![](images/tiles/1770.png) |
| 5   | Unused        | Indy only                   |
| 6   | Unspecific 4  | ![](images/tiles/1060.png) ![](images/tiles/1070.png) ![](images/tiles/1386.png) ![](images/tiles/1880.png) ![](images/tiles/1913.png) ![](images/tiles/2036.png) |
| 7   | Unspecific 5  | ![](images/tiles/0786.png)  |
| 8   | Droid         | ![](images/tiles/0504.png) ![](images/tiles/0796.png) ![](images/tiles/1776.png) |
| 9   | Wander        | ![](images/tiles/0792.png) ![](images/tiles/0793.png) ![](images/tiles/1768.png) ![](images/tiles/1959.png) |
| 10  | Patrol        | ![](images/tiles/0355.png) ![](images/tiles/1722.png) ![](images/tiles/1741.png) ![](images/tiles/1959.png) |
| 11  | Scaredy       | ![](images/tiles/1837.png)  |
| 12  | Animation     | ![](images/tiles/0784.png) ![](images/tiles/1346.png) ![](images/tiles/1383.png) ![](images/tiles/1592.png) ![](images/tiles/1632.png) ![](images/tiles/1797.png) ![](images/tiles/1799.png) ![](images/tiles/1800.png) ![](images/tiles/1846.png) ![](images/tiles/2039.png) |

#### None

Luke Skywalker, all weapons. These objects, in principle, do not need a movement algorithm.

#### Animation

Character is in one place, but the algorithm allows, for example,
in the case of Sarlac's tentacles, pull them in the direction of the protagonist.

#### Droid

The droid has a certain probability of moving randomly, or,
chooses for itself the preferred direction of movement.
If the main character is in the field of view of the droid, and these are 2 cells,
then the droid with a certain degree of probability can begin to move towards him.

#### Patrol

The patrol moves along the trajectory set using `Waypoints` (see [Monsters](dta-zone.md)).

#### Scaredy

If the protagonist approaches within 6 squares or less, that frightened imperial officer starts to run away.
In a normal situation, it moves randomly.

#### Sit

A determined enemy always goes to the protagonist.

#### Wander

A mechanical enemy is wandering around the map.

#### Unspecific

Несколько достаточно сложных алгоритмов, работу которых не удастся описать простыми словами.


### Frames

To provide all 8 tiles of one frame, you need to place them in this order:

`up-down-up-left-down-up-right-down`

> Reminds me of a secret code from a 16 bit game.

Here you need to understand that diagonal movements in the game are allowed,
but there is no separate graphics for them - either an upward movement or a downward movement is drawn.

3 frames of animation are allotted for movement in any direction.
That is, if the movement is continuous, then when moving to each neighboring cell, one frame will be replaced by the next.

However, if you move without holding down the movement key, the first frame will always be displayed.

Here is an example of how the animation works:

![](images/tiles/chars/luke.gif)


Game state
----------

We have everything ready to create the game world.
Locations will be drawn using tiles.
We will release characters on the levels - the main character, hostile inhabitants, neutral NPCs and see who wins.


Localization
------------

There is no need to translate anything in these sections.


Hacking possibilities
---------------------

Almost any character in the game can be made into an invincible Hulk, or a stunted mob dying at one touch.
It is enough to adjust the level of health, weapon type and damage.

When adding a new character, you can give him a unique name, specify his type and mode of movement, as well as
adjust animation frames, including diagonal ones.

Also saturate the game with new weapons.
`BFG9000` with simultaneous damage to all mobs is not supported by the game engine,
but any other types of small arms and heavy weapons - no problem.
