package md.leonis.ystt.model.puzzles;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.nio.charset.Charset;

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
        this.content = new String(this._io.readBytes(lenContent), Charset.forName(Yodesk.getCharset()));
    }

    public int byteSize() {
        return 2 + lenContent;
    }

    public int getLenContent() {
        return lenContent;
    }

    public String getContent() {
        return content;
    }

    public Yodesk get_root() {
        return _root;
    }

    public Puzzle get_parent() {
        return _parent;
    }
}
