package md.leonis.ystt.model.yodesk.characters;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
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
        return new CharacterAuxiliaries(new ByteBufferKaitaiStream(fileName));
    }

    public CharacterAuxiliaries(KaitaiStream io) {
        this(io, null, null);
    }

    public CharacterAuxiliaries(KaitaiStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public CharacterAuxiliaries(KaitaiStream io, CatalogEntry parent, Yodesk root) {
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