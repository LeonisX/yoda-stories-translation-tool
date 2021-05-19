package md.leonis.ystt.model.yodesk.tiles;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.TileNames;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;

public class TileName extends KaitaiStruct {

    private int tileId;
    private String name;

    private final Yodesk root;
    private final TileNames parent;

    public static TileName fromFile(String fileName) throws IOException {
        return new TileName(new ByteBufferKaitaiInputStream(fileName));
    }

    public TileName(KaitaiInputStream io) {
        this(io, null, null);
    }

    public TileName(KaitaiInputStream io, TileNames parent) {
        this(io, parent, null);
    }

    public TileName(KaitaiInputStream io, TileNames parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        tileId = io.readU2le();
        if (tileId != 65535) {
            name = io.readNullTerminatedString(24);
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeU2le(tileId);
        if (tileId != 65535) {
            os.writeNullTerminatedString(name);

            for (int i = 0; i < 24 - name.length() - 1; i++) {
                os.writeS1((byte) 0);
            }
        }
    }

    public void replaceTile(int tileId, int newTileId) {
        if (this.tileId == tileId) {
            this.tileId = newTileId;
        }
    }

    public int getTileId() {
        return tileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Yodesk getRoot() {
        return root;
    }

    public TileNames getParent() {
        return parent;
    }
}
