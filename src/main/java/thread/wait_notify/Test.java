package thread.wait_notify;

/**
 * Created by wana on 2015/10/19.
 */
public class Test {
    public static void main(String[] args) {
        NumberHandler handler = new NumberHandler();
        new IncreaseThread(handler).start();
        new DecreaseThread(handler).start();
        new IncreaseThread(handler).start();
        new DecreaseThread(handler).start();
    }
}
