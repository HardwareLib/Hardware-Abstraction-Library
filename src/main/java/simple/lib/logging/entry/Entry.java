package simple.lib.logging.entry;

public interface Entry<T> {
    public abstract void update(T value);
    public abstract T getValue();
}
