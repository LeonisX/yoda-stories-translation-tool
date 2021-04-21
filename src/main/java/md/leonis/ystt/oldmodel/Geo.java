package md.leonis.ystt.oldmodel;

import md.leonis.bin.Dump;


public class Geo {

    public static int increment = 0;

    private Planets planet; //TODO need???
    private String name;

    // 0x400: 00 - X, but inverse. In SS always 00. Probably offset in tilemap
    private int x;        // 0x401-0x402  X coordinate on Map. Step on right: 0006 → 1006 → 2006. 401 max B0
    // 0x403: 00
    // 0x404: 00 - Y, but inverse. In SS always 00. Probably offset in tilemap
    private int y;        // 0x405-0x406  Y coordinate on Map. Examples: 0001, 5001. 405 max B0
    // 0x407: 00
    private int mapLayer;    // 0x408 Map layer. Whole planet or group of maps. Examples: 00 - Palma, 04 — Camineet, Parolit,...
    // map: 00 00
    // 00 = Palma
    // 01 = Motavia
    // 02 = Dezoris
    // 04 = Camineet, Parolit
    // 05 = Gothic, Eppi, Loar, Abion
    // 06 = Drasgow, Shion
    // 07 = Paseo, Uzo, Casba, Sopia
    // 08 = Skure, Twintown (entrances)
    // 09 = Skure, Twintown
    // 0A = Air Castle
    private int mapId;    // 0x409  Map Id on layer. Examples: 00 — Palma, 04 — Camineet,... (0x00 - 0x17)
    private int direction;// 0x40A        Direction in dungeon. Default = 0; To right: 1 → 2 → 3. Contains after exit from dungeon
    // 0x40B: 00
    //TODO YX (Medusa's tower) XY (Dezorian Cavern #5)
    private int room;   // 0x40C        Room # in dungeon; Both X (4-bit), Y (4-bit). Examples: 5E
    private int dungeon;// 0x40D        Dungeon #. Examples: Medusa's Cave, outdoor: 00; Camineet Warehouse: 02
                        // (00-3A) Interesting things: some dungeons intersects (Scion/Naula caves, ...)
    private int transport;// 0x40E        00 -> 08 — hovercraft, 04 — landrover, 0C - ice digger
    private int animation1;// 0x40F        00..03 - transport animation? set 00
    private int animation2;// 0x410        00..03 - transport animation? set 00
    private int y2;       // 0x411-0x412  Y coordinate on Map. Same as 0x405-0x406
    private int x2;       // 0x413-0x414  X coordinate on Map. Same as 0x401-0x402
    private int color;    // 0x415        Dungeon color. (00-0A) Examples: 02: light green, 03: blue? 04: blue, 05: light blue, 06: yellow, 07: pink,...
    private int type;     // 0x416        Type of environment. 0D: outdoor, cities; 0B: dungeons
    private int church;   // 0x417        Church # (for teleport); Examples: 00: no; 01: Camineet, 02: Gothic, 03: Loar, ...
    // 0x418-4FF: 00


    public Geo() {
    }

    public Geo(String name) {
        this.name = name;
    }

    public Geo(/*Planets planet, */String name, int x, int y, int mapLayer, int mapId, int direction, int room, int dungeon, int transport, int animation1, int animation2, int y2, int x2, int color, int type, int church) {
        //this.planet = planet;
        if (type == 0x0B) transport = 0; // Fix for transport on dungeon exit
        this.name = name;
        this.x = x;
        this.y = y;
        this.mapLayer = mapLayer;
        this.mapId = mapId;
        this.direction = direction;
        this.room = room;
        this.dungeon = dungeon;
        this.transport = transport;
        this.animation1 = animation1;
        this.animation2 = animation2;
        this.y2 = y2;
        this.x2 = x2;
        this.color = color;
        this.type = type;
        this.church = church;
    }

    public void readFromRom(Dump romData, int offset) {
        romData.setOffset(offset);
        x = romData.getWord(0x01);
        y = romData.getWord( 0x05);
        mapLayer = romData.getByte( 0x08);
        mapId = romData.getByte( 0x09);
        direction = romData.getByte(0x0A);
        room = romData.getByte(0x0C);
        dungeon = romData.getByte(0x0D);
        transport = romData.getByte(0x0E);
        animation1 = romData.getByte(0x0F);
        animation2 = romData.getByte(0x10);
        y2 = romData.getWord( 0x11);
        x2 = romData.getWord(0x13);
        color = romData.getByte(0x15);
        type = romData.getByte(0x16);
        church = romData.getByte(0x17);
        romData.setOffset(0);
    }


    public void writeToRom(Dump romData, int offset) {
        romData.setWord(0x01, x);
        romData.setWord( 0x05, y);
        romData.setByte( 0x08, mapLayer);
        romData.setByte( 0x09, mapId);
        romData.setByte(0x0A, direction);
        romData.setByte(0x0C, room);
        romData.setByte(0x0D, dungeon);
        romData.setByte(0x0E, transport);
        romData.setByte(0x0F, animation1);
        romData.setByte(0x10, animation2);
        romData.setWord( 0x11, y2);
        romData.setWord(0x13, x2);
        romData.setByte(0x15, color);
        romData.setByte(0x16, type);
        romData.setByte(0x17, church);
    }


    public void copyDataTo(Geo destGeo) {
        destGeo.setX(x);
        destGeo.setY(y);
        destGeo.setMapLayer(mapLayer);
        destGeo.setMapId(mapId);
        destGeo.setDungeon(dungeon);
        destGeo.setRoom(room);
        destGeo.setDirection(direction);
        destGeo.setColor(color);
        destGeo.setType(type);
    }

    public String toCSV() {
        return String.format("\"%04X\"", x) + ";" +
                String.format("\"%04X\"", y) + ";" +
                String.format("\"%02X%02X\"", mapLayer, mapId) + ";" +
                String.format("\"%02X\"", dungeon) + ";" +
                String.format("\"%02X\"", room) + ";" +
                String.format("\"%02X\"", direction) + ";" +
                String.format("\"%02X\"", color) + ";" +
                String.format("\"%02X\"", type) + ";" +
                name;
    }

    public boolean isDungeon() {
        return type == 0x0B;
    }

    public boolean isCity() {
        return !isDungeon();
    }

    public Planets getPlanet() {
        return planet;
    }

    public void setPlanet(Planets planet) {
        this.planet = planet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        this.x2 = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        this.y2 = y;
    }

    public int getMapLayer() {
        return mapLayer;
    }

    public void setMapLayer(int mapLayer) {
        this.mapLayer = mapLayer;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public int getDungeon() {
        return dungeon;
    }

    public void setDungeon(int dungeon) {
        this.dungeon = dungeon;
    }

    public int getTransport() {
        return transport;
    }

    public void setTransport(int transport) {
        this.transport = transport;
    }

    public int getAnimation1() {
        return animation1;
    }

    public void setAnimation1(int animation1) {
        this.animation1 = animation1;
    }

    public int getAnimation2() {
        return animation2;
    }

    public void setAnimation2(int animation2) {
        this.animation2 = animation2;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getChurch() {
        return church;
    }

    public void setChurch(int church) {
        this.church = church;
    }

    @Override
    public String toString() {
        return "Geo{" +
                "planet=" + planet +
                ", name='" + name + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", mapLayer=" + mapLayer +
                ", mapId=" + mapId +
                ", direction=" + direction +
                ", room=" + room +
                ", dungeon=" + dungeon +
                ", transport=" + transport +
                ", animation1=" + animation1 +
                ", animation2=" + animation2 +
                ", y2=" + y2 +
                ", x2=" + x2 +
                ", color=" + color +
                ", type=" + type +
                ", church=" + church +
                '}';
    }


    public boolean isClearName() {
        return !(hasNameComment() || hasLevel());
    }

    public String getNameComment() {
        if (!hasNameComment()) return "";
        return name.substring(name.indexOf('(') + 1, name.length() - 1);
    }

    public boolean hasNameComment() {
        return name.contains("(");
    }

    public String getCleanName() {
        if (isClearName()) return name.trim();
        return removeNameComment(name.split("#")[0].trim());
    }

    public String getClearDungeonKey() {
        //dungeonIdXY=roomId;titleId;level;test commentId
        return String.format("dungeon%02X%04X%04X", dungeon, x, y);
    }

    public String getClearCityKey() {
        return String.format("city%02X%02X", mapLayer, mapId);
    }

    public String getLevel() {
        if (!hasLevel()) return "1";
        return removeNameComment(name.split("#")[1].trim());
    }

    private String removeNameComment(String substring) {
        if (!hasNameComment()) return substring;
        return substring.split("\\(")[0].trim();
    }

    private boolean hasLevel() {
        return name.contains("#");
    }

}
