package md.leonis.ystt.oldmodel2;

//TODO getChildren() -> list of movable children - add to all movable with children
public interface Movable {

    int getPosition();

    void setPosition(int position);

    default void move(int offset) {
        setPosition(getPosition() + offset);
    }
}
