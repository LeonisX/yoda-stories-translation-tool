package md.leonis.ystt.model;

import md.leonis.extractor.utils.Config;

import java.util.AbstractMap;
import java.util.Map;

public class DungeonData {

    private int id;
    private int x;
    private int y;
    private int dungeonId;
    private int roomId;
    private String title;
    private String level;
    private String comment;

    public DungeonData() {
    }

    public DungeonData(Geo geo) {
        this.id = Geo.increment++;
        this.x = geo.getX();
        this.y = geo.getY();
        this.roomId = geo.getRoom();
        this.dungeonId = geo.getDungeon();
        this.comment = geo.getNameComment();
        this.title = geo.getCleanName();
        this.level = geo.getLevel();
    }

    public String getDungeonKey() {
        return String.format("dungeon%02X", id);
    }

    public String getDungeonValue() {
        String titleId = Config.getKeyByValue(title);
        //System.out.println(titleId);
        String commentId = Config.getKeyByValue(comment);
        return String.format("%02X;%s;%s;%s", roomId, titleId, level, commentId);
    }

    @Override
    public String toString() {
        return "DungeonData{" +
                "id=" + String.format("%02X", id) +
                ", x=" + String.format("%04X", x) +
                ", y=" + String.format("%04X", y) +
                ", dungeonId=" + String.format("%02X", dungeonId) +
                ", roomId=" + String.format("%02X", roomId) +
                ", title='" + title + '\'' +
                ", level='" + level + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }

    public Map.Entry<String, String> toMapEntry() {
        return new AbstractMap.SimpleEntry<String, String>(getDungeonKey(), getDungeonValue());
    }

    public static DungeonData fromCSV(Map.Entry<String, String> entry) {
        //String[] vals = csv.split("=");
        String key = entry.getKey();
        String value = entry.getValue();
        //System.out.println("CSV: " + csv);
        DungeonData dungeonData = new DungeonData();
        String[] chunks = value.split(";");
        //System.out.println(e.getValue());
        //System.out.println(chunks.length);
        dungeonData.setTitle(Config.languageTable.getProperty(chunks[1]));

        dungeonData.setLevel(chunks[2]);
        if (chunks.length == 4) {
            dungeonData.setComment(Config.languageTable.getProperty(chunks[3]));
        } else {
            dungeonData.setComment("");
        }
        //System.out.println(chunks[1]);
        dungeonData.setDungeonId(Integer.parseInt(chunks[1].substring(7, 9), 16));
        dungeonData.setX(Integer.parseInt(chunks[1].substring(9, 13), 16));
        dungeonData.setY(Integer.parseInt(chunks[1].substring(13), 16));
        dungeonData.setRoomId(Integer.parseInt(chunks[0], 16));
        dungeonData.setId(Integer.parseInt(key.substring(7), 16));
        return dungeonData;
    }

    public String fineView() {
        StringBuilder stringBuilder = new StringBuilder(title);
        if (!level.equals("1")) {
            stringBuilder.append(" #").append(level);
        }
        if (!comment.isEmpty()) {
            stringBuilder.append(" (").append(comment).append(")");
        }
        return stringBuilder.toString();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDungeonId() {
        return dungeonId;
    }

    public void setDungeonId(int dungeonId) {
        this.dungeonId = dungeonId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
