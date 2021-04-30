package md.leonis.ystt.model.yodesk.puzzles;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.nio.charset.Charset;

public class PrefixedStr extends KaitaiStruct {

    private int lenContent;
    private String content;

    private final Yodesk root;
    private final Puzzle parent;

    public static PrefixedStr fromFile(String fileName) throws IOException {
        return new PrefixedStr(new ByteBufferKaitaiStream(fileName));
    }

    public PrefixedStr(KaitaiStream io) {
        this(io, null, null);
    }

    public PrefixedStr(KaitaiStream io, Puzzle parent) {
        this(io, parent, null);
    }

    public PrefixedStr(KaitaiStream io, Puzzle parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.lenContent = this.io.readU2le();
        this.content = new String(this.io.readBytes(lenContent), Charset.forName(Yodesk.getCharset()));
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

    public Yodesk getRoot() {
        return root;
    }

    public Puzzle getParent() {
        return parent;
    }
}
