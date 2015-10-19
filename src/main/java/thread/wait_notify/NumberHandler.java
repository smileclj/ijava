package thread.wait_notify;

/**
 * Created by wana on 2015/10/19.
 */
public class NumberHandler {
    private int number;

    //增加
    public synchronized void increase(){
        while(number != 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 能执行到这里说明已经被唤醒
        // 并且number为0
        number++;
        System.out.println(number);
        notify();
    }

    //减少
    public synchronized  void decrease(){
        while(number == 0){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        // 能执行到这里说明已经被唤醒
        // 并且number不为0
        number--;
        System.out.println(number);
        notify();
    }
}
