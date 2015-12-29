package lock;

import java.util.concurrent.locks.*;

/**
 * Created by wana on 2015/12/26.
 */
public class Lock3 {
    public synchronized void op(String id) {
        try {
            System.out.println("实例");
            System.out.println(Thread.currentThread().getName() + "开始操作");
            Thread.currentThread().sleep(5000);
            System.out.println(Thread.currentThread().getName() + "结束操作");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void staticop(String id) {
        try {
            System.out.println("类");
            System.out.println(Thread.currentThread().getName() + "开始操作");
            Thread.currentThread().sleep(5000);
            System.out.println(Thread.currentThread().getName() + "结束操作");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Runnable r1 = new Runnable() {
            @Override
            public void run() {
                Lock3 lock3 = new Lock3();
                lock3.op("1");
            }
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                Lock3.staticop("2");
            }
        };

        Thread t1 = new Thread(r1);
        t1.start();
//        Thread t2 = new Thread(r2);
//        t2.start();
//        Thread t3 = new Thread(r1);
//        t3.start();
        Thread t4 = new Thread(r2);
        t4.start();
    }
}

