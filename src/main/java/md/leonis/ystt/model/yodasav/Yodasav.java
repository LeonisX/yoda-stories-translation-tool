package md.leonis.ystt.model.yodasav;// This is a generated file! Please edit source .ksy file and use kaitai-struct-compiler to rebuild

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// https://github.com/cyco/WebFun/blob/master/docs/src/appendix/yodasav.ksy
public class Yodasav extends KaitaiStruct {

    private byte[] magic;
    private long seed;
    private long planet;
    private long onDagobah;
    private IdArray puzzles1;
    private IdArray puzzles2;
    private Dagobah dagobah;
    private World world;
    private long inventoryCount;
    private List<Integer> inventory;
    private int currentZone;
    private long worldX;
    private long worldY;
    private short currentWeapon;
    private Short currentAmmo;
    private short forceAmmo;
    private short blasterAmmo;
    private short blasterRifleAmmo;
    private long zoneX;
    private long zoneY;
    private long damageTaken;
    private long livesLeft;
    private long difficulty;
    private long timeElapsed;
    private int worldSize;
    private int unknownArrayCount;
    private int unknownArraySum;
    private long endPuzzle;
    private long endPuzzleAgain;

    private final Yodasav root;
    private final KaitaiStruct parent;

    public static Yodasav fromFile(String fileName) throws IOException {
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
        seed = io.readU4le();
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
        currentZone = io.readU2le();
        worldX = io.readU4le();
        worldY = io.readU4le();
        currentWeapon = io.readS2le();
        if (currentWeapon != -1) {
            currentAmmo = io.readS2le();
        }
        forceAmmo = io.readS2le();
        blasterAmmo = io.readS2le();
        blasterRifleAmmo = io.readS2le();
        zoneX = io.readU4le();
        zoneY = io.readU4le();
        damageTaken = io.readU4le();
        livesLeft = io.readU4le();
        difficulty = io.readU4le();
        timeElapsed = io.readU4le();
        worldSize = io.readU2le();
        unknownArrayCount = io.readU2le();
        unknownArraySum = io.readU2le();
        endPuzzle = io.readU4le();
        endPuzzleAgain = io.readU4le();
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

    public int getCurrentZone() {
        return currentZone;
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

    public long getLivesLeft() {
        return livesLeft;
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

    public int getUnknownArrayCount() {
        return unknownArrayCount;
    }

    public int getUnknownArraySum() {
        return unknownArraySum;
    }

    public long getEndPuzzle() {
        return endPuzzle;
    }

    public long getEndPuzzleAgain() {
        return endPuzzleAgain;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }
}
