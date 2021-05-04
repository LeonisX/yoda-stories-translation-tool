package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class IdArray extends KaitaiStruct {

    private int count;
    private List<Integer> content;

    private final Yodasav root;
    private final Yodasav parent;

    public static IdArray fromFile(String fileName) throws IOException {
        return new IdArray(new ByteBufferKaitaiInputStream(fileName));
    }

    public IdArray(KaitaiInputStream io) {
        this(io, null, null);
    }

    public IdArray(KaitaiInputStream io, Yodasav parent) {
        this(io, parent, null);
    }

    public IdArray(KaitaiInputStream io, Yodasav parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        count = io.readU2le();
        content = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            content.add(io.readU2le());
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public int getCount() {
        return count;
    }

    public List<Integer> getContent() {
        return content;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public Yodasav getParent() {
        return parent;
    }
}
