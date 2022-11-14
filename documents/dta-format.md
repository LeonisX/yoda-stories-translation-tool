YODESK.DTA Format
=================

All information about the game world is stored in a format similar to
[RIFF](https://en.wikipedia.org/wiki/Resource_Interchange_File_Format) (Resource Interchange File Format).
The data is divided into chunks (sections), consisting of a 4-byte text header,
4 bytes describing the size of the data, and, in fact, the data itself. The `little-endian` byte order.

A similar format is used, for example, in `WAV` and `MIDI` files.

However, the `YODESK.DTA` file does not follow the RIFF standard very strictly.
For example, 4 bytes after the `VERS` chunk should indicate the size of the YODESK.DTA file
without 8 bytes, or, at worst, the size of the `VERS` section, but in fact this is not the case.

The complete structure of the YODESK.DTA file in the language [Kaitai Struct](http://kaitai.io/) was described by Cyco in the file [yodesk.ksy](https://www.webfun.io/docs/appendix/yodesk.html).
The `yoda_stories_dta.ksy` file is not yet on the official website, it is still in the [approval stage](https://github.com/kaitai-io/kaitai_struct_formats/pull/403).
We will take this document as a basis, and will make only minor adjustments where necessary.

Why is Kaitai Struct so interesting?
-----------------------------------

The fact is that over several decades of the development of computer technology,
a huge amount of incompatible electronics has been created, and the main differences are:

* Processors: bit depth, instructions, endianness
* Programming languages: specialization, instructions, data types

Programming languages tend to smooth out processor differences, but at the binary data level,
the differences can be significant, for example, due to Java's lack of support for signed numbers.
Try without too much magic to provide work with any binary format on various platforms!

The declarative language `Kaitai Struct` tries to make this job easier - you
only need to describe the binary format once, and then that's it.
_However, practice shows that Kaitai Struct itself is constantly evolving, therefore,
some KSY files may be at the approval stage for a long time
just because of changes in the structure of the declarative language._

Tools specially designed for their purpose will translate the text format into code in any of the supported languages:
C++, C#, Go, Java, JavaScript, Lua, Nim, Perl, PHP, Python, Ruby, Rust or Swift.
As a result, the programmer receives a code that, without unnecessary changes, is able to load the described
binary file into memory, and provide access to data in a beautiful, easy-to-understand API.

Why was YODESK.DTA invented?
----------------------------

The game `Star Wars: Yoda Stories` is unique in many ways.
There are not many global quests in it, however, each newly generated world will be completely different
from the previous one, and Luke Skywalker is in for a long and rich adventure.

The executable file of the game loads the resources and scripts stored in YODESK.DTA,
generates the game world based on them and provides the gameplay.
 
* 15 global quests
* 658 unique locations
* Map consisting of 10x10 sectors
* Mini-quests from locals
* A wild flora, fauna, unfriendly inhabitants and, of course, the ubiquitous mercenaries and stormtroopers
* Friendly inhabitants, rebels, as well as all the characters of the original trilogy, including R2-D2, Ben Kenobi and of course, Yoda

When generating each new world, several puzzles are selected from the `PUZ2` section.
One of these puzzles will be key to completing the mission. This quest is given by Yoda at the beginning of each mission.
Completing a puzzle usually awards a unique item that is needed to solve the next puzzle.
But some tasks just need to be completed.

Based on the selected quests, during world generation, the global map is filled with zones.
It must be understood that zones are not just static locations with predetermined actions.
A simple but quite effective scripting engine greatly diversifies the game.
In some places, you will need to use a certain object, move or drag something,
press something, shoot someone, or try to negotiate verbally using the Force.
The random value generator allows you to pass the same location in different ways.

Structure of the YODESK.DTA file
--------------------------------

List of sections/chunks:

* [`VERS`](dta-vers.md): verification signature (version)
* [`STUP`](dta-stup.md): startup image
* [`SNDS`](dta-snds.md): sounds
* [`TILE`](dta-tile.md): tiles. Occupy almost half of the DTA file
* `ZONE`: zones. Occupy almost half of the DTA file
   *`IZON`
   * `IZAX`
   *`IZX2`
   *`IZX3`
   *`IZX4`
   *`IACT`
* `PUZ2`: puzzles
   *`IPUZ`
* `CHAR`: characters
   *`ICHA`
* `CHWP`: character weapons
* `CAUX`: character auxiliaries
* `TNAM`: tile names
* `TGEN`: tile genders (non-english versions)
* `ENDF`: end section

Usually, after the name of the section, the next 4 bytes indicate its size in bytes.

The exceptions are:
 
* `VERS`: all 4 bytes of this section store the value `512`.
* `ZONE`: the first 2 bytes of this section indicate the total number of zones in the file: `658`.

**_If you took part in the development of the game, you know some intriguing details,
you can add to this documentation, or you know the answer to one of the questions asked,
then be sure to contact us, we will be very happy with any information._**

[source 1](https://www.webfun.io/docs/appendix/yodesk.html), [source 2](https://www.trashworldnews.com/yoda-stories/)