package md.leonis.ystt.model.yodesk.tiles;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.TileNames;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.nio.charset.Charset;

public class TileName extends KaitaiStruct {

    private int tileId;
    private String name;

    private final Yodesk root;
    private final TileNames parent;

    public static TileName fromFile(String fileName) throws IOException {
        return new TileName(new ByteBufferKaitaiStream(fileName));
    }

    public TileName(KaitaiStream io) {
        this(io, null, null);
    }

    public TileName(KaitaiStream io, TileNames parent) {
        this(io, parent, null);
    }

    public TileName(KaitaiStream io, TileNames parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.tileId = this.io.readU2le();
        if (tileId != 65535) {
            this.name = new String(KaitaiStream.bytesTerminate(this.io.readBytes(24), (byte) 0, false), Charset.forName(Yodesk.getCharset()));
        }
    }

    public int getTileId() {
        return tileId;
    }

    public String getName() {
        return name;
    }

    public Yodesk getRoot() {
        return root;
    }

    public TileNames getParent() {
        return parent;
    }
}
