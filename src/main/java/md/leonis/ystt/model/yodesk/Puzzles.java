package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.puzzles.Puzzle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Puzzles extends KaitaiStruct {

    private List<Puzzle> puzzles;

    private final Yodesk root;
    private final CatalogEntry parent;

    public static Puzzles fromFile(String fileName) throws IOException {
        return new Puzzles(new ByteBufferKaitaiStream(fileName));
    }

    public Puzzles(KaitaiStream io) {
        this(io, null, null);
    }

    public Puzzles(KaitaiStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Puzzles(KaitaiStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {

        this.puzzles = new ArrayList<>();

        Puzzle _it;
        do {
            _it = new Puzzle(this.io, this, root);
            this.puzzles.add(_it);
        } while (_it.getIndex() != 65535);
    }

    public List<Puzzle> getPuzzles() {
        return puzzles;
    }

    public List<Puzzle> getFilteredPuzzles() {
        return puzzles.subList(0, puzzles.size() - 1);
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
