package md.leonis.ystt.model;

import java.util.List;

public class Char implements Movable {

    private int id;
    private int position; // position on text
    private int size;
    private List<Integer> tileIds;
    private String name;

    public Char(int id, int position, int size, List<Integer> tileIds, String name) {
        this.id = id;
        this.position = position;
        this.size = size;
        this.tileIds = tileIds;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Integer> getTileIds() {
        return tileIds;
    }

    public void setTileIds(List<Integer> tileIds) {
        this.tileIds = tileIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
