package md.leonis.ystt.model.yodasav;

import io.kaitai.struct.ByteBufferKaitaiInputStream;
import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Monster extends KaitaiStruct {

    private short characterId;
    private short x;
    private short y;
    private short damageTaken;
    private boolean enabled;
    private short field10;

    private short bulletX;
    private short bulletY;
    private short currentFrame;
    private boolean flag18;
    private boolean flag1c;
    private boolean flag20;
    private short directionX;
    private short directionY;

    private short bulletOffset;
    private short facingDirection;
    private short field60;
    private short loot;
    private boolean flag2c;
    private boolean flag34;
    private boolean hasItem;
    private short cooldown;
    private short preferred;

    private List<Waypoint> waypoints;

    private final Yodasav root;
    private final KaitaiStruct parent;

    public static Monster fromFile(String fileName) throws IOException {
        return new Monster(new ByteBufferKaitaiInputStream(fileName));
    }

    public Monster(KaitaiInputStream io) {
        this(io, null, null);
    }

    public Monster(KaitaiInputStream io, KaitaiStruct parent) {
        this(io, parent, null);
    }

    public Monster(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
        super(io);
        this.parent = parent;
        this.root = root;
        _read();
    }

    private void _read() {

        characterId = io.readS2le();
        x = io.readS2le();
        y = io.readS2le();
        damageTaken = io.readS2le();
        enabled = io.readU4le() != 0;
        field10 = io.readS2le();

        bulletX = io.readS2le();
        bulletY = io.readS2le();
        currentFrame = io.readS2le();
        flag18 = io.readU4le() != 0;
        flag1c = io.readU4le() != 0;
        flag20 = io.readU4le() != 0;
        directionX = io.readS2le();
        directionY = io.readS2le();

        bulletOffset = io.readS2le();
        facingDirection = io.readS2le();
        field60 = io.readS2le();
        loot = io.readS2le();
        flag2c = io.readU4le() != 0;
        flag34 = io.readU4le() != 0;
        hasItem = io.readU4le() != 0;
        cooldown = io.readS2le();
        preferred = io.readS2le();

        waypoints = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            waypoints.add(new Waypoint(io, this, root));
        }
    }

    @Override
    public void write(KaitaiOutputStream os) {
        throw new UnsupportedOperationException();
    }

    public short getCharacterId() {
        return characterId;
    }

    public short getX() {
        return x;
    }

    public short getY() {
        return y;
    }

    public short getDamageTaken() {
        return damageTaken;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public short getField10() {
        return field10;
    }

    public short getBulletX() {
        return bulletX;
    }

    public short getBulletY() {
        return bulletY;
    }

    public short getCurrentFrame() {
        return currentFrame;
    }

    public boolean isFlag18() {
        return flag18;
    }

    public boolean isFlag1c() {
        return flag1c;
    }

    public boolean isFlag20() {
        return flag20;
    }

    public short getDirectionX() {
        return directionX;
    }

    public short getDirectionY() {
        return directionY;
    }

    public short getBulletOffset() {
        return bulletOffset;
    }

    public short getFacingDirection() {
        return facingDirection;
    }

    public short getField60() {
        return field60;
    }

    public short getLoot() {
        return loot;
    }

    public boolean isFlag2c() {
        return flag2c;
    }

    public boolean isFlag34() {
        return flag34;
    }

    public boolean isHasItem() {
        return hasItem;
    }

    public short getCooldown() {
        return cooldown;
    }

    public short getPreferred() {
        return preferred;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }
}
