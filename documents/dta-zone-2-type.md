Zone Type
=========

Each zone has a type that is used in the world generation process. See the following table for a list of zone types:

| Type | Name                | Count | Description                                                                                                                                                                |
|:----:|---------------------|:-----:|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0    | None                |   -   |                                                                                                                                                                           |
| 1    | Empty               |  112  | An empty zone.                                                                                                                                                             |
| 2    | Blockade North      |   6   | Blocks access to zones north of this one until solved.                                                                                                                     |
| 3    | Blockade South      |   6   | Blocks access to zones south of this one until solved.                                                                                                                     |
| 4    | Blockade East       |   6   | Blocks access to zones east of this one until solved.                                                                                                                      |
| 5    | Blockade West       |   6   | Blocks access to zones west of this one until solved.                                                                                                                      |
| 6    | Gateway Departure   |  14   | When resolved, this allows the player to reach a zone on the island that cannot otherwise be reached by moving between zones in the main world.                            |
| 7    | Gateway Destination |  12   | Counterpart of `Gateway Departure`. Transport will arrive here after leaving the departure area. Zones are connected via hotspots such as 'GatewayStart' and 'GatewayEnd'. |
| 8    | Room                |  265  | Rooms cannot be placed directly in the main world. Access to them is possible only through the hotspots 'DoorIn'/'DoorOut' or using the instructions 'ChangeZone'.         |
| 9    | Load                |   1   | This zone is displayed when creating a new story.                                                                                                                          |
| 10   | Goal                |  15   | `Goal` zones are used for the final puzzles in each story.                                                                                                                 |
| 11   | Town / Spaceport    |   3   | The starting zone on the main world. Every generated world has exactly one town/spaceport.                                                                                 |
| 12   | Unknown             |   -   |                                                                                                                                                                           |
| 13   | Win                 |   1   | Shown when the game is won. This zone also displays the score.                                                                                                             |
| 14   | Lose                |   1   | This zone is shown after the hero has died.                                                                                                                                |
| 15   | Trade               |  69   | A zoone where the player must exchange items with an NPC to solve a puzzle.                                                                                                |
| 16   | Use                 |  56   | In order to solve this zone a tool must be used somewhere on the zone.                                                                                                     |
| 17   | Find                |  79   | `Find` zones provide an item without requiring anything else to solve them.                                                                                                |
| 18   | Find Unique Weapon  |   6   | One of them will be located near the city. it provides the player with a unique weapon (*The Force* in Yoda Stories).                                                      |

The number of zones like `Load`/`Win`/`Lose`, `Goal`, `Town` and `Find Unique Weapon` does not raise questions, there are as many as expected.

But the number of `Gateway Departure`/`Destination` frankly confuses.
However, everything is very simple, two "extra" zones are on Yoda's planet.

On each of the varieties of quest planets, two zones block progress in each of the sides of the world.


Load
----

**Zone**: 0

![](images/zones/000.png)

Win
---

**Zone**: 76

![](images/zones/076.png)

Lose
----

**Zone**: 77

![](images/zones/077.png)

Town
----

The hotspots `Ship From Planet` are located in these zones.

**Zones**: 2, 151, 476.

![](images/zones/002.png)
![](images/zones/151.png)
![](images/zones/476.png)


Find Unique Weapon
------------------

These are places of "`The Power`". Almost all hotspots `Drop Unique Weapon` are located in these zones.

**Zones**: 129, 272, 330, 369, 547, 640.

![](images/zones/129.png)
![](images/zones/272.png)
![](images/zones/330.png)
![](images/zones/369.png)
![](images/zones/547.png)
![](images/zones/640.png)

Goal
----

All 15 quests.

**Zones**: 265, 277, 296, 336, 374, 392, 408, 414, 446, 469, 495, 536, 556, 572, 630.

TODO quest title

![](images/zones/265.png)
![](images/zones/277.png)
![](images/zones/296.png)
![](images/zones/336.png)
![](images/zones/374.png)
![](images/zones/392.png)
![](images/zones/408.png)
![](images/zones/414.png)
![](images/zones/446.png)
![](images/zones/469.png)
![](images/zones/495.png)
![](images/zones/536.png)
![](images/zones/556.png)
![](images/zones/572.png)
![](images/zones/630.png)

Room
----

These zones are of different sizes, but for compactness, we chose several small ones.

![](images/zones/475.png)
![](images/zones/535.png)
![](images/zones/578.png)
![](images/zones/597.png)
![](images/zones/631.png)

Trade
-----

We will show only one zone for each planet.

![](images/zones/028.png)
![](images/zones/254.png)
![](images/zones/425.png)

Use
---

We will show only one zone for each planet.

![](images/zones/087.png)
![](images/zones/229.png)
![](images/zones/366.png)

Find
----

We will show only one zone for each planet.

![](images/zones/020.png)
![](images/zones/244.png)
![](images/zones/331.png)

Empty
-----

We will show only one zone for each planet.

![](images/zones/481.png)
![](images/zones/226.png)
![](images/zones/528.png)

Blockade North
--------------

**Zones**: 65, 66, 360, 390, 328, 435.

![](images/zones/065.png)
![](images/zones/066.png)
![](images/zones/360.png)
![](images/zones/390.png)
![](images/zones/328.png)
![](images/zones/435.png)

Blockade South
--------------

**Zones**: 73, 117, 251, 405, 436, 561.

![](images/zones/073.png)
![](images/zones/117.png)
![](images/zones/251.png)
![](images/zones/405.png)
![](images/zones/436.png)
![](images/zones/561.png)

Blockade East
-------------

**Zones**: 74, 116, 187, 192, 433, 554.

![](images/zones/074.png)
![](images/zones/116.png)
![](images/zones/187.png)
![](images/zones/192.png)
![](images/zones/433.png)
![](images/zones/554.png)

Blockade West
-------------

**Zones**: 99, 115, 196, 389, 434, 563.

![](images/zones/099.png)
![](images/zones/115.png)
![](images/zones/196.png)
![](images/zones/389.png)
![](images/zones/434.png)
![](images/zones/563.png)

Gateway Departure
-----------------

**Zones**: 93, 642.

The hotspots `Ship To Planet` are located in these zones.

![](images/zones/093.png)
![](images/zones/642.png)

Gateway Departure / Destination
-------------------------------

**Zones**: 105/106, 107/108, 142/143, 382/385, 384/402, 391/404,
463/464, 465/466, 542/543, 544/546, 595/596, 598/599.

These zones are respectively located hotspots `Vehicle To` and `Vehicle Back`.

![](images/zones/105.png)
![](images/zones/106.png)
![](images/zones/107.png)
![](images/zones/108.png)
![](images/zones/142.png)
![](images/zones/143.png)
![](images/zones/382.png)
![](images/zones/385.png)
![](images/zones/384.png)
![](images/zones/402.png)
![](images/zones/391.png)
![](images/zones/404.png)
![](images/zones/463.png)
![](images/zones/464.png)
![](images/zones/465.png)
![](images/zones/466.png)
![](images/zones/542.png)
![](images/zones/543.png)
![](images/zones/544.png)
![](images/zones/546.png)
![](images/zones/595.png)
![](images/zones/596.png)
![](images/zones/598.png)
![](images/zones/599.png)
