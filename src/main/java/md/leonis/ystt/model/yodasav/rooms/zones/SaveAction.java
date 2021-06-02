package md.leonis.ystt.model.yodasav.rooms.zones;

import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodasav.Yodasav;

public class SaveAction extends KaitaiStruct {

    private boolean enabled;

    private final transient Yodasav root;
    private final transient KaitaiStruct parent;

    public SaveAction(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
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

    public boolean isEnabled() {
        return enabled;
    }
}
