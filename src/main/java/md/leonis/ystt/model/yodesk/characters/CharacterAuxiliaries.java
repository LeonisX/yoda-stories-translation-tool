package md.leonis.ystt.model.yodesk.characters;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.CatalogEntry;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;

public class CharacterAuxiliaries extends KaitaiStruct {

    private ArrayList<CharacterAuxiliary> auxiliaries;

    private final Yodesk root;
    private final CatalogEntry parent;

    public static CharacterAuxiliaries fromFile(String fileName) throws IOException {
        return new CharacterAuxiliaries(new ByteBufferKaitaiInputStream(fileName));
    }

    public CharacterAuxiliaries(KaitaiInputStream io) {
        this(io, null, null);
    }

    public CharacterAuxiliaries(KaitaiInputStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public CharacterAuxiliaries(KaitaiInputStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.auxiliaries = new ArrayList<>();

        CharacterAuxiliary _it;
        do {
            _it = new CharacterAuxiliary(this.io, this, root);
            this.auxiliaries.add(_it);
        } while (_it.getIndex() != 65535);
    }

    @Override
    public void write(KaitaiOutputStream os) {
        auxiliaries.forEach(a -> a.write(os));
    }

    public ArrayList<CharacterAuxiliary> getAuxiliaries() {
        return auxiliaries;
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
