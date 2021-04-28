package md.leonis.ystt.model.sounds;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Sounds;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PrefixedStrz extends KaitaiStruct {

    private int lenContent;
    private String content;

    private final Yodesk _root;
    private final Sounds _parent;

    public static PrefixedStrz fromFile(String fileName) throws IOException {
        return new PrefixedStrz(new ByteBufferKaitaiStream(fileName));
    }

    public PrefixedStrz(KaitaiStream _io) {
        this(_io, null, null);
    }

    public PrefixedStrz(KaitaiStream _io, Sounds _parent) {
        this(_io, _parent, null);
    }

    public PrefixedStrz(KaitaiStream _io, Sounds _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.lenContent = this._io.readU2le();
        this.content = new String(KaitaiStream.bytesTerminate(this._io.readBytes(lenContent), (byte) 0, false), Charset.forName(Yodesk.getCharset()));
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

    public Sounds get_parent() {
        return _parent;
    }
}
