package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;

public class Instruction extends KaitaiStruct implements TextContainer {

    private InstructionOpcode opcode;
    private ArrayList<Short> arguments;
    private int lenText;
    private String text;

    private final Yodesk root;
    private final Action parent;

    public static Instruction fromFile(String fileName) throws IOException {
        return new Instruction(new ByteBufferKaitaiInputStream(fileName));
    }

    public Instruction(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Instruction(KaitaiInputStream io, Action parent) {
        this(io, parent, null);
    }

    public Instruction(KaitaiInputStream io, Action parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        opcode = InstructionOpcode.byId(io.readU2le());
        arguments = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            arguments.add(io.readS2le());
        }
        lenText = io.readU2le();
        text = io.readString(lenText);
    }

    @Override
    public void write(KaitaiOutputStream os) {
        os.writeU2le(opcode.getId());

        for (Short argument : arguments) {
            os.writeS2le(argument);
        }
        os.writeU2le(lenText);
        os.writeString(text);
    }

    public InstructionOpcode getOpcode() {
        return opcode;
    }

    public ArrayList<Short> getArguments() {
        return arguments;
    }

    @Override
    public int getLenText() {
        return lenText;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        int diff = text.length() - lenText;
        parent.getParent().setSize(parent.getParent().getSize() + diff);

        this.text = text;
        this.lenText = text.length();
    }

    public Yodesk getRoot() {
        return root;
    }

    public Action getParent() {
        return parent;
    }
}
