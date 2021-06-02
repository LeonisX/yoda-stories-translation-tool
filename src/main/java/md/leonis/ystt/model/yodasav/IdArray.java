package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.util.ArrayList;
import java.util.List;

public class IdArray extends KaitaiStruct {

    private int count;
    private List<Integer> content;

    private final transient Yodasav root;
    private final transient Yodasav parent;

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
        os.writeU2le(count);
        content.forEach(os::writeU2le);
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
