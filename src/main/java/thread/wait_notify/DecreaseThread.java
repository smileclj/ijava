package thread.wait_notify;

/**
 * Created by wana on 2015/10/19.
 */
public class DecreaseThread extends Thread {
    private  NumberHandler handler;

    public DecreaseThread(NumberHandler handler) {
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
            handler.decrease();
        }
    }
}
