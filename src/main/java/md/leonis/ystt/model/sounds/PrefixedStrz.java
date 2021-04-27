package md.leonis.ystt.model.sounds;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Sounds;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
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
        this.content = new String(KaitaiStream.bytesTerminate(this._io.readBytes(lenContent()), (byte) 0, false), StandardCharsets.US_ASCII);
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

    public Sounds _parent() {
        return _parent;
    }
}
