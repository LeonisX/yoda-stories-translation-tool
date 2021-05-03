package md.leonis.ystt.model.yodesk.puzzles;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Puzzles;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Puzzle extends KaitaiStruct {

    private int index;
    private byte[] marker;
    private Long size;
    private Long type;
    private Long _unnamed4;
    private Long _unnamed5;
    private Integer _unnamed6;
    private final List<PrefixedStr> prefixesStrings = new ArrayList<>(5);
    private Integer item1;
    private Integer item2;

/*

* Puzzle types:

0 Use
1 Trade
2 Goal
3 End

* Unnamed 4

0 Keycard
1 Tool
2 Part
3 Valuable

* Unnamed 5 (if item2 != 0 else None)

0
2
3
4 Valuable
FFFF

* Unnamed 6

0       0 00000000      15
128     0 10000000      43
271     1 00001111      50
279     1 00010111      1
286     1 00011110      8
287     1 00011111      1
311     1 00110111      1
319     1 00111111      86
*/

    private final Yodesk root;
    private final Puzzles parent;

    public static Puzzle fromFile(String fileName) throws IOException {
        return new Puzzle(new ByteBufferKaitaiInputStream(fileName));
    }

    public Puzzle(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Puzzle(KaitaiInputStream io, Puzzles parent) {
        this(io, parent, null);
    }

    public Puzzle(KaitaiInputStream io, Puzzles parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {

        index = io.readU2le();

        if (index != 65535) {
            marker = io.readBytes(4);

            if (!Arrays.equals(marker, new byte[]{73, 80, 85, 90})) { // IPUZ
                throw new KaitaiInputStream.ValidationNotEqualError(new byte[]{73, 80, 85, 90}, marker, getIo(), "/types/puzzle/seq/1");
            }

            size = io.readU4le();
            type = io.readU4le();
            _unnamed4 = io.readU4le();
            _unnamed5 = io.readU4le();
            _unnamed6 = io.readU2le();

            for (int i = 0; i < 5; i++) {
                PrefixedStr prefixedStr = new PrefixedStr(io, this, root);
                prefixesStrings.add(prefixedStr);
            }

            item1 = io.readU2le();
            item2 = io.readU2le();
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {

        os.writeU2le(index);

        if (index != 65535) {
            os.writeBytesFull(marker);

            os.writeU4le(size);
            os.writeU4le(type);
            os.writeU4le(_unnamed4);
            os.writeU4le(_unnamed5);
            os.writeU2le(_unnamed6);

            for (PrefixedStr prefixesString : prefixesStrings) {
                prefixesString.write(os);
            }

            os.writeU2le(item1);
            os.writeU2le(item2);
        }
    }

    public int byteSize() {

        return (null == marker) ? 2 :               // if marker == null, then we have last puzzle with FFFF index only
                2 +                                 // index
                        marker.length +             // marker
                        4 +                         // size
                        4 +                         // type
                        4 +                         // _unnamed4
                        4 +                         // _unnamed5
                        4 +                         // _unnamed6
                        prefixesStrings.stream().mapToInt(PrefixedStr::byteSize).sum() + // prefixesStrings
                        2 +                         // item1
                        2;                          // item2
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

    public Long getType() {
        return type;
    }

    public Long get_unnamed4() {
        return _unnamed4;
    }

    public Long get_unnamed5() {
        return _unnamed5;
    }

    public Integer get_unnamed6() {
        return _unnamed6;
    }

    public List<PrefixedStr> getPrefixesStrings() {
        return prefixesStrings;
    }

    public List<String> getStrings() {
        return prefixesStrings.stream().map(PrefixedStr::getContent).collect(Collectors.toList());
    }

    public Integer getItem1() {
        return item1;
    }

    public Integer getItem2() {
        return item2;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Puzzles getParent() {
        return parent;
    }
}
