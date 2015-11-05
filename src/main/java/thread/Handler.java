package thread;

/**
 * Created by wana on 2015/11/5.
 */
public class Handler {
    public void doSomeThing1(){
        synchronized (new String("123").intern()){
            try {
                System.out.println(Thread.currentThread().getName() + "do someThing1");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void doSomeThing2(){
        synchronized (new String("123").intern()){
            try {
                System.out.println(Thread.currentThread().getName() + "do someThing2");
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
