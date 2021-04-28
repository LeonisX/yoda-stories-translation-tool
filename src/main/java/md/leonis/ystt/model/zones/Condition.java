package md.leonis.ystt.model.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Condition extends KaitaiStruct {

    private ConditionOpcode opcode;
    private ArrayList<Short> arguments;
    private int lenText;
    // The `text_attribute` is never used, but seems to be included to shared the type with instructions.
    private String text;

    private final Yodesk _root;
    private final Action _parent;

    public static Condition fromFile(String fileName) throws IOException {
        return new Condition(new ByteBufferKaitaiStream(fileName));
    }

    public Condition(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Condition(KaitaiStream _io, Action _parent) {
        this(_io, _parent, null);
    }

    public Condition(KaitaiStream _io, Action _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.opcode = ConditionOpcode.byId(this._io.readU2le());
        arguments = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            this.arguments.add(this._io.readS2le());
        }
        this.lenText = this._io.readU2le();
        this.text = new String(this._io.readBytes(lenText), Charset.forName("Cp1252"));
    }

    public ConditionOpcode getOpcode() {
        return opcode;
    }

    public ArrayList<Short> getArguments() {
        return arguments;
    }

    public int getLenText() {
        return lenText;
    }

    public String getText() {
        return text;
    }

    public Yodesk get_root() {
        return _root;
    }

    public Action get_parent() {
        return _parent;
    }
}
