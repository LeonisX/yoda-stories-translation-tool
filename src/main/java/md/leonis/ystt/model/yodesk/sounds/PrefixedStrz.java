package md.leonis.ystt.model.yodesk.sounds;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Sounds;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;

public class PrefixedStrz extends KaitaiStruct {

    private int lenContent;
    private String content;

    private final transient Yodesk root;
    private final transient Sounds parent;

    public static PrefixedStrz fromFile(String fileName) throws IOException {
        return new PrefixedStrz(new ByteBufferKaitaiInputStream(fileName));
    }

    public PrefixedStrz(KaitaiInputStream io) {
        this(io, null, null);
    }

    public PrefixedStrz(KaitaiInputStream io, Sounds parent) {
        this(io, parent, null);
    }

    public PrefixedStrz(KaitaiInputStream io, Sounds parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        lenContent = io.readU2le();
        content = io.readNullTerminatedString(lenContent);
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeU2le(lenContent);
        os.writeNullTerminatedString(content);
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
