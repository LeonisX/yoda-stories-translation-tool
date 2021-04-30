package md.leonis.ystt.model.yodesk.zones;

import io.kaitai.struct.ByteBufferKaitaiStream;
import io.kaitai.struct.KaitaiStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Actions are the game's way to make static tile based maps more engaging
 * and interactive. Each action consists of zero or more conditions and
 * instructions, once all conditions are satisfied, all instructions are
 * executed in order.
 *
 * To facilitate state, each zone has three 16-bit registers. These registers
 * are named `counter`, `shared-counter` and `random`. In addition to these
 * registers hidden tiles are sometimes used to mark state.
 * There are conditions and instructions to query and update these registers.
 * The `shared-counter` register is special in that it is shared between a
 * zone and it's rooms. Instruction `0xc` roll_dice can be used to set the
 * `random` register to a random value.
 *
 * A naive implementation of the scripting engine could look like this:
 *
 * ```
 * for action in zone.actions:
 *   all_conditions_satisfied = False
 *   for condition in action.conditions:
 *       all_conditions_satisfied = check(condition)
 *       if !all_conditions_satisfied:
 *         break
 *
 *   if !all_conditions_satisfied:
 *     continue
 *
 *   for instruction in action.instructions:
 *     execute(instruction)
 * ```
 *
 * See `condition_opcode` and `instruction_opcode` enums for a list of
 * available opcodes and their meanings.
 */
public class Action extends KaitaiStruct {

    private byte[] marker;
    private long size;
    private int numConditions;
    private ArrayList<Condition> conditions;
    private int numInstructions;
    private ArrayList<Instruction> instructions;

    private final Yodesk root;
    private final Zone parent;

    public static Action fromFile(String fileName) throws IOException {
        return new Action(new ByteBufferKaitaiStream(fileName));
    }

    public Action(KaitaiStream io) {
        this(io, null, null);
    }

    public Action(KaitaiStream io, Zone parent) {
        this(io, parent, null);
    }

    public Action(KaitaiStream io, Zone parent, Yodesk root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        this.marker = this.io.readBytes(4);
        if (!(Arrays.equals(marker, new byte[]{73, 65, 67, 84}))) { // IACT
            throw new KaitaiStream.ValidationNotEqualError(new byte[]{73, 65, 67, 84}, marker, getIo(), "/types/action/seq/0");
        }
        this.size = this.io.readU4le();
        this.numConditions = this.io.readU2le();
        conditions = new ArrayList<>(numConditions);
        for (int i = 0; i < numConditions; i++) {
            this.conditions.add(new Condition(this.io, this, root));
        }
        this.numInstructions = this.io.readU2le();
        instructions = new ArrayList<>(numInstructions);
        for (int i = 0; i < numInstructions; i++) {
            this.instructions.add(new Instruction(this.io, this, root));
        }
    }

    public byte[] getMarker() {
        return marker;
    }

    public long getSize() {
        return size;
    }

    public int getNumConditions() {
        return numConditions;
    }

    public ArrayList<Condition> getConditions() {
        return conditions;
    }

    public int getNumInstructions() {
        return numInstructions;
    }

    public ArrayList<Instruction> getInstructions() {
        return instructions;
    }

    public Yodesk getRoot() {
        return root;
    }

    public Zone getParent() {
        return parent;
    }
}
