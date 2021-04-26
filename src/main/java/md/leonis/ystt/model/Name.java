package md.leonis.ystt.model;

public class Name implements Movable {

    private int position; // position on text
    private int tileId;
    private String name;

    public Name(int position, int tileId, String name) {
        this.position = position;
        this.tileId = tileId;
        this.name = name;
    }

    @Override
    public int getPosition() {
        return position;
    }

    @Override
    public void setPosition(int position) {
        this.position = position;
    }

    public int getTileId() {
        return tileId;
    }

    public void setTileId(int tileId) {
        this.tileId = tileId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
