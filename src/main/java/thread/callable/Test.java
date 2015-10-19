//package thread.callable;
//
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//
///**
// * Created by wana on 2015/10/19.
// */
//public class Test {
//    public static void main(String[] args) {
//        ExecutorService service = Executors.newFixedThreadPool(2);
//        for(int i = 0;i<20;i++){
////            try {
////                System.out.println(service.submit(new CallOne()).get());
////                System.out.println(service.submit(new CallTwo()).get());
////                service.execute();
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        System.out.println("下一步");
//    }
//}
