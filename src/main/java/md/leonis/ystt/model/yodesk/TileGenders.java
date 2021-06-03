package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.tiles.TileGender;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The TGEN section is only present in non-english versions of the game.
 * These are genders of tile names, they are used to form Action dialogs.
 */
public class TileGenders extends KaitaiStruct {

    private List<TileGender> genders;

    private final transient Yodesk root;
    private final transient CatalogEntry parent;

    public static TileGenders fromFile(String fileName) throws IOException {
        return new TileGenders(new ByteBufferKaitaiInputStream(fileName));
    }

    public TileGenders(KaitaiInputStream io) {
        this(io, null, null);
    }

    public TileGenders(KaitaiInputStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public TileGenders(KaitaiInputStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {

        genders = new ArrayList<>();
        for (int i = 0; i < parent.getParent().getTiles().getTiles().size(); i++) {
            genders.add(TileGender.byId(io.readU2le()));
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {

        for (int i = 0; i < parent.getParent().getTiles().getTiles().size(); i++) {
            os.writeU2le(genders.get(i).getId());
        }
    }

    public void addTileGender() {
        genders.add(TileGender.MALE);
        parent.setSize(genders.size() * 2L);
    }

    public void deleteTileGender() {
        genders.remove(genders.size() - 1);
        parent.setSize(genders.size() * 2L);
    }

    public List<TileGender> getGenders() {
        return genders;
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
