package md.leonis.ystt.model.yodesk;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.characters.Character;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Characters extends KaitaiStruct {

    private List<Character> characters;

    private final Yodesk root;
    private final CatalogEntry parent;

    public static Characters fromFile(String fileName) throws IOException {
        return new Characters(new ByteBufferKaitaiStream(fileName));
    }

    public Characters(KaitaiStream io) {
        this(io, null, null);
    }

    public Characters(KaitaiStream io, CatalogEntry parent) {
        this(io, parent, null);
    }

    public Characters(KaitaiStream io, CatalogEntry parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {

        this.characters = new ArrayList<>();

        Character _it;
        do {
            _it = new Character(this.io, this, root);
            this.characters.add(_it);
        } while (_it.getIndex() != 65535);
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public List<Character> getFilteredCharacters() {
        return characters.subList(0, characters.size() - 1);
    }

    public Yodesk getRoot() {
        return root;
    }

    public CatalogEntry getParent() {
        return parent;
    }
}
