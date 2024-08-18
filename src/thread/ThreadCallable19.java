package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * 實現線程的第三種方式: 實現 Callable接口, 實現call方法
 * 這種方式實現的線程, 可以得到返回值
 * */
public class ThreadCallable19 {
    public static void main(String[] args) {
        // 創建 "未來任務"對象
        FutureTask<Integer> task = new FutureTask<>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                //處理業務...
                Thread.sleep(1000*3);
                return 1;
            }
        });

        //創建線程對象
        Thread t = new Thread(task);
        t.setName("t1");
        t.start();

        //啟動之後可以獲取 "未來任務" 的返回值
        //會阻塞線程, 因為要取到返回值, 所以要等 "未來線程"結束
        try {
            Integer i = null;
            i = task.get();
            System.out.println(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
