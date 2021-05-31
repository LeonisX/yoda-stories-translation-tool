package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;

public class Sector extends KaitaiStruct {

    private boolean visited;
    private boolean solved1;
    private boolean solved2;
    private boolean solved3;
    private boolean solved4;

    private short zoneId;
    private short puzzleIndex;
    private short requiredItemId;
    private short findItemId;
    private short isGoal;
    private short additionalRequiredItem;
    private short additionalGainItem;
    private short npcId;

    private int unknown;
    private boolean usedAlternateStrain;

    private final Yodasav root;
    private final KaitaiStruct parent;

    public static Sector fromFile(String fileName) throws IOException {
        return new Sector(new ByteBufferKaitaiInputStream(fileName));
    }

    public Sector(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Sector(KaitaiInputStream io, KaitaiStruct parent) {
        this(io, parent, null);
    }

    public Sector(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {
        visited = io.readBool4le();
        solved1 = io.readBool4le();
        solved2 = io.readBool4le();
        solved3 = io.readBool4le();
        solved4 = io.readBool4le();
        zoneId = io.readS2le();
        puzzleIndex = io.readS2le();
        requiredItemId = io.readS2le();
        findItemId = io.readS2le();
        isGoal = io.readS2le();
        additionalRequiredItem = io.readS2le();
        additionalGainItem = io.readS2le();
        npcId = io.readS2le();
        unknown = io.readS4le();
        short alternateStrain = io.readS2le();
        usedAlternateStrain = alternateStrain == 1; // alternateStrain == -1 ? null : alternateStrain == 1;
    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isSolved1() {
        return solved1;
    }

    public boolean isSolved2() {
        return solved2;
    }

    public boolean isSolved3() {
        return solved3;
    }

    public boolean isSolved4() {
        return solved4;
    }

    public int getZoneId() {
        return zoneId;
    }

    public int getPuzzleIndex() {
        return puzzleIndex;
    }

    public int getRequiredItemId() {
        return requiredItemId;
    }

    public int getFindItemId() {
        return findItemId;
    }

    public int getIsGoal() {
        return isGoal;
    }

    public int getAdditionalRequiredItem() {
        return additionalRequiredItem;
    }

    public int getAdditionalGainItem() {
        return additionalGainItem;
    }

    public int getNpcId() {
        return npcId;
    }

    public long getUnknown() {
        return unknown;
    }

    public boolean isUsedAlternateStrain() {
        return usedAlternateStrain;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }
}
