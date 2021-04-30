package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Instruction extends KaitaiStruct {

    private InstructionOpcode opcode;
    private ArrayList<Short> arguments;
    private int lenText;
    private String text;

    private final Yodesk root;
    private final Action parent;

    public static Instruction fromFile(String fileName) throws IOException {
        return new Instruction(new ByteBufferKaitaiStream(fileName));
    }

    public Instruction(KaitaiStream io) {
        this(io, null, null);
    }

    public Instruction(KaitaiStream io, Action parent) {
        this(io, parent, null);
    }

    public Instruction(KaitaiStream io, Action parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.opcode = InstructionOpcode.byId(this.io.readU2le());
        arguments = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            this.arguments.add(this.io.readS2le());
        }
        this.lenText = this.io.readU2le();
        this.text = new String(this.io.readBytes(lenText), Charset.forName(Yodesk.getCharset()));
    }

    public InstructionOpcode getOpcode() {
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

    public Yodesk getRoot() {
        return root;
    }

    public Action getParent() {
        return parent;
    }
}
