package md.leonis.ystt.model;

import md.leonis.extractor.utils.Config;

import java.util.*;
import java.util.stream.Collectors;

public class Event {

    public static int counter = 0;

    private int id;
    private String locationId;
    private int relativeAddress;
    private int newValue;
    private int roomId;
    private List<String> textIds = new LinkedList<>();
    private Geo geo;

    public Event() {
    }

    public Event(Geo currentGeo, String text, Map<String, String> textPairs) {
        id = counter++;
        String[] chunks = text.split("\t");
        relativeAddress = Integer.parseInt(chunks[0], 16) - 0xBF00;
        newValue = Integer.parseInt(chunks[1], 16);
        int textOffset = 2;
        if (chunks[textOffset].length() == 2) {
            roomId = Integer.parseInt(chunks[textOffset], 16);
            textOffset++;
        }
        for (int i = textOffset; i < chunks.length; i++) {
            String value = Config.getKeyByValue(chunks[i]);
            if (value.isEmpty()) {
                String key = Event.getKeyByValue(textPairs, chunks[i]);
                if (key.isEmpty()) {
                    key = "phrase" + textPairs.size();
                    textPairs.put(key, chunks[i]);
                }
                value = key;
            }
            textIds.add(value);
        }
        geo = currentGeo;
        //System.out.println(chunks.length);
    }

    public static String getKeyByValue(Map<String, String> map, String value) {
        return map.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .findFirst().orElse("");
    }

    public Event(int relativeAddress, int newValue, int roomId, List<String> textIds) {
        this.relativeAddress = relativeAddress;
        this.newValue = newValue;
        this.roomId = roomId;
        this.textIds = textIds;
    }

    public String getEventKey() {
        return String.format("event%02X", id);
    }

    public String getEventValue() {
        String text = textIds.stream().collect(Collectors.joining(";"));
        return String.format("%04X;%02X;%01X;%s;%s", relativeAddress, roomId, newValue, locationId, text);
    }

    public Map.Entry<String, String> toMapEntry() {
        return new AbstractMap.SimpleEntry<String, String>(getEventKey(), getEventValue());
    }

    public Event fromCSV(Map.Entry<String, String> entry) {
        String key = entry.getKey();
        String value = entry.getValue();
        //System.out.println("CSV: " + csv);
        Event event = new Event();
        String[] chunks = value.split(";");
        //System.out.println(e.getValue());
        //System.out.println(chunks.length);
        //System.out.println(chunks[1]);
        event.setId(Integer.parseInt(key.substring(5), 16));
        event.setRelativeAddress(Integer.parseInt(chunks[0], 16));
        event.setRoomId(Integer.parseInt(chunks[1], 16));
        event.setNewValue(Integer.parseInt(chunks[2], 16));
        event.setLocationId(chunks[3]);
        //TODO textz
        for (int i = 4; i < chunks.length; i++) {
            event.textIds.add(chunks[i]);
        }
        return event;
    }

    public String fineView(List<DungeonData> dungeonDatas, List<CityData> cityDatas) {
        StringBuilder stringBuilder = new StringBuilder(String.format("%04X", relativeAddress));
        stringBuilder.append(String.format("\t%02X", newValue));
        String location = "";
        if (locationId.startsWith("dungeon")) {
            DungeonData dungeonData = dungeonDatas.get(Integer.parseInt(locationId.replace("dungeon", ""), 16));
            stringBuilder.append(String.format("\t%02X", dungeonData.getDungeonId()));
            stringBuilder.append(String.format("\t%02X", roomId));
            location = dungeonData.fineView();
        } else {
            CityData cityData = cityDatas.get(Integer.parseInt(locationId.replace("city", ""), 16));
            stringBuilder.append(String.format("\t%02X", cityData.getMapLayer()));
            stringBuilder.append(String.format("\t%02X", cityData.getMapId()));
            location = cityData.fineView();
        }
        StringBuilder stringBuilder2 = new StringBuilder();
        textIds.forEach(t -> stringBuilder2.append("     ").append(Config.languageTable.getProperty(t).split(";")[0].trim()));

        stringBuilder/*.append("\t")*/.append(String.format("%-40s", stringBuilder2));

        stringBuilder.append("\t\t").append(location);
        return stringBuilder.toString();
    }


    public void audit(List<DungeonData> dungeonDatas, List<CityData> cityDatas) {
        //System.out.print(locationId);
        int dungeonId = Integer.parseInt(locationId.replace("dungeon", ""), 16);
        DungeonData dungeonData = dungeonDatas.stream().filter(d -> d.getId() == dungeonId).findFirst().get();
        //int did = dungeonData.getDungeonId();
        System.out.print("    " + String.format("%02X ", roomId));
        StringBuilder stringBuilder2 = new StringBuilder();
        textIds.forEach(t -> stringBuilder2.append("     ").append(Config.languageTable.getProperty(t).split(";")[0].trim()));
        System.out.println(stringBuilder2);

    }


    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public Geo getGeo() {
        return geo;
    }

    public void setGeo(Geo geo) {
        this.geo = geo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelativeAddress() {
        return relativeAddress;
    }

    public void setRelativeAddress(int relativeAddress) {
        this.relativeAddress = relativeAddress;
    }

    public int getNewValue() {
        return newValue;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public List<String> getTextIds() {
        return textIds;
    }

    public void setTextIds(List<String> textIds) {
        this.textIds = textIds;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + String.format("%02X", id) +
                ", relativeAddress=" + String.format("%04X", relativeAddress) +
                ", newValue=" + String.format("%02X", newValue) +
                ", locationId=" + locationId +
                ", roomId=" + String.format("%02X", roomId) +
                ", textIds=" + textIds +
                //", geo=" + geo +
                '}';
    }

}
