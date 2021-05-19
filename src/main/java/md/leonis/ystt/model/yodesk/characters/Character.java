package md.leonis.ystt.model.yodesk.characters;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Characters;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Character extends KaitaiStruct {

    private int index;
    private byte[] marker;
    private Long size;
    private String name;
    private byte[] rawName; // TODO - if rename - overwrite bytes with new name
    private CharacterType type;
    private MovementType movementType;
    private Integer probablyGarbage1;
    private Long probablyGarbage2;
    private CharFrame frame1;
    private CharFrame frame2;
    private CharFrame frame3;

    private final Yodesk root;
    private final Characters parent;

    public static Character fromFile(String fileName) throws IOException {
        return new Character(new ByteBufferKaitaiInputStream(fileName));
    }

    public Character(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Character(KaitaiInputStream io, Characters parent) {
        this(io, parent, null);
    }

    public Character(KaitaiInputStream io, Characters parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        index = io.readU2le();

        if (index != 65535) {
            marker = io.readBytes(4);

            if (!Arrays.equals(marker, new byte[]{73, 67, 72, 65})) { // ICHA
                throw new KaitaiInputStream.ValidationNotEqualError(new byte[]{73, 67, 72, 65}, marker, getIo(), "/types/character/seq/1");
            }

            size = io.readU4le();
            rawName = io.readBytes(16);
            name = io.readNullTerminatedString(16, rawName);
            type = CharacterType.byId(io.readU2le());
            movementType = MovementType.byId(io.readU2le());
            probablyGarbage1 = io.readU2le();
            probablyGarbage2 = io.readU4le();
            frame1 = new CharFrame(io, this, root);
            frame2 = new CharFrame(io, this, root);
            frame3 = new CharFrame(io, this, root);
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {

        os.writeU2le(index);

        if (index != 65535) {
            os.writeBytesFull(marker);
            os.writeU4le(size);
            os.writeBytesFull(rawName);
            os.writeU2le(type.getId());
            os.writeU2le(movementType.getId());
            os.writeU2le(probablyGarbage1);
            os.writeU4le(probablyGarbage2);

            frame1.write(os);
            frame2.write(os);
            frame3.write(os);
        }
    }

    public int byteSize() {

        return (null == marker) ? 2 :               // if marker == null, then we have last character with FFFF index only
                2 +                                 // index
                        marker.length +             // marker
                        4 +                         // size
                        16 +                        // name
                        2 +                         // type
                        2 +                         // movementType
                        2 +                         // probablyGarbage1
                        4 +                         // probablyGarbage2
                        frame1.byteSize() +         // frame1
                        frame2.byteSize() +         // frame2
                        frame3.byteSize();          // frame3
    }

    public List<Integer> getTileIds() {

        List<Integer> tileIds = new ArrayList<>();

        if (null != marker) {
            tileIds.addAll(frame1.getTiles());
            tileIds.addAll(frame2.getTiles());
            tileIds.addAll(frame3.getTiles());
        }

        return tileIds.stream().filter(id -> id != 0xFFFF).distinct().sorted().collect(Collectors.toList());
    }

    @SuppressWarnings("all")
    public void replaceTile(int tileId, int newTileId) {

        if (frame1 != null) {
            Collections.replaceAll(frame1.getTiles(), tileId, newTileId);
        }
        if (frame2 != null) {
            Collections.replaceAll(frame2.getTiles(), tileId, newTileId);
        }
        if (frame3 != null) {
            Collections.replaceAll(frame3.getTiles(), tileId, newTileId);
        }
    }

    public int getIndex() {
        return index;
    }

    public byte[] getMarker() {
        return marker;
    }

    public Long getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    public CharacterType getType() {
        return type;
    }

    public MovementType getMovementType() {
        return movementType;
    }

    public Integer getProbablyGarbage1() {
        return probablyGarbage1;
    }

    public Long getProbablyGarbage2() {
        return probablyGarbage2;
    }

    public CharFrame getFrame1() {
        return frame1;
    }

    public CharFrame getFrame2() {
        return frame2;
    }

    public CharFrame getFrame3() {
        return frame3;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Characters getParent() {
        return parent;
    }
}
