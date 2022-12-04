Zone Auxiliary #2
=================

`IZX2` is a small helper object whose purpose is to store a list of `Provided Items`.

These are Items that can be gained when the zone is solved. Each occupies 2 bytes (tileId).
When generating a map, the `Provided Item` is randomly selected from the proposed list.
This increases the variability of the game.

We have calculated in which types of zones `Provided Items` can be found most often:

| Zone Type          | Zones count |
|:------------------:|:-----------:|
| Room               |     90      |
| Find               |     65      |
| Trade              |     28      |
| Use                |     24      |
| Goal               |     14      |
| Find Unique Weapon |      4      |


Provided Items
--------------

| TileId | Image                      | Name                | Uses | Zones                                                             |
|:------:|:--------------------------:|---------------------|:----:|-------------------------------------------------------------------|
|  431   | ![](images/tiles/0431.png) | Smoke Grenade | 144 | 4, 13, 15, 17, 19, 19, 20, 23, 25, 29, 32, 34, 35, 37, 39, 40, ... |
|  460   | ![](images/tiles/0460.png) | Sensor Pack | 143 | 4, 6, 12, 13, 15, 17, 19, 20, 23, 25, 27, 29, 32, 35, 37, 40, ... |
|  430   | ![](images/tiles/0430.png) | Gas Grenade | 141 | 4, 13, 15, 17, 19, 19, 20, 23, 25, 29, 32, 34, 35, 37, 39, 40, ... |
|  436   | ![](images/tiles/0436.png) | Vial of Tibanna Gas | 136 | 4, 6, 12, 13, 15, 19, 20, 23, 27, 29, 32, 37, 39, 40, 42, 45, ... |
|  479   | ![](images/tiles/0479.png) | Power Coupling | 136 | 6, 13, 15, 17, 20, 29, 34, 35, 37, 39, 42, 45, 48, 52, 67, 82, ... |
|  443   | ![](images/tiles/0443.png) | Bar of Durasteel | 133 | 4, 6, 12, 13, 15, 17, 19, 20, 23, 27, 35, 37, 40, 42, 45, 48, ... |
|  450   | ![](images/tiles/0450.png) | Lommite Crystal | 133 | 4, 6, 13, 15, 19, 20, 25, 35, 37, 40, 42, 45, 48, 52, 55, 57, ... |
|  453   | ![](images/tiles/0453.png) | Sensor Array | 133 | 4, 6, 12, 13, 15, 17, 19, 20, 23, 25, 29, 34, 42, 45, 48, 52, ... |
|  456   | ![](images/tiles/0456.png) | Fusion Welder | 133 | 6, 12, 15, 17, 19, 20, 23, 25, 27, 35, 37, 39, 40, 42, 52, 55, ... |
|  457   | ![](images/tiles/0457.png) | Beamdrill | 133 | 4, 6, 12, 15, 17, 19, 20, 25, 27, 34, 35, 37, 40, 42, 52, 55, ... |
|  461   | ![](images/tiles/0461.png) | Holocron | 132 | 4, 6, 15, 17, 20, 29, 37, 39, 40, 42, 45, 48, 52, 55, 67, 75, ... |
|  466   | ![](images/tiles/0466.png) | Power Converter | 131 | 4, 6, 12, 15, 17, 19, 20, 23, 25, 29, 32, 34, 37, 42, 42, 45, ... |
|  452   | ![](images/tiles/0452.png) | Sample of Ryll | 130 | 4, 6, 13, 15, 20, 27, 37, 39, 40, 42, 45, 48, 52, 55, 67, 72, ... |
|  454   | ![](images/tiles/0454.png) | Repulsor | 130 | 6, 12, 15, 17, 20, 25, 27, 29, 32, 35, 39, 42, 45, 48, 52, 55, ... |
|  481   | ![](images/tiles/0481.png) | Sample of Carbonite  | 130 | 4, 6, 12, 15, 17, 19, 20, 23, 29, 34, 37, 40, 42, 45, 48, 52, ... |
|  485   | ![](images/tiles/0485.png) | Homing Beacon | 130 | 4, 6, 12, 13, 15, 17, 19, 20, 29, 35, 40, 42, 45, 52, 55, 57, ... |
|  492   | ![](images/tiles/0492.png) | Hydrospanner | 130 | 6, 12, 17, 19, 20, 23, 27, 29, 32, 34, 35, 37, 39, 40, 48, 52, ... |
|  499   | ![](images/tiles/0499.png) | Breath Mask | 130 | 6, 12, 15, 17, 20, 27, 29, 32, 37, 39, 40, 42, 45, 48, 52, 55, ... |
|  438   | ![](images/tiles/0438.png) | Restraining Bolt | 129 | 6, 12, 13, 15, 17, 19, 20, 25, 27, 29, 32, 35, 37, 39, 40, 42, ... |
|  446   | ![](images/tiles/0446.png) | Comm Unit | 129 | 6, 12, 13, 15, 17, 20, 23, 25, 29, 35, 37, 39, 42, 45, 48, 52, ... |
|  515   | ![](images/tiles/0515.png) | IM Mine | 128 | 4, 15, 17, 20, 23, 25, 27, 34, 35, 37, 39, 40, 42, 45, 52, 55, ... |
|  432   | ![](images/tiles/0432.png) | Sonic Cleaver | 128 | 13, 15, 17, 19, 19, 20, 23, 25, 29, 34, 37, 39, 40, 42, 52, 55, ... |
|  523   | ![](images/tiles/0523.png) | Stasis Mine | 127 | 4, 15, 17, 20, 23, 25, 34, 35, 37, 39, 40, 42, 45, 52, 55, 67, ... |
|  462   | ![](images/tiles/0462.png) | Decoder | 127 | 4, 6, 12, 15, 17, 20, 29, 35, 37, 42, 48, 52, 55, 67, 75, 80, ... |
|  464   | ![](images/tiles/0464.png) | Transponder | 127 | 6, 15, 17, 20, 25, 29, 32, 37, 42, 45, 48, 52, 55, 67, 78, 80, ... |
|  469   | ![](images/tiles/0469.png) | Computer Probe | 127 | 6, 15, 20, 23, 29, 32, 35, 37, 42, 45, 48, 48, 52, 67, 75, 78, ... |
|  470   | ![](images/tiles/0470.png) | Droid Caller | 127 | 6, 15, 19, 20, 29, 32, 35, 37, 39, 40, 42, 48, 52, 57, 67, 75, ... |
|  447   | ![](images/tiles/0447.png) | Anti-Grav Generator | 126 | 6, 12, 13, 15, 17, 20, 23, 25, 29, 34, 42, 45, 52, 55, 57, 67, ... |
|  476   | ![](images/tiles/0476.png) | Locomotor | 126 | 4, 6, 12, 15, 17, 20, 23, 29, 34, 35, 37, 39, 42, 48, 52, 55, ... |
|  478   | ![](images/tiles/0478.png) | Glow Rod | 126 | 6, 15, 19, 20, 25, 29, 32, 37, 45, 52, 67, 86, 90, 91, 109, 111, ... |
|  484   | ![](images/tiles/0484.png) | Nav Card | 126 | 6, 15, 20, 23, 27, 29, 35, 37, 39, 40, 42, 45, 48, 52, 67, 72, ... |
|  439   | ![](images/tiles/0439.png) | Power Terminal | 125 | 6, 12, 13, 15, 17, 19, 25, 27, 29, 40, 42, 45, 48, 52, 55, 67, ... |
|  449   | ![](images/tiles/0449.png) | Data Card | 125 | 6, 12, 15, 17, 20, 23, 25, 27, 29, 37, 39, 40, 42, 48, 52, 67, ... |
|  491   | ![](images/tiles/0491.png) | Macrofuser | 125 | 6, 12, 15, 17, 19, 20, 23, 27, 29, 32, 34, 35, 37, 40, 52, 55, ... |
|  495   | ![](images/tiles/0495.png) | Macroscope | 125 | 6, 12, 17, 19, 20, 29, 32, 34, 35, 39, 40, 42, 45, 48, 52, 55, ... |
|  498   | ![](images/tiles/0498.png) | Telesponder | 125 | 6, 15, 17, 20, 25, 29, 32, 34, 35, 37, 40, 42, 48, 52, 55, 57, ... |
|  435   | ![](images/tiles/0435.png) | Alluvial Damper | 124 | 6, 12, 13, 15, 17, 19, 20, 23, 25, 27, 29, 32, 37, 42, 48, 52, ... |
|  440   | ![](images/tiles/0440.png) | Power Prybar | 124 | 4, 12, 15, 17, 19, 20, 25, 27, 29, 34, 35, 37, 39, 40, 52, 55, ... |
|  487   | ![](images/tiles/0487.png) | Electroscope | 124 | 6, 15, 17, 20, 27, 29, 32, 34, 35, 48, 52, 55, 67, 83, 85, 91, ... |
|  488   | ![](images/tiles/0488.png) | Rangefinder | 124 | 6, 13, 15, 17, 20, 34, 35, 39, 48, 52, 55, 57, 67, 83, 83, 85, ... |
|  444   | ![](images/tiles/0444.png) | Shield Generator | 123 | 6, 13, 15, 17, 20, 25, 27, 29, 37, 39, 42, 48, 55, 67, 75, 88, ... |
|  437   | ![](images/tiles/0437.png) | Thermal Cape | 121 | 6, 15, 17, 19, 20, 25, 37, 45, 48, 52, 55, 67, 75, 78, 109, 111, ... |
|  467   | ![](images/tiles/0467.png) | Motivator | 121 | 6, 15, 17, 19, 20, 29, 32, 34, 37, 39, 42, 48, 52, 67, 78, 82, ... |
|  490   | ![](images/tiles/0490.png) | Pair of Binoculars | 121 | 6, 13, 17, 20, 23, 29, 34, 35, 39, 48, 52, 55, 57, 67, 78, 83, ... |
|  497   | ![](images/tiles/0497.png) | Fusion Furnace | 121 | 4, 6, 12, 13, 17, 20, 27, 29, 32, 34, 39, 45, 52, 55, 57, 67, ... |
|  503   | ![](images/tiles/0503.png) | Comlink | 121 | 6, 12, 17, 19, 20, 29, 35, 37, 40, 42, 45, 48, 52, 55, 67, 83, ... |
|  500   | ![](images/tiles/0500.png) | Holocomm | 120 | 6, 17, 20, 27, 29, 34, 37, 42, 48, 52, 67, 78, 83, 88, 90, 91, ... |
|  501   | ![](images/tiles/0501.png) | Transfer Register | 120 | 6, 15, 17, 19, 20, 29, 37, 39, 40, 42, 48, 52, 67, 78, 83, 90, ... |
|  518   | ![](images/tiles/0518.png) | Fusion Cutter | 119 | 15, 17, 20, 23, 25, 27, 34, 35, 39, 40, 42, 45, 52, 55, 78, 85, ... |
|  458   | ![](images/tiles/0458.png) | Vocabulator | 119 | 6, 15, 17, 19, 20, 23, 29, 32, 37, 39, 40, 42, 45, 48, 52, 67, ... |
|  471   | ![](images/tiles/0471.png) | Crate of Spice | 119 | 4, 6, 12, 15, 17, 27, 29, 34, 37, 42, 45, 55, 67, 72, 78, 109, ... |
|  477   | ![](images/tiles/0477.png) | Electrolockpick | 119 | 12, 15, 19, 20, 23, 27, 29, 34, 35, 37, 39, 52, 57, 75, 78, 80, ... |
|  493   | ![](images/tiles/0493.png) | Imperial Belt | 117 | 6, 15, 17, 20, 25, 27, 29, 35, 37, 52, 55, 57, 67, 82, 83, 90, ... |
|  486   | ![](images/tiles/0486.png) | Drive Guide | 116 | 6, 12, 15, 17, 27, 29, 32, 34, 37, 39, 40, 42, 52, 55, 67, 75, ... |
|  489   | ![](images/tiles/0489.png) | Condenser Unit | 116 | 6, 12, 13, 15, 17, 20, 27, 29, 32, 34, 35, 45, 48, 52, 55, 57, ... |
|  463   | ![](images/tiles/0463.png) | Holocube | 115 | 6, 15, 20, 29, 35, 37, 39, 42, 48, 52, 67, 80, 82, 84, 86, 90, ... |
|  496   | ![](images/tiles/0496.png) | Utility Belt | 115 | 6, 17, 20, 25, 29, 35, 40, 45, 52, 55, 57, 67, 78, 83, 90, 91, ... |
|  441   | ![](images/tiles/0441.png) | Navicomputer | 113 | 6, 12, 15, 17, 19, 25, 27, 29, 37, 42, 48, 67, 72, 78, 85, 86, ... |
|  445   | ![](images/tiles/0445.png) | Drive Compensator | 112 | 6, 13, 15, 17, 20, 25, 29, 37, 42, 48, 52, 67, 111, 114, 118, 119, ... |
|  455   | ![](images/tiles/0455.png) | Hyperspace Compass | 112 | 6, 12, 15, 17, 25, 29, 34, 42, 48, 52, 67, 80, 82, 84, 90, 111, ... |
|  465   | ![](images/tiles/0465.png) | Droid Part | 112 | 4, 6, 12, 13, 15, 20, 23, 25, 29, 35, 37, 39, 45, 55, 57, 78, ... |
|  474   | ![](images/tiles/0474.png) | Green Key Card | 112 | 6, 12, 15, 20, 23, 23, 27, 29, 34, 37, 39, 42, 52, 57, 75, 80, ... |
|  475   | ![](images/tiles/0475.png) | Training Remote | 109 | 6, 15, 20, 23, 29, 40, 48, 52, 67, 78, 82, 91, 111, 119, 120, 124, ... |
|  472   | ![](images/tiles/0472.png) | Blue Key Card | 106 | 6, 12, 15, 20, 23, 27, 29, 34, 35, 37, 39, 42, 52, 57, 75, 80, ... |
|  473   | ![](images/tiles/0473.png) | Red Key Card | 104 | 12, 15, 19, 20, 23, 27, 29, 34, 37, 39, 42, 52, 57, 75, 80, 84, ... |
|  494   | ![](images/tiles/0494.png) | Generator | 104 | 6, 17, 20, 23, 32, 34, 45, 52, 55, 57, 67, 78, 83, 90, 91, 111, ... |
| 1292   | ![](images/tiles/1292.png) | Grappling Hook | 100 | 15, 37, 42, 45, 48, 52, 55, 67, 78, 85, 88, 90, 114, 124, 136, 138, ... |
|  434   | ![](images/tiles/0434.png) | Droid Detector | 97 | 6, 15, 17, 19, 23, 25, 27, 29, 39, 42, 55, 78, 111, 119, 120, 122, ... |
|  468   | ![](images/tiles/0468.png) | Energy Cell | 64 | 210, 235, 237, 246, 257, 258, 292, 294, 308, 317, 320, 323, 351, 353, 358, 359, ... |
|  483   | ![](images/tiles/0483.png) | Blumfruit | 63 | 82, 109, 126, 136, 146, 149, 150, 157, 185, 189, 197, 203, 213, 216, 218, 221, ... |
|  433   | ![](images/tiles/0433.png) | Ice Drill | 48 | 13, 138, 145, 146, 149, 157, 160, 167, 169, 170, 173, 176, 178, 189, 197, 199, ... |
|  429   | ![](images/tiles/0429.png) | 10,000 Credits | 47 | 4, 17, 20, 25, 37, 40, 42, 45, 48, 52, 55, 109, 111, 122, 126, 130, ... |
|  526   | ![](images/tiles/0526.png) | Sequencer Charge | 46 | 72, 150, 218, 221, 290, 292, 294, 323, 329, 354, 359, 362, 364, 365, 373, 388, ... |
|  428   | ![](images/tiles/0428.png) | Collapsible Bridge | 45 | 292, 329, 358, 359, 364, 373, 423, 424, 426, 432, 438, 440, 442, 443, 455, 461, ... |
|  527   | ![](images/tiles/0527.png) | Sequencer Charge | 43 | 72, 150, 221, 290, 292, 294, 323, 329, 354, 359, 362, 364, 365, 373, 388, 428, ... |
|  508   | ![](images/tiles/0508.png) | Purple Key Card | 40 | 150, 185, 231, 247, 253, 290, 294, 327, 329, 353, 354, 359, 362, 368, 387, 423, ... |
|  509   | ![](images/tiles/0509.png) | Yellow Key Card | 40 | 150, 185, 231, 247, 253, 290, 294, 327, 329, 353, 354, 359, 362, 368, 387, 423, ... |
|  516   | ![](images/tiles/0516.png) | Orange Key Card | 39 | 150, 185, 216, 231, 247, 259, 290, 294, 329, 353, 354, 359, 362, 368, 387, 423, ... |
| 1351   | ![](images/tiles/1351.png) | Energy Relay | 30 | 4, 15, 17, 19, 25, 25, 27, 29, 34, 37, 39, 40, 42, 45, 48, 52, ... |
| 1243   | ![](images/tiles/1243.png) | Key Card | 29 | 6, 15, 19, 23, 25, 27, 32, 34, 37, 39, 45, 55, 75, 80, 82, 83, ... |
| 1357   | ![](images/tiles/1357.png) | Droid Body | 26 | 4, 13, 15, 17, 19, 25, 27, 29, 34, 37, 39, 42, 45, 48, 52, 55, ... |
|  502   | ![](images/tiles/0502.png) | C-3PO's Head | 13 | 13, 25, 57, 67, 83, 84, 109, 118, 353, 354, 358, 362, 455 |
| 1350   | ![](images/tiles/1350.png) | Jawa | 12 | 4, 15, 17, 19, 29, 34, 37, 75, 82, 353, 354, 362 |
|  421   | ![](images/tiles/0421.png) | Locator | 11 | 127, 128, 228, 331, 335, 381, 413, 516, 625, 638, 639 |
| 1246   | ![](images/tiles/1246.png) | Ladder | 11 | 388, 424, 440, 442, 462, 511, 522, 550, 551, 552, 582 |
|  511   | ![](images/tiles/0511.png) | THE FORCE | 6 | 129, 273, 330, 370, 547, 640 |
|  514   | ![](images/tiles/0514.png) | Thermal Detonator | 2 | 387, 458 |
|  809   | ![](images/tiles/0809.png) | Han Solo | 2 | 72, 275 |
| 1353   | ![](images/tiles/1353.png) | C-3PO | 2 | 265, 401 |
|  459   | ![](images/tiles/0459.png) | Adegan Crystal | 2 | 235, 630 |
|  504   | ![](images/tiles/0504.png) | ??? #504 | 2 | 288, 348 |
|  517   | ![](images/tiles/0517.png) | ??? #517 | 1 | 216 |
|  524   | ![](images/tiles/0524.png) | Mine Pin | 1 | 555 |
|  529   | ![](images/tiles/0529.png) | End8A | 1 | 495 |
| 1828   | ![](images/tiles/1828.png) | END10A | 1 | 408 |
| 1829   | ![](images/tiles/1829.png) | END10B | 1 | 408 |
|  810   | ![](images/tiles/0810.png) | END1A | 1 | 287 |
|  816   | ![](images/tiles/0816.png) | END1B | 1 | 287 |
| 1596   | ![](images/tiles/1596.png) | END3A | 1 | 374 |
| 1597   | ![](images/tiles/1597.png) | END3B | 1 | 374 |
| 2114   | ![](images/tiles/2114.png) | Chewbacca | 1 | 641 |
| 1354   | ![](images/tiles/1354.png) | END2B | 1 | 265 |
| 1617   | ![](images/tiles/1617.png) | END4A | 1 | 392 |
| 1618   | ![](images/tiles/1618.png) | END4B | 1 | 392 |
|  610   | ![](images/tiles/0610.png) | Sith Amulet | 1 | 495 |
| 1378   | ![](images/tiles/1378.png) | END13A | 1 | 296 |
| 1379   | ![](images/tiles/1379.png) | END13B | 1 | 296 |
| 1650   | ![](images/tiles/1650.png) | END12A | 1 | 446 |
| 1651   | ![](images/tiles/1651.png) | END12B | 1 | 446 |
| 1652   | ![](images/tiles/1652.png) | END16A | 1 | 414 |
| 1653   | ![](images/tiles/1653.png) | END16B | 1 | 414 |
| 1419   | ![](images/tiles/1419.png) | END15A | 1 | 336 |
| 1420   | ![](images/tiles/1420.png) | END15B | 1 | 336 |
| 1935   | ![](images/tiles/1935.png) | End7A | 1 | 536 |
| 1936   | ![](images/tiles/1936.png) | End7B | 1 | 536 |
| 1983   | ![](images/tiles/1983.png) | END9B | 1 | 630 |
| 1215   | ![](images/tiles/1215.png) | DataCube | 1 | 401 |
| 1984   | ![](images/tiles/1984.png) | END6B | 1 | 572 |
|  448   | ![](images/tiles/0448.png) | Lantern of Sacred Light | 1 | 572 |
| 1985   | ![](images/tiles/1985.png) | END11A | 1 | 556 |
| 1986   | ![](images/tiles/1986.png) | END11B | 1 | 556 |
| 1245   | ![](images/tiles/1245.png) | Rebel ID Card | 1 | 518 |
| 1788   | ![](images/tiles/1788.png) | Ending5A | 1 | 469 |
| 1789   | ![](images/tiles/1789.png) | Ending5B | 1 | 469 |
|  510   | ![](images/tiles/0510.png) | Lightsaber | 1 | 623 |

Most of the rarely used items are generally hidden from the player's eyes.
