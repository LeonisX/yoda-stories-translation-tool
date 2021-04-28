package md.leonis.ystt.model;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.characters.Character;

import java.io.IOException;
import java.util.ArrayList;

public class Characters extends KaitaiStruct {

    private ArrayList<Character> characters;

    private final Yodesk _root;
    private final CatalogEntry _parent;

    public static Characters fromFile(String fileName) throws IOException {
        return new Characters(new ByteBufferKaitaiStream(fileName));
    }

    public Characters(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Characters(KaitaiStream _io, CatalogEntry _parent) {
        this(_io, _parent, null);
    }

    public Characters(KaitaiStream _io, CatalogEntry _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {

        this.characters = new ArrayList<>();

        Character _it;
        do {
            _it = new Character(this._io, this, _root);
            this.characters.add(_it);
        } while (_it.getIndex() != 65535);
    }

    public ArrayList<Character> getCharacters() {
        return characters;
    }

    public Yodesk get_root() {
        return _root;
    }

    public CatalogEntry get_parent() {
        return _parent;
    }
}