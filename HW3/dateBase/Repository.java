package HW3.dateBase;

public interface Repository<T> {
    void save(T text);

    T readLog();
}