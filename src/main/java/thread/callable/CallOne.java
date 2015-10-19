package thread.callable;

import java.util.concurrent.Callable;

/**
 * Created by wana on 2015/10/19.
 */
public class CallOne implements Callable<String> {

    @Override
    public String call() throws Exception {
        Thread.sleep(1000);
        return "one";
    }
}
