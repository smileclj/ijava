package lock;

/**
 * Created by wana on 2015/12/26.
 */
public class Lock1 {

    public void op(String id) {
        synchronized (id.intern()) {
            try {
                System.out.println(Thread.currentThread().getName() + "开始操作");
                Thread.currentThread().sleep(5000);
                System.out.println(Thread.currentThread().getName() + "结束操作");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        MyRunnable mr1 = new MyRunnable("1");
        MyRunnable mr2 = new MyRunnable("2");
        Thread t1 = new Thread(mr1);
        t1.start();
        Thread t2 = new Thread(mr1);
        t2.start();
        Thread t3 = new Thread(mr2);
        t3.start();
    }
}

class MyRunnable implements Runnable {
    private String id;

    public MyRunnable(String id) {
        this.id = id;
    }

    @Override
    public void run() {
        Lock1 lock = new Lock1();
        lock.op(id);
    }
}
