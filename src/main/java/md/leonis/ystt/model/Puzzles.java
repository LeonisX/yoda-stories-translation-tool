package md.leonis.ystt.model;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.puzzles.Puzzle;

import java.io.IOException;
import java.util.ArrayList;

public class Puzzles extends KaitaiStruct {

    private ArrayList<Puzzle> puzzles;

    private final Yodesk _root;
    private final CatalogEntry _parent;

    public static Puzzles fromFile(String fileName) throws IOException {
        return new Puzzles(new ByteBufferKaitaiStream(fileName));
    }

    public Puzzles(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Puzzles(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public Puzzles(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {

        this.puzzles = new ArrayList<>();

        Puzzle _it;
        do {
            _it = new Puzzle(this._io, this, _root);
            this.puzzles.add(_it);
        } while (_it.getIndex() != 65535);
    }

    public ArrayList<Puzzle> getPuzzles() {
        return puzzles;
    }

    public Yodesk get_root() {
        return _root;
    }

    public CatalogEntry get_parent() {
        return _parent;
    }
}
