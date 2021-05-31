package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class Action extends KaitaiStruct {

    private boolean enabled;

    private final Yodasav root;
    private final KaitaiStruct parent;

    public static Action fromFile(String fileName) throws IOException {
        return new Action(new ByteBufferKaitaiInputStream(fileName));
    }

    public Action(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Action(KaitaiInputStream io, KaitaiStruct parent) {
        this(io, parent, null);
    }

    public Action(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        enabled = !io.readBool4le();
    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }
}
