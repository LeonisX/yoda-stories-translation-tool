package md.leonis.ystt.model.yodasav;// This is a generated file! Please edit source .ksy file and use kaitai-struct-compiler to rebuild

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodesk.Yodesk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://github.com/cyco/WebFun/tree/master/src/engine/save-game
public class Yodasav extends KaitaiStruct {

    private byte[] magic;
    private long seed;
    private long planet;
    private long onDagobah; // 0 == false
    private IdArray puzzles1;
    private IdArray puzzles2;
    private Dagobah dagobah;
    private World world;
    private long inventoryCount;
    private List<Integer> inventory;
    private int currentZoneId;
    private long worldX;
    private long worldY;
    private short currentWeapon;
    private Short currentAmmo;
    private short forceAmmo;
    private short blasterAmmo;
    private short blasterRifleAmmo;
    private long zoneX; // x * 32
    private long zoneY; // y * 32
    private int damageTaken;
    private int livesLost; //TODO investigate
    private long difficulty;
    private long timeElapsed;
    private int worldSize;
    private short unknownCount;
    private short unknownSum;
    private long goalPuzzle;
    private long goalPuzzleAgain;

    private static Yodesk yodesk;

    private final Yodasav root;
    private final KaitaiStruct parent;

    public static Yodasav fromFile(String fileName, Yodesk yodesk) throws IOException {
        Yodasav.yodesk = yodesk;
        return new Yodasav(new ByteBufferKaitaiInputStream(fileName));
    }

    public Yodasav(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Yodasav(KaitaiInputStream io, KaitaiStruct parent) {
        this(io, parent, null);
    }

    public Yodasav(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root == null ? this : root;
        _read();
    }

    private void _read() {
        magic = io.readBytes(9);
        if (!Arrays.equals(magic, new byte[]{89, 79, 68, 65, 83, 65, 86, 52, 52})) {
            throw new KaitaiInputStream.ValidationNotEqualError(new byte[]{89, 79, 68, 65, 83, 65, 86, 52, 52}, magic, io, "/seq/0");
        }
        seed = io.readU4le(); //TODO  & 0xFFFF  need to investigate
        planet = io.readU4le();
        onDagobah = io.readU4le();
        puzzles1 = new IdArray(io, this, root);
        puzzles2 = new IdArray(io, this, root);
        dagobah = new Dagobah(io, this, root);
        world = new World(io, this, root);
        inventoryCount = io.readU4le();
        inventory = new ArrayList<>((int) inventoryCount);
        for (int i = 0; i < inventoryCount; i++) {
            inventory.add(io.readU2le());
        }
        currentZoneId = io.readU2le();
        worldX = io.readU4le();
        worldY = io.readU4le();
        currentWeapon = io.readS2le();
        if (currentWeapon > 0) {
            currentAmmo = io.readS2le();
        }
        forceAmmo = io.readS2le();
        blasterAmmo = io.readS2le();
        blasterRifleAmmo = io.readS2le();
        zoneX = io.readU4le();
        zoneY = io.readU4le();
        damageTaken = io.readS4le();
        livesLost = io.readS4le();
        difficulty = io.readU4le();
        timeElapsed = io.readU4le();
        worldSize = io.readU2le();
        unknownCount = io.readS2le();
        unknownSum = io.readS2le();
        goalPuzzle = io.readU4le();
        goalPuzzleAgain = io.readU4le();

        assert goalPuzzle == goalPuzzleAgain;
        assert io.isEof();
    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public byte[] getMagic() {
        return magic;
    }

    public long getSeed() {
        return seed;
    }

    public long getPlanet() {
        return planet;
    }

    public long getOnDagobah() {
        return onDagobah;
    }

    public IdArray getPuzzles1() {
        return puzzles1;
    }

    public IdArray getPuzzles2() {
        return puzzles2;
    }

    public Dagobah getDagobah() {
        return dagobah;
    }

    public World getWorld() {
        return world;
    }

    public long getInventoryCount() {
        return inventoryCount;
    }

    public List<Integer> getInventory() {
        return inventory;
    }

    public int getCurrentZoneId() {
        return currentZoneId;
    }

    public long getWorldX() {
        return worldX;
    }

    public long getWorldY() {
        return worldY;
    }

    public short getCurrentWeapon() {
        return currentWeapon;
    }

    public Short getCurrentAmmo() {
        return currentAmmo;
    }

    public short getForceAmmo() {
        return forceAmmo;
    }

    public short getBlasterAmmo() {
        return blasterAmmo;
    }

    public short getBlasterRifleAmmo() {
        return blasterRifleAmmo;
    }

    public long getZoneX() {
        return zoneX;
    }

    public long getZoneY() {
        return zoneY;
    }

    public long getDamageTaken() {
        return damageTaken;
    }

    public long getLivesLost() {
        return livesLost;
    }

    public long getDifficulty() {
        return difficulty;
    }

    public long getTimeElapsed() {
        return timeElapsed;
    }

    public int getWorldSize() {
        return worldSize;
    }

    public int getUnknownCount() {
        return unknownCount;
    }

    public int getUnknownSum() {
        return unknownSum;
    }

    public long getGoalPuzzle() {
        return goalPuzzle;
    }

    public long getGoalPuzzleAgain() {
        return goalPuzzleAgain;
    }

    public Yodasav getRoot() {
        return root;
    }

    public static Yodesk getYodesk() {
        return yodesk;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }
}
