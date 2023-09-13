package Model;

public interface Iterator<T> {
    T next();
    boolean hasNext();
    T currentItem();
    void reset();
}
