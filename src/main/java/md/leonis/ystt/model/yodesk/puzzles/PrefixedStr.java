package md.leonis.ystt.model.yodesk.puzzles;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;

public class PrefixedStr extends KaitaiStruct {

    private int lenContent;
    private String content;

    private final Yodesk root;
    private final Puzzle parent;

    public static PrefixedStr fromFile(String fileName) throws IOException {
        return new PrefixedStr(new ByteBufferKaitaiInputStream(fileName));
    }

    public PrefixedStr(KaitaiInputStream io) {
        this(io, null, null);
    }

    public PrefixedStr(KaitaiInputStream io, Puzzle parent) {
        this(io, parent, null);
    }

    public PrefixedStr(KaitaiInputStream io, Puzzle parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        lenContent = io.readU2le();
        content = io.readString(lenContent);
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeU2le(lenContent);
        os.writeString(content);
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

    public void setContent(String content) {

        int diff = content.length() - lenContent;
        parent.getParent().getParent().setSize(parent.getParent().getParent().getSize() + diff);

        this.content = content;
        this.lenContent = content.length();
    }

    public Yodesk getRoot() {
        return root;
    }

    public Puzzle getParent() {
        return parent;
    }
}
