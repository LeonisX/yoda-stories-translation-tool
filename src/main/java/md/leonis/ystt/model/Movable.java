package md.leonis.ystt.model;

public interface Movable {

    int getPosition();

    void setPosition(int position);

    default void move(int offset) {
        setPosition(getPosition() + offset);
    }
}
