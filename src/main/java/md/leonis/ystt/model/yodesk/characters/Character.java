package md.leonis.ystt.model.yodesk.characters;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Characters;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Character extends KaitaiStruct {

    private int index;
    private byte[] marker;
    private Long size;
    private String name;
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
        return new Character(new ByteBufferKaitaiStream(fileName));
    }

    public Character(KaitaiStream io) {
        this(io, null, null);
    }

    public Character(KaitaiStream io, Characters parent) {
        this(io, parent, null);
    }

    public Character(KaitaiStream io, Characters parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.index = this.io.readU2le();

        if (index != 65535) {
            this.marker = this.io.readBytes(4);

            if (!(Arrays.equals(marker, new byte[]{73, 67, 72, 65}))) { // ICHA
                throw new KaitaiStream.ValidationNotEqualError(new byte[]{73, 67, 72, 65}, marker, getIo(), "/types/character/seq/1");
            }

            this.size = this.io.readU4le();
            this.name = new String(KaitaiStream.bytesTerminate(this.io.readBytes(16), (byte) 0, false), Charset.forName(Yodesk.getCharset()));
            this.type = CharacterType.byId(this.io.readU2le());
            this.movementType = MovementType.byId(this.io.readU2le());
            this.probablyGarbage1 = this.io.readU2le();
            this.probablyGarbage2 = this.io.readU4le();
            this.frame1 = new CharFrame(this.io, this, root);
            this.frame2 = new CharFrame(this.io, this, root);
            this.frame3 = new CharFrame(this.io, this, root);
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
