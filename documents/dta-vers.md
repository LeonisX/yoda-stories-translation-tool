VERS
====

Version of the file. This value always `512` (0x00020000). Or `0.2.0.0`.

We think that the name of the section `VERS` is more correct to decipher as `Verification Signature`.

The fact is that in the first game of the series, `Indiana Jones and his Desktop Adventures`,
this value is also `512`.

Perhaps LucasArts had other games with a similar engine, if you know of such, then write to us.

Hacking possibilities
---------------------

These 4 bytes strictly checked when loading the game, and any other value will cause the game to crash.
