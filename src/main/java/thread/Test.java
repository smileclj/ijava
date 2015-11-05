package thread;

/**
 * Created by wana on 2015/11/5.
 */
public class Test {
    public static void main(String[] args) {
//        Handler handler = new Handler();
        Thread1 t1 = new Thread1(new Handler());
        t1.start();
        Thread2 t2 = new Thread2(new Handler());
        t2.start();
    }
}

class Thread1 extends Thread {
    private Handler handler;

    public Thread1(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.doSomeThing1();
    }
}

class Thread2 extends Thread {
    private Handler handler;

    public Thread2(Handler handler) {
        this.handler = handler;
    }

    @Override
    public void run() {
        handler.doSomeThing2();
    }
}
