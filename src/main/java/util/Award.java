package util;

/**
 * 奖品父类，具体奖品可继承此类
 */
public class Award<T> {
    private T id;
    private int ratio;

    public Award() {
    }

    public Award(T id, int ratio) {
        this.id = id;
        this.ratio = ratio;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public int getRatio() {
        return ratio;
    }

    public void setRatio(int ratio) {
        this.ratio = ratio;
    }
}
