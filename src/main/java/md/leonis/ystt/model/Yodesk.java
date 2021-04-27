package md.leonis.ystt.model;
// This is a generated file! Please edit source .ksy file and use kaitai-struct-compiler to rebuild

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import io.kaitai.struct.KaitaiStream;
import md.leonis.ystt.model.puzzles.Puzzle;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * [Star Wars: Yoda Stories](https://en.wikipedia.org/wiki/Star_Wars:_Yoda_Stories) is a unique tile based game with procedurally
 * generated worlds.
 * This spec describes the file format used for all assets of the Windows
 * version of the game.
 * 
 * The file format follows the TLV (type-length-value) pattern to build a
 * central catalog containing the most important (and globally accessible)
 * assets of the game (e.g. puzzles, zones, tiles, etc.). The same pattern is
 * also found in some catalog entries to encode arrays of variable-length
 * structures.
 * 
 * With every new game, Yoda Stories generates a new world. This is done by
 * picking a random sample of puzzles from `PUZ2`. One of the chosen puzzles
 * will be the goal, which when solved wins the game.
 * Each puzzle provides an item when solved and some require one to be completed.
 * During world generation a global world map of 10x10 sectors is filled with
 * zones based on the selected puzzles.
 * 
 * To add variety and interactivity to each zone the game includes a simple
 * scripting engine. Zones can declare actions that when executed can for
 * example set, move or delete tiles, drop items or activate enemies.
 */
public class Yodesk extends KaitaiStruct {

    private ArrayList<CatalogEntry> catalog;
    private final Yodesk _root;
    private final KaitaiStruct _parent;

    public static Yodesk fromFile(String fileName) throws IOException {
        return new Yodesk(new ByteBufferKaitaiStream(fileName));
    }

    public Yodesk(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Yodesk(KaitaiStream _io, KaitaiStruct _parent) {
        this(_io, _parent, null);
    }

    public Yodesk(KaitaiStream _io, KaitaiStruct _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root == null ? this : _root;
        _read();
    }

    private void _read() {
        this.catalog = new ArrayList<>();
        {
            CatalogEntry _it;
            int i = 0;
            do {
                _it = new CatalogEntry(this._io, this, _root);
                this.catalog.add(_it);
                i++;
            } while (!(_it.type().equals("ENDF")));
        }
    }

    public ArrayList<CatalogEntry> catalog() { return catalog; }
    public Yodesk _root() { return _root; }
    public KaitaiStruct _parent() { return _parent; }
}
