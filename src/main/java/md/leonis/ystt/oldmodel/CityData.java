package md.leonis.ystt.oldmodel;

import java.util.AbstractMap;
import java.util.Map;

public class CityData {

    private int id;
    private int mapLayer;
    private int mapId;
    private String title;
    private String comment;

    public CityData() {
    }

    public CityData(Geo geo) {
        this.id = Geo.increment++;
        this.mapLayer = geo.getMapLayer();
        this.mapId = geo.getMapId();
        this.comment = geo.getNameComment();
        this.title = geo.getCleanName();
        //System.out.println("TTTTTITLE: " + this.title + "`");
    }

    public String getCityKey() {
        return String.format("city%02X", id);
    }

    public String getCityValue() {
        /*String titleId = Config.getKeyByValue(title);
        //System.out.println(titleId);
        String commentId = Config.getKeyByValue(comment);
        return String.format("%02X;%02X;%s;%s", mapLayer, mapId, titleId, commentId);*/
        return "";
    }

    public Map.Entry<String, String> toMapEntry() {
        return new AbstractMap.SimpleEntry<String, String>(getCityKey(), getCityValue());
    }

    public static CityData fromCSV(Map.Entry<String, String> entry) {
        //System.out.println("----- " + entry);
        String key = entry.getKey();
        String value = entry.getValue();
        CityData cityData = new CityData();
        /*String[] chunks = value.split(";");
        cityData.setTitle(Config.languageTable.getProperty(chunks[2]));
        if (chunks.length == 4) {
            cityData.setComment(Config.languageTable.getProperty(chunks[3]));
        } else {
            cityData.setComment("");
        }
        cityData.setMapLayer(Integer.parseInt(chunks[0], 16));
        cityData.setMapId(Integer.parseInt(chunks[1], 16));
        cityData.setId(Integer.parseInt(key.substring(4), 16));*/
        return cityData;

    }

    public String fineView() {
        StringBuilder stringBuilder = new StringBuilder(title);
        if (!comment.isEmpty()) {
            stringBuilder.append(" (").append(comment).append(")");
        }
        return stringBuilder.toString();
    }

    @Override
    public String toString() {
        return "CityData{" +
                "id=" + String.format("%02X", id) +
                ", mapLayer=" + String.format("%02X", mapLayer) +
                ", mapId=" + String.format("%02X", mapId) +
                ", title='" + title + '\'' +
                ", comment='" + comment + '\'' +
                '}';
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
