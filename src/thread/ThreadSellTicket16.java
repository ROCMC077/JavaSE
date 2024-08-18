package thread;

/**
 * 模擬 3個窗口賣票
 * */
public class ThreadSellTicket16 {
    public static void main(String[] args) {
        MyRunnable1 runnable = new MyRunnable1();
        Thread t1 = new Thread(runnable);
        t1.setName("t1");
        Thread t2 = new Thread(runnable);
        t2.setName("t2");
        Thread t3 = new Thread(runnable);
        t3.setName("t3");

        t1.start();
        t2.start();
        t3.start();

    }
}

class  MyRunnable1 implements Runnable{
    //實例變量 (多線程共享)
    private int ticketTotal = 100;

    @Override
    public void run() {
        // 賣票
        while (true){
            synchronized (this){
                if (ticketTotal<=0){
                    System.out.println("票已售完");
                    break; // 停止循環
                }
                // 票還有 ticketTotal>=0
                // 設定 處理一張票要50毫秒
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("窗口"+Thread.currentThread().getName()+" 賣一張票,還剩 "+(--ticketTotal)+" 張票");
            }
        }
    }
}
