Zone Type
=========

Each zone has a type that is used in the world generation process. See the following table for a list of zone types:

| Type | Name                | Description                                                                                                                                                                |
|:----:|---------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| 0    | None                |                                                                                                                                                                            |
| 1    | Empty               | An empty zone.                                                                                                                                                             |
| 2    | Blockade North      | Blocks access to zones north of this one until solved.                                                                                                                     |
| 3    | Blockade South      | Blocks access to zones south of this one until solved.                                                                                                                     |
| 4    | Blockade East       | Blocks access to zones east of this one until solved.                                                                                                                      |
| 5    | Blockade West       | Blocks access to zones west of this one until solved.                                                                                                                      |
| 6    | Gateway Departure   | When resolved, this allows the player to reach a zone on the island that cannot otherwise be reached by moving between zones in the main world.                            |
| 7    | Gateway Destination | Counterpart of `Gateway Departure`. Transport will arrive here after leaving the departure area. Zones are connected via hotspots such as 'GatewayStart' and 'GatewayEnd'. |
| 8    | Room                | Rooms cannot be placed directly in the main world. Access to them is possible only through the hotspots 'DoorIn'/'DoorOut' or using the instructions 'ChangeZone'.         |
| 9    | Load                | This zone is displayed when creating a new story.                                                                                                                          |
| 10   | Goal                | `Goal` zones are used for the final puzzles in each story.                                                                                                                 |
| 11   | Town / Spaceport    | The starting zone on the main world. Every generated world has exactly one town/spaceport.                                                                                 |
| 12   | Unknown             |                                                                                                                                                                            |
| 13   | Win                 | Shown when the game is won. This zone also displays the score.                                                                                                             |
| 14   | Lose                | This zone is shown after the hero has died.                                                                                                                                |
| 15   | Trade               | A zoone where the player must exchange items with an NPC to solve a puzzle.                                                                                                |
| 16   | Use                 | In order to solve this zone a tool must be used somewhere on the zone.                                                                                                     |
| 17   | Find                | `Find` zones provide an item without requiring anything else to solve them.                                                                                                |
| 18   | Find Unique Weapon  | One of them will be located near the city. it provides the player with a unique weapon (*The Force* in Yoda Stories).                                                      |

In addition, some zones may have lists of tiles that can be used to solve the puzzle, 
trade with NPC, and tiles that can drop after solving the puzzle.
During world generation, these items are selected semi-randomly to create a new story each time.

On the world map neighboring zones can be visited by walking off the current zone. Additionally, zones are connected through doors.

In order to make zones a little more interesting to play and replay, the game includes a custom scripting language. These *actions* are defined per zone.

Special points of interest on a zone are marked by *hotspots*. 
These locations mark doors, or places where an item can be used, or an NPC be placed by the world generator.
