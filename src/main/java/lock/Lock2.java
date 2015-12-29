package lock;

import java.util.concurrent.locks.*;

/**
 * Created by wana on 2015/12/26.
 */
public class Lock2 {
    private static Lock lock = new ReentrantLock();

    public void op(String id) {
        lock.lock();
        try {
            System.out.println(Thread.currentThread().getName() + "开始操作");
            Thread.currentThread().sleep(5000);
            System.out.println(Thread.currentThread().getName() + "结束操作");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        MyRunnable2 mr1 = new MyRunnable2("1");
        MyRunnable2 mr2 = new MyRunnable2("2");
        Thread t1 = new Thread(mr1);
        t1.start();
        Thread t2 = new Thread(mr1);
        t2.start();
        Thread t3 = new Thread(mr2);
        t3.start();
    }
}

class MyRunnable2 implements Runnable {
    private String id;

    public MyRunnable2(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        Lock2 lock = new Lock2();
        lock.op(id);
    }
}
