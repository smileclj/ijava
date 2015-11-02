package current;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by wana on 2015/10/28.
 */
public class BlockingQueue {
    private static final ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(10);
    private static final ExecutorService service = Executors.newFixedThreadPool(20);

    public static void main(String[] args) {

        for(int i = 0;i<20;i++){
            service.execute(new Runnable() {
                @Override
                public void run() {
                    queue.add("1");
                }
            });

            service.execute(new Runnable() {
                @Override
                public void run() {
                    queue.remove();
                }
            });
        }
    }
}
