package md.leonis.ystt.model.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.Yodesk;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Instruction extends KaitaiStruct {

    private InstructionOpcode opcode;
    private ArrayList<Short> arguments;
    private int lenText;
    private String text;

    private final Yodesk _root;
    private final Action _parent;

    public static Instruction fromFile(String fileName) throws IOException {
        return new Instruction(new ByteBufferKaitaiStream(fileName));
    }

    public Instruction(KaitaiStream _io) {
        this(_io, null, null);
    }

    public Instruction(KaitaiStream _io, Action _parent) {
        this(_io, _parent, null);
    }

    public Instruction(KaitaiStream _io, Action _parent, Yodesk _root) {
        super(_io);
        this._parent = _parent;
        this._root = _root;
        _read();
    }

    private void _read() {
        this.opcode = InstructionOpcode.byId(this._io.readU2le());
        arguments = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            this.arguments.add(this._io.readS2le());
        }
        this.lenText = this._io.readU2le();
        this.text = new String(this._io.readBytes(lenText()), StandardCharsets.US_ASCII);
    }

    public InstructionOpcode opcode() {
        return opcode;
    }

    public ArrayList<Short> arguments() {
        return arguments;
    }

    public int lenText() {
        return lenText;
    }

    public String text() {
        return text;
    }

    public Yodesk _root() {
        return _root;
    }

    public Action _parent() {
        return _parent;
    }
}
