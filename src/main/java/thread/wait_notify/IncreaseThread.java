package thread.wait_notify;

import java.util.Random;

/**
 * Created by wana on 2015/10/19.
 */
public class IncreaseThread extends Thread{
    private NumberHandler handler;

    public IncreaseThread(NumberHandler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        for(int i=0;i<20;i++){
            try {
                Thread.sleep((long)Math.random()*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.increase();
        }
    }
}
