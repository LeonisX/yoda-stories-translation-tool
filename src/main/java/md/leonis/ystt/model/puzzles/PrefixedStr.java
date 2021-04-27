package md.leonis.ystt.model.puzzles;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class PrefixedStr extends KaitaiStruct {

    private int lenContent;
    private String content;

    private final Yodesk _root;
    private final Puzzle _parent;

    public static PrefixedStr fromFile(String fileName) throws IOException {
        return new PrefixedStr(new ByteBufferKaitaiStream(fileName));
    }

    public PrefixedStr(KaitaiStream _io) {
        this(_io, null, null);
    }

    public PrefixedStr(KaitaiStream _io, Puzzle _parent) {
        this(_io, _parent, null);
    }

    public PrefixedStr(KaitaiStream _io, Puzzle _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.lenContent = this._io.readU2le();
        this.content = new String(this._io.readBytes(lenContent()), StandardCharsets.US_ASCII);
    }

    public int lenContent() {
        return lenContent;
    }

    public String content() {
        return content;
    }

    public Yodesk _root() {
        return _root;
    }

    public Puzzle _parent() {
        return _parent;
    }
}
