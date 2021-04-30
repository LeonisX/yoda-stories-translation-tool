package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Condition extends KaitaiStruct implements TextContainer {

    private ConditionOpcode opcode;
    private ArrayList<Short> arguments;
    private int lenText;
    // The `text_attribute` is never used, but seems to be included to shared the type with instructions.
    private String text;

    private final Yodesk root;
    private final Action parent;

    public static Condition fromFile(String fileName) throws IOException {
        return new Condition(new ByteBufferKaitaiStream(fileName));
    }

    public Condition(KaitaiStream io) {
        this(io, null, null);
    }

    public Condition(KaitaiStream io, Action parent) {
        this(io, parent, null);
    }

    public Condition(KaitaiStream io, Action parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.opcode = ConditionOpcode.byId(this.io.readU2le());
        arguments = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            this.arguments.add(this.io.readS2le());
        }
        this.lenText = this.io.readU2le();
        this.text = new String(this.io.readBytes(lenText), Charset.forName(Yodesk.getCharset()));
    }

    public ConditionOpcode getOpcode() {
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
