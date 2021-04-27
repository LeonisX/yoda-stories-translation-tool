package md.leonis.ystt.model;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.sounds.PrefixedStrz;

import java.io.IOException;
import java.util.ArrayList;

/**
 * This section declares sounds used in the game. The actual audio data is
 * stored in wav files on the disk (in a directory named `sfx`) so this
 * section contains paths to each sound file.
 * Sounds can be referenced from the scripting language (see `play_sound`
 * instruction opcode below) and from weapon (see `character` structure).
 * Some sound ids (like the one when the hero is hit, or can't leave a
 * zone) are hard coded in the game.
 */
public class Sounds extends KaitaiStruct {

    private short count;
    private ArrayList<PrefixedStrz> sounds;

    private final Yodesk _root;
    private final CatalogEntry _parent;

    public static Sounds fromFile(String fileName) throws IOException {
        return new Sounds(new ByteBufferKaitaiStream(fileName));
    }

    public Sounds(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Sounds(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public Sounds(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.count = this._io.readS2le();
        sounds = new ArrayList<>(((Number) (-count())).intValue());
        for (int i = 0; i < -(count()); i++) {
            this.sounds.add(new PrefixedStrz(this._io, this, _root));
        }
    }

    public short count() {
        return count;
    }

    public ArrayList<PrefixedStrz> sounds() {
        return sounds;
    }

    public Yodesk _root() {
        return _root;
    }

    public CatalogEntry _parent() {
        return _parent;
    }
}
