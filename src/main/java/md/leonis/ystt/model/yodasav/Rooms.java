package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Rooms extends KaitaiStruct {

    private List<Room> rooms;

    private final Yodasav root;
    private final KaitaiStruct parent;

    public static Rooms fromFile(String fileName) throws IOException {
        return new Rooms(new ByteBufferKaitaiInputStream(fileName));
    }

    public Rooms(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Rooms(KaitaiInputStream io, KaitaiStruct parent) {
        this(io, parent, null);
    }

    public Rooms(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        rooms = new ArrayList<>();

        Room _it;
        do {
            _it = new Room(io, this, root);
            rooms.add(_it);
        } while (_it.getUnknown1() != -1);

    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }
}
