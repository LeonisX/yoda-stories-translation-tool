package md.leonis.ystt.model.yodesk.sounds;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Sounds;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.nio.charset.Charset;

public class PrefixedStrz extends KaitaiStruct {

    private int lenContent;
    private String content;

    private final Yodesk root;
    private final Sounds parent;

    public static PrefixedStrz fromFile(String fileName) throws IOException {
        return new PrefixedStrz(new ByteBufferKaitaiStream(fileName));
    }

    public PrefixedStrz(KaitaiStream io) {
        this(io, null, null);
    }

    public PrefixedStrz(KaitaiStream io, Sounds parent) {
        this(io, parent, null);
    }

    public PrefixedStrz(KaitaiStream io, Sounds parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.lenContent = this.io.readU2le();
        this.content = new String(KaitaiStream.bytesTerminate(this.io.readBytes(lenContent), (byte) 0, false), Charset.forName(Yodesk.getCharset()));
    }

    public int getLenContent() {
        return lenContent;
    }

    public String getContent() {
        return content;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Sounds getParent() {
        return parent;
    }
}
