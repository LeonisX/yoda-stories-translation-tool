Sounds
======

Despite the fact that everything is more or less clear with sound effects, some points are still worth voicing.

Some sound effects, such as the introductory melody, are triggered directly by the game engine.
The vast majority of sound effects described in `YODESK.DTA`,
are called in many scripts of various game zones, and sound when using various types of weapons.

#### Format

* 4 bytes: "`SNDS`"
* 4 bytes: section size (`872` bytes)
* 2 bytes: number of melodies (`-64`)
* `64` SFX File Names

Interestingly, the number of melodies is negative. Why this was done is unclear.

##### SFX File Names format:

* 2 bytes: length of the file name (including the terminal character with code `0x00`)
* File name + terminal symbol with code `0x00`


#### Sound quality

The sound quality is low, apparently, this was done for the sake of performance.

* Melodies: 11025 Hz PCM, 176 kbps, 16 bit, Mono
* Sound effects: 11025 Hz PCM, 88 kbps, 8 bit, Mono

#### Statistics on the use of sound effects in game zones

|  #  |     Sound    | Usages |
|:---:|:------------:|:------:|
|  0  | schwing.wav  |   493  |
|  1  | push.wav     |   96   |
|  2  | switch-7.wav |   105  |
|  3  | shpoonk.wav  |   42   |
|  4  | hurt.wav     |   56   |
|  5  | eep.wav      |   2    |
|  6  | nogo.wav     |   436  |
|  7  | blaster.wav  |        |
|  8  | rifle.wav    |   1    |
|  9  | saberswg.wav |        |
| 10  | force.wav    |   13   |
| 11  | banglrg.wav  |   6    |
| 12  | bangmed.wav  |   47   |
| 13  | banglrg.wav  |   37   |
| 14  | banghuge.wav |   5    |
| 15  | doorelec.wav |   141  |
| 16  | doorpnum.wav |   394  |
| 17  | hugedoor.wav |   44   |
| 18  | switch-4.wav |   16   |
| 19  | chestsml.wav |   59   |
| 20  | chestlrg.wav |   156  |
| 21  | blip.wav     |   22   |
| 22  | chirp.wav    |   31   |
| 23  | switch-6.wav |        |
| 24  | buzzlow.wav  |   19   |
| 25  | buzzhigh.wav |   19   |
| 26  | switch-2.wav |   7    |
| 27  | switch-3.wav |   12   |
| 28  | switch-4.wav |   10   |
| 29  | moverock.wav |   7    |
| 30  | moverock.wav |   13   |
| 31  | saberout.wav |   24   |
| 32  | armed.wav    |   2    |
| 33  | grenade.wav  |   3    |
| 34  | locator.wav  |   4    |
| 35  | mapcls.wav   |   2    |
| 36  | impblst.wav  |        |
| 37  | vader.wav    |   27   |
| 38  | xwingin.wav  |   23   |
| 39  | xwingout.wav |   29   |
| 40  | xwingby.wav  |   10   |
| 41  | tieby.wav    |   2    |
| 42  | r2d2-1.wav   |   2    |
| 43  | r2d2-2.wav   |        |
| 44  | wookie.wav   |   18   |
| 45  |              |        |
| 46  | speeder.wav  |   17   |
| 47  | truckout.wav |   8    |
| 48  | walker.wav   |   53   |
| 49  | transprt.wav |   2    |
| 50  | splash.wav   |   16   |
| 51  | bleepsml.wav |        |
| 52  | armforce.wav |   1    |
| 53  | buzzmed.wav  |   2    |
| 54  | bong.wav     |   22   |
| 55  | cantina.wav  |   7    |
| 56  | crash.wav    |   19   |
| 57  | truckin.wav  |   5    |
| 58  | flourish.wav |   79   |
| 59  | emptheme.wav |   10   |
| 60  | mystery.wav  |   26   |
| 61  | opening.wav  |        |
| 62  | tryagain.wav |        |
| 63  | youwin.wav   |        |

#### 10 most popular sound effects

|  #  |     Sound    | Usages |
|:---:|:------------:|:------:|
|  0  | schwing.wav  |   493  |
|  6  | nogo.wav     |   436  |
| 16  | doorpnum.wav |   394  |
| 20  | chestlrg.wav |   156  |
| 15  | doorelec.wav |   141  |
|  2  | switch-7.wav |   105  |
|  1  | push.wav     |   96   |
| 58  | flourish.wav |   79   |
| 19  | chestsml.wav |   59   |
|  4  | hurt.wav     |   56   |

#### Using sounds in different versions of the game

|  #  |     Sound    | Eng-1.2 | Eng-1.1 | Eng-1.0 | Eng-Demo |
|:---:|:------------:|:-------:|:-------:|:-------:|:--------:|
|  0  | schwing.wav  |   493   |   496   |   495   |   482   |
|  3  | shpoonk.wav  |   42    |   42    |    42   |   41    |
| 39  | xwingout.wav |   29    |   29    |    29   |   27    |
| 58  | flourish.wav |   79    |   76    |   76    |   76    |
| 60  | mystery.wav  |   26    |   23    |   23    |   23    |

#### Weapon sounds

|  #  |     Sound    |       Weapon        |
|:---:|:------------:|:-------------------:|
|  7  | blaster.wav  | Blaster             |
|  8  | rifle.wav    | Blast Rifle         |
|  9  | saberswg.wav | Light Saber 1       |
|  9  | saberswg.wav | Light Saber (Newer) |
| 10  | force.wav    | The Force           |
| 10  | force.wav    | Evil Force          |
| 11  | banglrg.wav  | Thermal Detonator   |
| 36  | impblst.wav  | Imperial Blaster    |

#### Other sounds

These sounds and melodies just described in YODESK.DTA.
Some of them use the game engine, the purpose of others unknown.

|  #  |     Sound    |            Description           |
|:---:|:------------:|:--------------------------------:|
| 23  | switch-6.wav | Is it used???                    |
| 43  | r2d2-2.wav   | R2-D2 replicas                   |
| 45  |              | Empty entry                      |
| 51  | bleepsml.wav | Probably is used, but where?     |
| 61  | opening.wav  | Sounds when the game starts      |
| 62  | tryagain.wav | Sounds on the screen "Try Again" |
| 63  | youwin.wav   | Sounds on the screen "You Win"   |

#### Music Credits

* Cantina Band, Ben's Death and TIE Fighter Attack, Star Wars (Main Title): John Williams, 1977
* The Magic Tree, Lando's Palace, The Empire Strikes Back (Finale): John Williams, 1980
* Yoda's Theme: John Williams, 1984
* The Empire Strikes Back/The Imperial Probe: John Williams, 1996
* The Moisture Farm: John Williams, 1997

#### SFX Credits

Several sound effects have been [identified](https://soundeffects.fandom.com/wiki/Star_Wars:_Yoda_Stories).
In particular, the [three lightsaber effects](https://soundeffects.fandom.com/wiki/SKYWALKER_LIGHTSABER_SOUND) belong to Skywalker Sound,
and were recorded by Ben Burtt in 1976.

EEP.WAV is a slightly modified version of [PLUCK, CARTOON - VAROOP](https://soundeffects.fandom.com/wiki/Sound_Ideas,_PLUCK,_CARTOON_-_VAROOP) by Hanna-Barbera.
A very popular sound recorded back in the 50s by Greg Watson.


Game state
----------

At this point, we will already be able to diversify the static splash screen with sound.


Localization
------------

It doesn't make any sense to translate filenames.


Hacking possibilities
---------------------

With sounds, the possibilities abound. Consider some options, from simple to complex.

1. Replace the sound effect with any other, for example, change the sound of the weapons.
2. Replace all sound effects with quality analogues 44100-48000 Hz Stereo.
3. Rename sound files and synchronously rename them in DTA file.
4. Add new tracks and sound some events even richer.

Option 2 is the surest way to improve the sound quality of the game,
and the rest of the options provide a set of options for ROM hackers.
