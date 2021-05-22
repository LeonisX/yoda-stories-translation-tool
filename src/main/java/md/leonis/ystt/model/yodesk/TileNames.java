package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.tiles.TileName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TileNames extends KaitaiStruct {

    /**
     * List of tile ids and their corresponding names. These are shown in the
     * inventory or used in dialogs (see `speak_hero` and `speak_npc` opcodes).
     */
    private List<TileName> names;

    private final transient Yodesk root;
    private final transient CatalogEntry parent;

    public static TileNames fromFile(String fileName) throws IOException {
        return new TileNames(new ByteBufferKaitaiInputStream(fileName));
    }

    public TileNames(KaitaiInputStream io) {
        this(io, null, null);
    }

    public TileNames(KaitaiInputStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public TileNames(KaitaiInputStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        names = new ArrayList<>();

        TileName _it;
        do {
            _it = new TileName(this.io, this, root);
            names.add(_it);

        } while (_it.getTileId() != 65535);
    }

    @Override
    public void write(KaitaiOutputStream os) {
        names.forEach(n -> n.write(os));
    }

    public List<TileName> getNames() {
        return names;
    }

    public List<TileName> getFilteredNames() {
        return names.subList(0, names.size() - 1);
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
