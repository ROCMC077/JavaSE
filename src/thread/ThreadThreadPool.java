package thread;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 創建線程的第4種方式: 使用線程池
 * 線程池本身是一種緩存: cache
 * 一般都是伺服器啟動的時候, 初始化線程池
 * 也就是說伺服器啟動的時候, 創建N多個對象
 * 直接放到線程池中, 需要使用線程對象的時候, 直接從線程池中獲取
 *
 *
 *
 *
 * */
public class ThreadThreadPool {
    public static void main(String[] args) {
        // 創建一個線程池 (裡面放3個線程)
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        //將任務交給線程池,(不需要觸碰線程對象, 只需將要處理的任務交給線程池)
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    System.out.println(Thread.currentThread().getName()+"---->"+i);
                }
            }
        });

        //最後記得要關閉線程池
        executorService.shutdown();
    }
}
