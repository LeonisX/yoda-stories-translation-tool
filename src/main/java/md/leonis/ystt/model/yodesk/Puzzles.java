package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.puzzles.Puzzle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Puzzles extends KaitaiStruct {

    private List<Puzzle> puzzles;

    private final transient Yodesk root;
    private final transient CatalogEntry parent;

    public static Puzzles fromFile(String fileName) throws IOException {
        return new Puzzles(new ByteBufferKaitaiInputStream(fileName));
    }

    public Puzzles(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Puzzles(KaitaiInputStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Puzzles(KaitaiInputStream io, CatalogEntry parent, Yodesk root) {
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

    @Override
    public void write(KaitaiOutputStream os) {
        puzzles.forEach(p -> p.write(os));
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
