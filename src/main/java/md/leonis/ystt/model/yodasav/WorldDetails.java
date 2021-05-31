package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class WorldDetails extends KaitaiStruct {

    private int x;
    private int y;
    private short zoneId;
    private boolean visited;
    private Room room;

    private final Yodasav root;
    private final KaitaiStruct parent;

    public static WorldDetails fromFile(String fileName) throws IOException {
        return new WorldDetails(new ByteBufferKaitaiInputStream(fileName));
    }

    public WorldDetails(KaitaiInputStream io) {
        this(io, null, null);
    }

    public WorldDetails(KaitaiInputStream io, KaitaiStruct parent) {
        this(io, parent, null);
    }

    public WorldDetails(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {

        x = io.readS4le();
        y = io.readS4le();

        if (x != -1 && y != -1) {
            zoneId = io.readS2le();
			visited = io.readBool4le();

            room = new Room(io, this, root, zoneId, visited);
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public short getZoneId() {
        return zoneId;
    }

    public boolean getVisited() {
        return visited;
    }

    public Room getRoom() {
        return room;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }
}
