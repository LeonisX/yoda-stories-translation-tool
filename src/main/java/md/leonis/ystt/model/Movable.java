package md.leonis.ystt.model;

//TODO getChildren() -> list of movable children - add to all movable with children
public interface Movable {

    int getPosition();

    void setPosition(int position);

    default void move(int offset) {
        setPosition(getPosition() + offset);
    }
}
