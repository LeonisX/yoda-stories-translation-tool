package md.leonis.ystt.model.yodasav.rooms.zones;

import io.kaitai.struct.KaitaiInputStream;
import io.kaitai.struct.KaitaiOutputStream;
import io.kaitai.struct.KaitaiStruct;
import md.leonis.ystt.model.yodasav.Yodasav;

import java.util.ArrayList;
import java.util.List;

public class SaveMonster extends KaitaiStruct {

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
    private short coolDown;
    private short preferred;

    private List<SaveWaypoint> waypoints;

    private int id;

    private final transient Yodasav root;
    private final transient KaitaiStruct parent;

    public SaveMonster(KaitaiInputStream io, KaitaiStruct parent, Yodasav root) {
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
        coolDown = io.readS2le();
        preferred = io.readS2le();

        waypoints = new ArrayList<>(4);
        for (int i = 0; i < 4; i++) {
            waypoints.add(new SaveWaypoint(io, this, root));
        }

        // monster.face = this._assets.get(Char, characterId, NullIfMissing);
    }

    @Override
    public void write(KaitaiOutputStream os) {

        os.writeS2le(characterId);
        os.writeS2le(x);
        os.writeS2le(y);
        os.writeS2le(damageTaken);
        os.writeU4le(enabled ? 1 : 0);
        os.writeS2le(field10);

        os.writeS2le(bulletX);
        os.writeS2le(bulletY);
        os.writeS2le(currentFrame);
        os.writeU4le(flag18 ? 1 : 0);
        os.writeU4le(flag1c ? 1 : 0);
        os.writeU4le(flag20 ? 1 : 0);
        os.writeS2le(directionX);
        os.writeS2le(directionY);

        os.writeS2le(bulletOffset);
        os.writeS2le(facingDirection);
        os.writeS2le(field60);
        os.writeS2le(loot);
        os.writeU4le(flag2c ? 1 : 0);
        os.writeU4le(flag34 ? 1 : 0);
        os.writeU4le(hasItem ? 1 : 0);
        os.writeS2le(coolDown);
        os.writeS2le(preferred);

        waypoints.forEach(w -> w.write(os));
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

    public short getCoolDown() {
        return coolDown;
    }

    public short getPreferred() {
        return preferred;
    }

    public List<SaveWaypoint> getWaypoints() {
        return waypoints;
    }

    public Yodasav getRoot() {
        return root;
    }

    @Override
    public KaitaiStruct getParent() {
        return parent;
    }

    //DO we need it???
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
