package md.leonis.ystt.oldmodel2;

// IZON, IZAX, IZX2, IZX3, IZX4
public class Izon implements Movable {

    private int position;
    private int size;

    public Izon(int position, int size) {
        this.position = position;
        this.size = size;
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
}
